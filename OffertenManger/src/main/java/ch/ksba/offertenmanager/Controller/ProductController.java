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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sharu
 */
public class ProductController implements Initializable {

    @FXML
    private TextField txtproductname;
    @FXML
    private TextField txtproducttype;
    @FXML
    private TextField txtunitprice;
    @FXML
    private Button btnaddProduct;
    @FXML
    private Label message;
    @FXML
    private Label lblProductname;
    @FXML
    private Label lblProducttype;
    @FXML
    private Label lblProductprice;
    @FXML
    private Label lblTitelProduct;
    @FXML
    private Button btnBack;

    private Stage stage;

    private String selectedEnglish = "english";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        stage = MainApp.getStage();

    }

    @FXML
    private void handleaddProduct(ActionEvent event) throws IOException {

        Database.getInstance().addProduct(txtproductname.getText(), txtproducttype.getText(), Double.parseDouble(txtunitprice.getText()));

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void Switch2Selection(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
