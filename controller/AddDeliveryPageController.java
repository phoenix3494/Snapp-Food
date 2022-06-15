package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.Delivery;
import model.IDcounter;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.regex.Pattern;

public class AddDeliveryPageController implements Initializable {
    Stage stage;
    Random rand = new Random();
    @FXML
    private TextField EmailTXF;

    @FXML
    private TextField codeTXF;

    @FXML
    private Label errorLBL;

    @FXML
    private TextField fnameTXF;

    @FXML
    private TextField nameTXF;

    @FXML
    private PasswordField passwordTXF;

    @FXML
    private TextField phoneTXF;

    @FXML
    private Button resetBTN;

    @FXML
    private Button signupBTN;
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private IDcounter idcounter = new IDcounter();
    private String Code;

    public void initfunction(Stage stage, ArrayList<Delivery> deliveries, ArrayList<Costumer> costumers,
            ArrayList<CafeRestaurant> cafeRestaurants) {
        this.stage = stage;
        this.deliveries = deliveries;
        this.costumers = costumers;
        this.cafeRestaurants = cafeRestaurants;

    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\adminmainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminMainPageController controller = loader.getController();
        controller.initfunction(stage, cafeRestaurants, deliveries, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @FXML
    void reset(ActionEvent event) {
        setDisable(false);
        setEmpty();
        resetBTN.setDisable(true);
        signupBTN.setText("Confirm");
        codeTXF.setText("");
        errorLBL.setText("");
        this.Code = "";
    }

    @FXML
    void signup(ActionEvent event) {
        if (signupBTN.getText().equals("Confirm")) {
            Boolean name = Pattern.matches("[A-Za-z ]{3,30}", nameTXF.getText());
            Boolean fname = Pattern.matches("[A-Za-z ]{3,30}", fnameTXF.getText());
            Boolean check_phone = Pattern.matches("09[0-9]{9}", phoneTXF.getText().toString());
            Boolean checkPass = Pattern.matches("[A-Za-z0-9]{8,30}", passwordTXF.getText().toString());
            boolean checkEmail = Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
                    EmailTXF.getText().toString());
            if (name && fname && check_phone && checkPass && checkEmail) {
                if (checkEmailList(EmailTXF.getText().toString())) {
                    sendEmail(generateNumber(), EmailTXF.getText().toString());
                    errorLBL.setText("");
                    setDisable(true);
                    signupBTN.setText("Verify");
                }
            } else {
                errorLBL.setText("Please Enter Correct inputs");
            }
        } else {
            if (this.Code.equals(codeTXF.getText().toString())) {
                getCounter();
                Delivery selected = new Delivery(idcounter.getDeliveryID(), nameTXF.getText(), fnameTXF.getText(),
                        EmailTXF.getText(), passToHash(passwordTXF.getText().toString()),
                        phoneTXF.getText().toString());
                idcounter.AddDelivery();
                deliveries.add(selected);
                SetInFile.setDelivery(deliveries);
                SetInFile.setCounter(idcounter);
                errorLBL.setText("delivery added!");
                setDisable(false);
                setEmpty();
                signupBTN.setText("Confirm");

            } else {
                errorLBL.setText("Enter A Correct Code");
            }
        }

    }

    private void setEmpty() {
        codeTXF.setText("");
        nameTXF.setText("");
        fnameTXF.setText("");
        passwordTXF.setText("");
        EmailTXF.setText("");
        phoneTXF.setText("");

    }

    private void setDisable(boolean b) {
        codeTXF.setDisable(!b);
        nameTXF.setDisable(b);
        fnameTXF.setDisable(b);
        passwordTXF.setDisable(b);
        EmailTXF.setDisable(b);
        phoneTXF.setDisable(b);
    }

    // generate 4-digit number
    private String generateNumber() {
        String number = "";
        for (int i = 0; i <= 3; i++) {
            int a = rand.nextInt(9);
            number += a;
        }
        this.Code = number;
        return number;
    }

    private void sendEmail(String code, String Email) {
        String to = Email;
        String from = "mohammadghanbari.original@gmail.com";
        String host = "smtp.gmail.com";
        String username = "mohammadghanbari.original";
        String password = "abosakwufkuyzsoa";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Snappfood Verification");
            message.setText(code);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    private void getCounter() {
        try {
            FileInputStream fis = new FileInputStream("counter.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.idcounter = (IDcounter) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    private boolean checkEmailList(String email) {
        for (Costumer costumer : costumers) {
            if (costumer.getUsername().equals(email)) {
                errorLBL.setText("this Email Exist!");
                return false;
            }
        }
        for (Delivery delivery : deliveries) {
            if (delivery.getUsername().equals(email)) {
                errorLBL.setText("this Email Exist!");
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeTXF.setDisable(true);
    }
}
