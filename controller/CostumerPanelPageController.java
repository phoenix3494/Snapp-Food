package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.Order;

import java.io.IOException;
import java.util.ArrayList;

public class CostumerPanelPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private  ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();

    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();

    public void initfunction(Stage stage ,Costumer costumer ,ArrayList<Order> orders ,ArrayList<CafeRestaurant> cafeRestaurants,ArrayList<Costumer> costumers )
    {
        this.stage = stage;
        this.costumer =costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
    }
    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumermainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CostumerMainPageController controller = loader.getController();
        controller.initfunction(stage,costumer,orders,cafeRestaurants,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void discountcodes(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\discountpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DiscountCodePageController controller = loader.getController();
        controller.initfunction(stage,costumer,orders,cafeRestaurants,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void history(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\history.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HistoryPageController controller = loader.getController();
        controller.initfunction(stage,costumer,orders,cafeRestaurants,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @FXML
    void invite(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\invitefriend.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InvitePageController controller = loader.getController();
        controller.initfunction(stage,costumer,orders,cafeRestaurants,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void recharge(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\accountrecharge.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AccountRechargePageController controller = loader.getController();
        controller.initfunction(stage,costumer,orders,cafeRestaurants,costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }
}
