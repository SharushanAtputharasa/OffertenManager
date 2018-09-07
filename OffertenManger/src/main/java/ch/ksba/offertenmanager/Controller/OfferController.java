/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ksba.offertenmanager.Controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import ch.ksba.offertenmanager.Database;
import ch.ksba.offertenmanager.MainApp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author sharu
 */
public class OfferController implements Initializable {

    public static final String DEST = "results/chapter01/HelloWorld.pdf";

    @FXML private ComboBox<String> cmbClients, cmbProducts, cmbPaymentterm, cmbPaymentmethod, cmbSeller;
    @FXML private TextField txtQuantity, txtSalenotice, txtConsulting, txtAdd;
    @FXML private DatePicker dateReception;
    @FXML private Label lblOffertext, lblMessaageSuccess, lblTitelOffer, lblPreview;
    @FXML private Button btnConfirm, btnBack;
  

    private String Companyname = "<Companyname>",
            Firstname = "<Firstname>",
            Lastname = "<Lastname>",
            Adress = "<Adress>",
            Place = "<Place>",
            Autodate,
            Productname = "<Productname>",
            Salutation = "<Salutation>",
            Termin1 = "<Termin1>",
            Tax = "<Tax>",
            Sale = "<Sale>",
            Paymentterm = "<Paymentterm>",
            Paymentmethod = "<Paymentmethod>",
            Saleadvice = "<Saleadvice>",
            Consulting = "<Consulting>",
            Seller = "<Seller>",
            Advertise = "<Advertise>",
            Department = "<Department>",
            Extras = "<Beilagen>";

    private int Postcode, Quantity;

    private double price, Pricetotal, Sales;

    private Stage stage;

    private String offertenText = "";
    private String offertenTextPDF = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        stage = MainApp.getStage();

        cmbClients.setItems(Database.getInstance().getClients());
        cmbProducts.setItems(Database.getInstance().getProducts());
        cmbSeller.setItems(Database.getInstance().getSeller());

        ObservableList<String> comboBoxInputs = FXCollections.observableArrayList();
        comboBoxInputs.addAll("PayPal", "Carte de credite", "Cash", "Banque");
        cmbPaymentmethod.setItems(comboBoxInputs);

        ObservableList<String> comboBoxInputs2 = FXCollections.observableArrayList();
        comboBoxInputs2.addAll("15", "30");
        cmbPaymentterm.setItems(comboBoxInputs2);

