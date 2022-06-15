package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class HistoryPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private  ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    @FXML
    private TableView<Order> orderTBL;
    private TableColumn<Order,String> restaurant;
    private TableColumn<Order,Double> price;
    private TableColumn<Order, Date> date;
    private TableColumn<Order,Integer> numberOfItems;
    private TableColumn<Order,String> senderAddress;

    private TableColumn<Order, OrderStatus> status;

    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();

    public void initfunction(Stage stage ,Costumer costumer ,ArrayList<Order> orders ,ArrayList<CafeRestaurant> cafeRestaurants,ArrayList<Costumer> costumers )
    {
        this.stage = stage;
        this.costumer =costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        restaurant = new TableColumn<>("Restaurant's Name");
        price = new TableColumn<>("Price");
        date = new TableColumn<>("Date");
        numberOfItems = new TableColumn<>("Number Of Items");
        senderAddress = new TableColumn<>("Restaurant's Address");
        status = new TableColumn<>("Status");
        restaurant.setCellValueFactory(new PropertyValueFactory<>("RestaurantName"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        numberOfItems.setCellValueFactory(new PropertyValueFactory<>("numberOfItems"));
        senderAddress.setCellValueFactory(new PropertyValueFactory<>("SenderAddress"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTBL.getColumns().addAll(restaurant,numberOfItems,price,date,status,senderAddress);
        orderTBL.getItems().addAll(costumer.getOrders());
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
    void checkItems(ActionEvent event) {
        Order Selected = orderTBL.getSelectionModel().getSelectedItem();
        if(Selected !=null)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("..\\view\\itemshistory.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ItemsHistoryPageController controller = loader.getController();
            controller.initfunction(stage,costumer,orders,cafeRestaurants,costumers,Selected);
            this.stage.setScene(new Scene(loader.getRoot()));
        }

    }

}
