/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ksba.offertenmanager.Controller;

import ch.ksba.offertenmanager.Database;
import ch.ksba.offertenmanager.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sharu
 */
public class ClientController implements Initializable {

    @FXML
    private TextField txtcompanyname, txtname, txtfirstname, txtadress, txtpostcode, txtplace;
    @FXML
    private ComboBox<String> cmbSalutation;
    @FXML
    private Button btnaddClient, btnBack;
    @FXML
    private Label message, lblCompanyname, lblSalutationClient, lblNameClient, lblFirstnameClient, lblAdressClient, lblPostalCodeClient, lblPlaceClient, lblTitelClient;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stage = MainApp.getStage();

        ObservableList<String> cmbFiller = FXCollections.observableArrayList();
        cmbFiller.addAll("Mister", "Madame");
        cmbSalutation.setItems(cmbFiller);

    }

    @FXML
    private void handleaddClient(ActionEvent event) throws IOException {

        Database.getInstance().addClient(txtcompanyname.getText().trim(), cmbSalutation.getSelectionModel().getSelectedItem().toString().trim(), txtname.getText().trim(), txtfirstname.getText().trim(),
                txtadress.getText().trim(), Integer.parseInt(txtpostcode.getText().trim()), txtplace.getText().trim());

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        if (txtcompanyname.getText().trim().isEmpty() || cmbSalutation.getSelectionModel().getSelectedItem().trim().isEmpty() || txtname.getText().trim().isEmpty() || txtfirstname.getText().trim().isEmpty()
                || txtadress.getText().trim().isEmpty() || txtpostcode.getText().trim().isEmpty() || txtplace.getText().trim().isEmpty()) {
            System.out.println("faux");
        }
    }

    @FXML
    private void Switch2Selection(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
