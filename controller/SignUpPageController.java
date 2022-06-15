package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.*;

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

public class SignUpPageController implements Initializable {
    Stage stage;
    private IDcounter idcounter = new IDcounter();
    private String Code;
    Random rand = new Random();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<CafeRestaurant> restaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
    @FXML
    private Label errorLBL;
    @FXML
    private Button resetBTN;

    @FXML
    private Button signupBTN;
    @FXML
    private TextField EmailTXF;

    @FXML
    private TextField codeTXF;

    @FXML
    private TextField fnameTXF;

    @FXML
    private TextField invitationTXF;

    @FXML
    private TextField nameTXF;

    @FXML
    private PasswordField passwordTXF;

    @FXML
    private TextField phoneTXF;
    String inviteCode = "";
    boolean inviteIsCorrect = false;
    boolean invitationCodeIsEmpty = true;
    Invitation invite;

    public void initfunction(Stage stage) {
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
    void reset(ActionEvent event) {
        codeTXF.setText("");
        passwordTXF.setText("");
        nameTXF.setText("");
        fnameTXF.setText("");
        invitationTXF.setText("");
        EmailTXF.setText("");
        phoneTXF.setText("");
        this.invite = null;
        signupBTN.setText("Sign Up");
        resetBTN.setDisable(true);
        setDisable(false);
        errorLBL.setText("");

    }

    private void setDisable(boolean b) {
        codeTXF.setDisable(!b);
        nameTXF.setDisable(b);
        fnameTXF.setDisable(b);
        passwordTXF.setDisable(b);
        EmailTXF.setDisable(b);
        phoneTXF.setDisable(b);
        invitationTXF.setDisable(b);
    }

    private boolean checkInvitationCode(String email, String code) {
        for (int i = 0; i < invitations.size(); i++) {
            if (email.equals(invitations.get(i).getInvitedEmail())
                    && code.equals(invitations.get(i).getInvitationCode())) {
                this.invite = invitations.get(i);
                return true;
            }
        }
        return false;
    }

    @FXML
    void signup(ActionEvent event) {
        if (signupBTN.getText().equals("Sign Up")) {
            Boolean name = Pattern.matches("[A-Za-z ]{3,30}", nameTXF.getText());
            Boolean fname = Pattern.matches("[A-Za-z ]{3,30}", fnameTXF.getText());
            Boolean check_phone = Pattern.matches("09[0-9]{9}", phoneTXF.getText().toString());
            Boolean checkPass = Pattern.matches("[A-Za-z0-9]{8,30}", passwordTXF.getText().toString());
            boolean checkEmail = Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
                    EmailTXF.getText().toString());
            if (name && fname && check_phone && checkPass && checkEmail) {
                if (checkEmailList(EmailTXF.getText().toString())) {
                    if (invitationTXF.getText().equals("")) {
                        invitationCodeIsEmpty = true;
                        inviteIsCorrect = false;
                        invite = null;
                    }

                    if (!invitationTXF.getText().equals("")
                            && checkInvitationCode(EmailTXF.getText(), invitationTXF.getText())) {
                        this.inviteCode = invitationTXF.getText();
                        invitationCodeIsEmpty = false;
                        inviteIsCorrect = true;
                    }
                    if (!invitationTXF.getText().equals("")
                            && !checkInvitationCode(EmailTXF.getText(), invitationTXF.getText())) {
                        errorLBL.setText("invitation code is invalid");
                        return;
                    }
                    sendEmail(generateNumber(), EmailTXF.getText());
                    errorLBL.setText("");
                    signupBTN.setText("Verify");
                    setDisable(true);
                    resetBTN.setDisable(false);

                } else {
                    errorLBL.setText("This Email Exists");
                }
            } else {
                errorLBL.setText("Please Enter Correct inputs");
            }
        } else {
            if (this.Code.equals(codeTXF.getText().toString())) {
                getCounter();
                Costumer costumer = new Costumer(idcounter.getCostumerID(), nameTXF.getText(), fnameTXF.getText(),
                        EmailTXF.getText(), passToHash(passwordTXF.getText()), phoneTXF.getText());
                costumers.add(costumer);
                idcounter.AddCostumer();
                if (invite != null) {
                    addDiscountCode(invite.getInviterEmail(), EmailTXF.getText());
                }
                removeInvitations(EmailTXF.getText());
                SetInFile.setCounter(idcounter);
                SetInFile.setcostumers(costumers);
                SetInFile.setinvitations(invitations);
                goToCostumerMenu(costumer);
                errorLBL.setText("delivery added!");
                setDisable(false);
                signupBTN.setText("Confirm");

            } else {
                errorLBL.setText("Enter A Correct Code");
            }
        }
    }

    private void addDiscountCode(String inviterEmail, String invitedEmail) {
        for (int i = 0; i < costumers.size(); i++) {
            if (inviterEmail.equals(costumers.get(i).getUsername())) {
                costumers.get(i).getCostumerDiscounts().add(new DiscountCode(getCode(), 15));
                break;
            }
        }
        for (int i = 0; i < costumers.size(); i++) {
            if (invitedEmail.equals(costumers.get(i).getUsername())) {
                costumers.get(i).getCostumerDiscounts().add(new DiscountCode(getCode(), 20));
                break;
            }
        }
    }

    private String getCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private void goToCostumerMenu(Costumer costumer) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumermainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CostumerMainPageController controller = loader.getController();
        controller.initfunction(stage, costumer, orders, restaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
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

    // this method sends Email to the entered Email
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
            message.setSubject("Snapp!Food Verification");
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

    private void removeInvitations(String email) {
        ArrayList<Invitation> deletedInvitations = new ArrayList<Invitation>();
        for (int i = 0; i < invitations.size(); i++) {
            if (invitations.get(i).getInvitedEmail().equals(email)) {
                deletedInvitations.add(invitations.get(i));
            }
        }
        invitations.removeAll(deletedInvitations);
    }

    private void getCostumers() {
        try {
            FileInputStream fis = new FileInputStream("costumers.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.costumers = (ArrayList<Costumer>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getDeliveries() {
        try {
            FileInputStream fis = new FileInputStream("deliveries.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.deliveries = (ArrayList<Delivery>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getRestaurants() {
        try {
            FileInputStream fis = new FileInputStream("CafeRestaurants.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.restaurants = (ArrayList<CafeRestaurant>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getOrders() {
        try {
            FileInputStream fis = new FileInputStream("orders.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.orders = (ArrayList<Order>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getInvitations() {
        try {
            FileInputStream fis = new FileInputStream("invitations.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.invitations = (ArrayList<Invitation>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNumber() {
        String number = "";
        for (int i = 0; i <= 3; i++) {
            int a = rand.nextInt(9);
            number += a;
        }
        this.Code = number;
        return number;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetBTN.setDisable(true);
        getCostumers();
        getDeliveries();
        getRestaurants();
        getOrders();
        getInvitations();
        codeTXF.setDisable(true);
    }
}
