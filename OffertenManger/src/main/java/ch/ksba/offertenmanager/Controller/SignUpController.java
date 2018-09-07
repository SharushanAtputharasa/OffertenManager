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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sharu
 */
public class SignUpController implements Initializable {
    
    @FXML private TextField txtSellerName, txtSellerFirstname, txtSellerAdress, txtSellerPostcode, txtSellerPlace, txtSellerDepartment, txtusername;
    @FXML private ComboBox<String> cmbSellerSalutation;
    @FXML private PasswordField pwSellerPW, pwSellerConfirmPW;
    @FXML private Label message, lblName, lblFirstname, lblSalutation, lblAdress, lblPostalcode, lblPlace, lblDepartment, lblPassword, lblPasswordConfirm, lblUsername, lblTitelSignUp;
    @FXML private Button btnConfirm, btnBack;

    private Stage stage;

    private String male;
    private String female;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stage = MainApp.getStage();
       
        ObservableList<String> cmbFiller = FXCollections.observableArrayList();
        cmbFiller.addAll("Mister", "Madame");
        cmbSellerSalutation.setItems(cmbFiller);
    }

    @FXML
    private void btnSignUp(ActionEvent event) throws IOException {
        if (txtusername.getText().trim().isEmpty() || cmbSellerSalutation.getSelectionModel().getSelectedItem().toString().trim().isEmpty() || txtSellerFirstname.getText().trim().isEmpty() || txtSellerName.getText().trim().isEmpty() || txtSellerAdress.getText().trim().isEmpty()
                || txtSellerPostcode.getText().trim().isEmpty() || txtSellerPlace.getText().trim().isEmpty() || txtSellerDepartment.getText().trim().isEmpty() || pwSellerPW.getText().trim().isEmpty() || pwSellerConfirmPW.getText().trim().isEmpty()) {
            message.setText("Alle felder müssen gefüllt sein");
        } else if (Database.getInstance().redundand(txtusername.getText())) {
            message.setText("New Username");
        } else if (!(pwSellerPW.getText().trim().equals(pwSellerConfirmPW.getText().trim()))) {
            message.setText("Bei den Passwörten ist eine gewisse Diskrepanz aufgetaucht.");

        } else {
            Database.getInstance().Register(txtusername.getText(), cmbSellerSalutation.getSelectionModel().getSelectedItem().toString(), txtSellerFirstname.getText(), txtSellerName.getText(), txtSellerAdress.getText(),
                     Integer.parseInt(txtSellerPostcode.getText()), txtSellerPlace.getText(), txtSellerDepartment.getText(), pwSellerPW.getText());

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    private void Switch2Login(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
