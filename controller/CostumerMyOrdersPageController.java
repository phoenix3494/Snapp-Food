package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.Order;
import model.OrderStatus;

public class CostumerMyOrdersPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    @FXML
    private TableView<Order> orderTBL;
    private TableColumn<Order, String> restaurantName;
    private TableColumn<Order, Integer> numberOfItems;
    private TableColumn<Order, Date> date;
    private TableColumn<Order, OrderStatus> orderStatus;
    private TableColumn<Order, String> senderAddress;
    private TableColumn<Order, Double> price;
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        restaurantName = new TableColumn<>("Restaurant's Name");
        numberOfItems = new TableColumn<>("Number Of Items");
        date = new TableColumn<>("Order's Date");
        orderStatus = new TableColumn<>("Order Status");
        price = new TableColumn<>("Price");
        senderAddress = new TableColumn<>("Restaurant Address");
        restaurantName.setCellValueFactory(new PropertyValueFactory<>("RestaurantName"));
        numberOfItems.setCellValueFactory(new PropertyValueFactory<>("numberOfItems"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        senderAddress.setCellValueFactory(new PropertyValueFactory<>("SenderAddress"));
        orderTBL.getColumns().addAll(restaurantName, numberOfItems, date, orderStatus, price, senderAddress);
        orderTBL.getItems().addAll(costumer.getOrders());
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
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void show(ActionEvent event) {
        Order order = orderTBL.getSelectionModel().getSelectedItem();
        if (order != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("..\\view\\costumermyordersitems.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CostumerMyOderItemsPageController controller = loader.getController();
            controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers, order);
            this.stage.setScene(new Scene(loader.getRoot()));
        }

    }

}
