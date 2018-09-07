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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sharu
 */
public class LoginController implements Initializable {

    @FXML private TextField txtusername;
    @FXML private PasswordField txtpassword;
    @FXML private Button btnLogin, btnRegister;
    @FXML private Label message, lblTitel, lblTitelLogin, lblWelcome;
    
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stage = MainApp.getStage();

    }

    @FXML
    public void login() {
        handleLogin();
    }

    public void handleLogin() {

        try {
            if (Database.getInstance().check(txtusername.getText().trim(), txtpassword.getText().trim())) {

                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else if (txtusername.getText().trim().isEmpty() || txtpassword.getText().trim().isEmpty()) {
                System.out.println("ja mafewfwenn");
                message.setText("S'il vous plaît entrer votre nom d'utilisateur et mot de passe.");

            } else {
                message.setText("Veuillez entrer vos données correctes");

            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSignUp(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void ChooseLanguage(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Language.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