        createOffer();
    }

    @FXML
    public void getInfo() throws SQLException {
        if (!cmbClients.getSelectionModel().isEmpty()) {
            Companyname = cmbClients.getSelectionModel().getSelectedItem().toString();
            System.out.println(Companyname);
            JSONObject client = Database.getInstance().getClientoffer(Companyname);

            Salutation = client.getString("anrede");

            Firstname = client.getString("vorname");
            Lastname = client.getString("name");
            Adress = client.getString("adresszeile");
            Place = client.getString("ort");

            Postcode = client.getInt("plz");

        }

        Productname = cmbProducts.getSelectionModel().getSelectedItem();

        if (!txtQuantity.getText().trim().isEmpty()) {
            Quantity = Integer.parseInt(txtQuantity.getText());
        }

        if (!txtQuantity.getText().isEmpty()) {
            JSONObject produkt = Database.getInstance().getProductoffer(Productname);
            price = produkt.getDouble("preis");

            Pricetotal = price * Quantity;
        }

        if (dateReception.getValue() != null) {
            LocalDate ld = dateReception.getValue();
            Calendar c = Calendar.getInstance();
            c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
            Date date = c.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");

            Termin1 = dateFormat.format(date);
        }

        Paymentterm = cmbPaymentterm.getSelectionModel().getSelectedItem();

        Paymentmethod = cmbPaymentmethod.getSelectionModel().getSelectedItem();

        Saleadvice = txtSalenotice.getText();

        Consulting = txtConsulting.getText();

        Seller = cmbSeller.getSelectionModel().getSelectedItem();

        Advertise = txtAdd.getText();

        createOffer();

    }

    public void createOffer() {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        Date date = new Date();
        Autodate = dateFormat.format(date);

        String aaa = String.valueOf(Quantity);
        //aaa = "<Quantity>";

        String bbb = String.valueOf(Sales);
        //bbb = "<Sales>";

        Paymentmethod = cmbPaymentmethod.getSelectionModel().getSelectedItem();

        offertenText = "  VIN de Lausanne SA \n" + "  3, Rue de la Piquette \n  2000 Lausanne\n\n";
        offertenText += "  " + Companyname + "\n  " + Lastname + " " + Firstname + "\n  " + Adress + "\n  " + Postcode + " " + Place + "\n\n\n\n";
        offertenText += "  Lausanne, " + Autodate + "\n\n\n";
        offertenText += "  Offer for " + Salutation + Lastname + "\n\n";
        offertenText += "  " + Salutation + "\n\n";

        offertenText += "  We have received your request from" + " " + Termin1 + "  " + "and we thank you very much." + "\n";
        offertenText += "  We are pleased to submit the following offer." + "\n\n";

        offertenText += "  " + aaa + "      " + Productname + "      " + Pricetotal + " CHF " + " " + "\n\n";

        offertenText += "  We offer you the " + Productname + " at the price of " + price + " CHF each, including " + "8%" + " Tax .\n"
                + "  In addition, you can benefit from a special discount of " + bbb + " % for any order that is higher than " + Sales + " CHF." + "\n\n";

        offertenText += "  We promise to deliver the order within 7 days." + "\n" + "  The payment period is " + Paymentterm + " days after receipt of goods." + "\n"
                + "  Please pay with " + Paymentmethod + " This offer is valid until " + Termin1 + "." + "\n\n  ";

        offertenText += Saleadvice + "\n\n  ";
        offertenText += Consulting + "\n  ";
        offertenText += ""+ Advertise + "\n\n  ";

        offertenText += "If you still have any questions, please do not hesitate to contact us." + "\n\n  ";
        offertenText += "Sincerely " + "\n\n  ";

        offertenText += Seller;

        lblOffertext.setText(offertenText);
    }

    public String getHTMLText() {

        String aaa = String.valueOf(Quantity);
        //aaa = "<Quantity>";

        String bbb = String.valueOf(Sales);
        //bbb = "<Sales>";

        String offertenTextPDF = "";

        offertenTextPDF += "  " + " VIN de Lausanne SA <br>" + "   3, Rue de la Piquette <br>   2000 Lausanne<br><br>";
        offertenTextPDF += "  " + Companyname + "<br>  " + Lastname + " " + Firstname + "<br>  " + Adress + "<br>  " + Postcode + " " + Place + "<br><br><br><br>";
        offertenTextPDF += "  Lausanne, " + Autodate + "<br><br>";
        offertenTextPDF += "  Offer for  " + Productname + "<br><br>";
        offertenTextPDF += "  " + Salutation + "<br><br>";

        offertenTextPDF += "  We have received your request from" + " " + Termin1 + "  " + "and we thank you very much." + "<br>";
        offertenTextPDF += "   We are pleased to submit the following offer." + "<br><br>";

        offertenTextPDF += "  " + aaa + "      " + Productname + "      " + Pricetotal + " CHF " + "      " + "8% " + "<br><br>";

        offertenTextPDF += "   We offer you the " + Productname + " at the price of " + price + "CHF each, including " + "8%" + " Tax." + "<br>"
                + "  In addition, you can benefit from a special discount of " + bbb + " % for any order that is higher than" + "<br>" +"  "+ Sales + " CHF." + "<br><br>";

        offertenTextPDF += "  We promise to deliver the order within 7 days." + "\n" + "   The payment period is  " + Paymentterm + " jours" + " " + " days after receipt of "+"goods." + "\n"
                + "  Please pay with " + Paymentmethod + " This offer is valid until" + Termin1 + "." + "\n\n  ";

        offertenTextPDF += Saleadvice + "<br><br>  ";
        offertenTextPDF += Consulting;

        offertenTextPDF += Advertise;

        offertenTextPDF += "" + " If you still have any questions, please do not hesitate to contact us." + "<br><br>  ";
        offertenTextPDF += "  Sincerely " + Salutation + "<br><br><br>  ";

        offertenTextPDF += Seller;

        return offertenTextPDF;

    }

    @FXML
    private void btnSaveOffer(ActionEvent event) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, new FileOutputStream("generated_offer.pdf"));

        document.open();

        HTMLWorker hw = new HTMLWorker(document);
        hw.parse(new StringReader("<html><p>" + this.getHTMLText().concat(offertenTextPDF) + "</p></html>"));
        document.close();

        lblMessaageSuccess.setVisible(true);
    }

    @FXML
    private void Switch2Selection(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
