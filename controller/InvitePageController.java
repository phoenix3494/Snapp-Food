package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;
public class InvitePageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private  ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();

    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();

    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
    @FXML
    private TextField emailTXF;
    @FXML
    private Label errorLBL;

    public void initfunction(Stage stage ,Costumer costumer ,ArrayList<Order> orders ,ArrayList<CafeRestaurant> cafeRestaurants,ArrayList<Costumer> costumers )
    {
        this.stage = stage;
        this.costumer =costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        getDeliveries();
        getInvitations();
    }
    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumerpanel.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CostumerPanelPageController controller = loader.getController();
        controller.initfunction(stage,costumer,orders,cafeRestaurants,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void invite(ActionEvent event) {
        boolean checkEmail = Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
                emailTXF.getText());
        if(checkEmail)
        {
            if(checkEmailExistance(emailTXF.getText()))
            {
                String code = getCode();
                for(int i = 0 ; i<invitations.size() ; i++)
                {
                    if(invitations.get(i).getInvitedEmail().equals(emailTXF.getText()) && invitations.get(i).getInviterEmail().equals(costumer.getUsername()))
                    {
                            invitations.get(i).setInvitationCode(code);
                            sendEmail(code,emailTXF.getText());
                            errorLBL.setText("The invitation code sended!");
                            SetInFile.setinvitations(invitations);
                            return;

                    }
                }
                invitations.add(new Invitation(costumer.getID(),costumer.getUsername(),emailTXF.getText(),code));
                sendEmail(code,emailTXF.getText());
                SetInFile.setinvitations(invitations);
                errorLBL.setText("The invitation code sended!");
            }
        }
        else
        {
            errorLBL.setText("Enter A Correct Email");
        }
    }

    private boolean checkEmailExistance(String mail)
    {
        for(Costumer cos : costumers)
        {
            if(cos.getUsername().equals(mail))
            {
                errorLBL.setText("this Email Exist in system!");
                return  false;

            }
        }
        for(Delivery del : deliveries )
        {
            if(del.getUsername().equals(mail))
            {
                errorLBL.setText("this Email Exist in system!");
                return false;
            }
        }
        return true;
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
            message.setSubject("Snappfood Invitation");
            message.setText(code);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
    private String getCode()
    {
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
    private void getInvitations()
    {
        try {
            FileInputStream fis = new FileInputStream("invitations.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.invitations = (ArrayList<Invitation>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
