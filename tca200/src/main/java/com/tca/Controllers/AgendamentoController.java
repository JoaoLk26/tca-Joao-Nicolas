package com.tca.Controllers;

import com.tca.DAO.AgendamentoDAO;
import com.tca.Models.Agendamento;
import com.tca.Models.Pet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AgendamentoController {

    @FXML
    private DatePicker datePickerData;
    
    @FXML
    private TextField txtMotivo;
    
    @FXML
    private Button btnSalvar;

    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private Pet petSelecionado;

    public void setPetSelecionado(Pet pet) {
        this.petSelecionado = pet;
    }

    @FXML
    private void initialize() {
        btnSalvar.setOnAction(event -> salvarAgendamento());
    }

    @FXML
    private void salvarAgendamento() {
        if (petSelecionado == null) {
            mostrarAlerta("Erro", "Nenhum pet foi selecionado!");
            return;
        }
    
        if (petSelecionado.getDono() == null) {
            mostrarAlerta("Erro", "O pet selecionado não possui um dono associado!");
            return;
        }
    
        LocalDate dataAgendamento = datePickerData.getValue();
        String motivo = txtMotivo.getText().trim();
    
        if (dataAgendamento == null || motivo.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos obrigatórios!");
            return;
        }
    
        // Criando o objeto Agendamento corretamente
        Agendamento agendamento = new Agendamento(0, petSelecionado, dataAgendamento, motivo);
    
        try {
            agendamentoDAO.inserirAgendamento(agendamento);
            mostrarAlerta("Sucesso", "Agendamento cadastrado com sucesso!");
            fecharJanela();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Não foi possível salvar o agendamento.");
            e.printStackTrace();
        }
    }
    
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }
}