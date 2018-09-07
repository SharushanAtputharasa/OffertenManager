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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sharu
 */
public class SelectionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lblTitelSelection;
    @FXML
    private Button btnBack;

    private Stage stage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stage = MainApp.getStage();
    }

    @FXML
    private void btnSwitch2Client(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnSwitch2Product(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Product.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnSwitch2Offer(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Offer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Switch2Login(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
