package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.Order;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AccountRechargePageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    @FXML
    private Label cashLBL;
    @FXML
    private TextField moneyTXF;
    @FXML
    private Label errorLBL;

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        String money = Double.toString(costumer.getMoney());
        cashLBL.setText(money);
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
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @FXML
    void recharge(ActionEvent event) {
        boolean check = Pattern.matches("[0-9]{1,15}", moneyTXF.getText());
        if (check) {
            double rechargemoney = Double.parseDouble(moneyTXF.getText().toString());
            costumer.setMoney(costumer.getMoney() + rechargemoney);
            errorLBL.setText("Account Recharged!");
            String s = Double.toString(costumer.getMoney());
            cashLBL.setText(s);
            SetInFile.setcostumers(costumers);
        } else {
            errorLBL.setText("Enter Correct Numbers!");
            moneyTXF.setText("");
        }

    }
}
