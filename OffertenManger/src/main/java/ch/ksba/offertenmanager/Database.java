/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ksba.offertenmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sharu
 */
public class Database {

    private static Database instance = null;
    private String url = "https://sharushan.com/api/api/";
    private String session = "";

    
    private Database() {

    }

    //Hier wird der Konstruktor ausgeführt
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    
    public boolean checkUser(String username, String password) {
        HttpClient client = HttpClientBuilder.create().build();

        try {

            if (username == null && password == null) {
                return false;
            }

            HttpPost post = new HttpPost(url + "session.php");

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("username", username));
            urlPrameters.add(new BasicNameValuePair("password", password));

            post.setEntity(new UrlEncodedFormEntity(urlPrameters));

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                session = obj.getString("session");
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void addClient(String firmenname, String anrede, String name, String vorname, String addresszeile, int plz, String ort) {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost(url + "addClient.php?session=" + session);

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("firmenname", firmenname));
            urlPrameters.add(new BasicNameValuePair("anrede", anrede));
            urlPrameters.add(new BasicNameValuePair("name", name));
            urlPrameters.add(new BasicNameValuePair("vorname", vorname));
            urlPrameters.add(new BasicNameValuePair("addresszeile", addresszeile));
            urlPrameters.add(new BasicNameValuePair("plz", Integer.toString(plz)));
            urlPrameters.add(new BasicNameValuePair("ort", ort));

            post.setEntity(new UrlEncodedFormEntity(urlPrameters));

            client.execute(post);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addProduct(String produktname, String produkttyp, double preis) {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost(url + "addProduct.php?session=" + session);

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("produktname", produktname));
            urlPrameters.add(new BasicNameValuePair("produkttyp", produkttyp));
            urlPrameters.add(new BasicNameValuePair("preis", Double.toString(preis)));

            post.setEntity(new UrlEncodedFormEntity(urlPrameters));

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                System.out.println(obj.getString("msg"));
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Register(String benutzername, String anrede, String vorname, String nachname, String adresse, int plz, String ort, String abteilung, String passwort) {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost(url + "register.php");

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("benutzername", benutzername));
            urlPrameters.add(new BasicNameValuePair("anrede", anrede));
            urlPrameters.add(new BasicNameValuePair("vorname", vorname));
            urlPrameters.add(new BasicNameValuePair("nachname", nachname));
            urlPrameters.add(new BasicNameValuePair("adresse", adresse));
            urlPrameters.add(new BasicNameValuePair("plz", Integer.toString(plz)));
            urlPrameters.add(new BasicNameValuePair("ort", ort));
            urlPrameters.add(new BasicNameValuePair("abteilung", abteilung));
            urlPrameters.add(new BasicNameValuePair("passwort", passwort));

            post.setEntity(new UrlEncodedFormEntity(urlPrameters));

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                System.out.println(obj.getString("msg"));
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<String> getClients() {
        HttpClient client = HttpClientBuilder.create().build();
        try {
            ObservableList<String> kunden = FXCollections.observableArrayList();
            HttpGet request = new HttpGet(url + "clients.php?session=" + session);

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                JSONArray arr = obj.getJSONArray("clients");
                for (int i = 0; i < arr.length(); i++) {
                    kunden.add(arr.getString(i));
                }
            }
            return kunden;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ObservableList<String> getProducts() {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            ObservableList<String> produkte = FXCollections.observableArrayList();
            HttpGet request = new HttpGet(url + "products.php?session=" + session);

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                JSONArray arr = obj.getJSONArray("products");
                for (int i = 0; i < arr.length(); i++) {
                    produkte.add(arr.getString(i));
                }
            }
            return produkte;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ObservableList<String> getSeller() {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            ObservableList<String> users = FXCollections.observableArrayList();

            HttpGet request = new HttpGet(url + "users.php?session=" + session);

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                JSONArray arr = obj.getJSONArray("users");
                for (int i = 0; i < arr.length(); i++) {
                    users.add(arr.getString(i));
                }
            }
            return users;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JSONObject getClientoffer(String firmenname) {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            JSONObject clientOffer = null;

            HttpPost request = new HttpPost(url + "clientOffer.php?session=" + session);

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("firmenname", firmenname));

            request.setEntity(new UrlEncodedFormEntity(urlPrameters));

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                clientOffer = obj.getJSONObject("client");
            }
            return clientOffer;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JSONObject getProductoffer(String produktname) {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            JSONObject produkt = null;

            HttpPost request = new HttpPost(url + "productOffer.php?session=" + session);

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("produktname", produktname));

            request.setEntity(new UrlEncodedFormEntity(urlPrameters));

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                produkt = obj.getJSONObject("produkt");
            }
            return produkt;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean check(String username, String password) {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            if (username == null && password == null) {
                return false;
            }

            HttpPost post = new HttpPost(url + "session.php");

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("username", username));
            urlPrameters.add(new BasicNameValuePair("password", password));

            post.setEntity(new UrlEncodedFormEntity(urlPrameters));

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            System.out.println(obj);
            if (obj.getInt("status") == 200) {
                session = obj.getString("session");
                return true;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean redundand(String benutzername) {
        HttpClient client = HttpClientBuilder.create().build();

        try {
            if (benutzername == null) {
                return false;
            }

            HttpPost post = new HttpPost(url + "session.php?session=" + session);

            List<NameValuePair> urlPrameters = new ArrayList<>();
            urlPrameters.add(new BasicNameValuePair("benutzername", benutzername));

            post.setEntity(new UrlEncodedFormEntity(urlPrameters));

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            JSONObject obj = new JSONObject(rd.readLine());
            if (obj.getInt("status") == 200) {
                return obj.getBoolean("redundand");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
}
