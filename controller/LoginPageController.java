package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.Delivery;
import model.Order;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    private String AdminUserName = "Admin";
    private String Adminpassword = "12345678";
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<CafeRestaurant> restaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Order> orders = new ArrayList<Order>();


    @FXML
    private Label errorLBL;
    @FXML
    private PasswordField passwordTXF;

    @FXML
    private TextField usernameTXF;
    Stage stage;
    public void initfunction(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\startpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StartPageController controller = loader.getController();
        controller.initfunction(this.stage);

        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @FXML
    void login(ActionEvent event) {
    if(usernameTXF.getText().equals("Admin")&&passwordTXF.getText().equals("12345678"))
    {
        goToAdminPage();
    }
    for(Costumer cos : costumers)
    {
        if(usernameTXF.getText().equals(cos.getUsername()) && cos.getPassword().equals(passToHash(passwordTXF.getText())))
        {
            goToCostumerMenu(cos);
        }
    }
    for(Delivery del :deliveries)
    {
        if(usernameTXF.getText().equals(del.getUsername()) && del.getPassword().equals(passToHash(passwordTXF.getText())))
        {
            goToDeliveryMenu(del);
        }
    }
    errorLBL.setText("the entered Username Or Password IS incorrect!");

    }
    private String passToHash(String pass) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        try {
            // hash password with PBKDF2 algorithm
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            System.out.printf("hash: %s%n", enc.encodeToString(hash));
            return enc.encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;

    }
    private void goToAdminPage()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\adminmainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminMainPageController controller = loader.getController();
        controller.initfunction(stage,restaurants,deliveries,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }
    private void goToCostumerMenu(Costumer costumer)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumermainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CostumerMainPageController controller = loader.getController();
        controller.initfunction(stage,costumer,orders,restaurants,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }
    private void goToDeliveryMenu(Delivery delivery)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\deliverymainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deliveryMainPageController controller = loader.getController();
        controller.initfunction(stage,delivery,orders,deliveries,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }
    private void getCostumers()
    {
        try {
            FileInputStream fis = new FileInputStream("costumers.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.costumers = (ArrayList<Costumer>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getDeliveries()
    {
        try {
            FileInputStream fis = new FileInputStream("deliveries.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.deliveries = (ArrayList<Delivery>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void getRestaurants()
    {
        try {
            FileInputStream fis = new FileInputStream("CafeRestaurants.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.restaurants = (ArrayList<CafeRestaurant>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void getOrders()
    {
        try {
            FileInputStream fis = new FileInputStream("orders.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.orders = (ArrayList<Order>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCostumers();
        getDeliveries();
        getRestaurants();
        getOrders();

    }
}
