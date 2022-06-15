package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

public class costumerRestaurantPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    @FXML
    private TableView<CafeRestaurant> restaurantTBL;
    private TableColumn<CafeRestaurant, String> name;
    private TableColumn<CafeRestaurant, String> placeName;
    private TableColumn<CafeRestaurant, String> address;
    private TableColumn<CafeRestaurant, CAFEORRESTAURANT> cafeOrRestaurant;
    @FXML
    private Label errorLBL;
    @FXML
    private TextField searchTXF;

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        name = new TableColumn<>("Name");
        placeName = new TableColumn<>("Place");
        address = new TableColumn<>("Address");
        cafeOrRestaurant = new TableColumn<>("Type");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        placeName.setCellValueFactory(new PropertyValueFactory<>("placeName"));
        cafeOrRestaurant.setCellValueFactory(new PropertyValueFactory<>("type"));
        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        restaurantTBL.getColumns().addAll(cafeOrRestaurant, name, placeName, address);
        restaurantTBL.getItems().addAll(cafeRestaurants);
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
    void reset(ActionEvent event) {
        restaurantTBL.getItems().removeAll();
        errorLBL.setText("");
        restaurantTBL.getItems().addAll(cafeRestaurants);
    }

    @FXML
    void search(ActionEvent event) {
        if (!searchTXF.getText().equals("")) {
            restaurantTBL.getItems().clear();
            int counter = 0;
            for (int i = 0; i < cafeRestaurants.size(); i++) {
                if (cafeRestaurants.get(i).getPlaceName().equals(searchTXF.getText())) {
                    restaurantTBL.getItems().add(cafeRestaurants.get(i));
                    counter = counter + 1;
                }
            }
            if (counter == 0) {
                errorLBL.setText("No Restaurant Found In the Entered Location");
            } else {
                errorLBL.setText("");
            }
        } else {
            errorLBL.setText("Enter A Place");
        }

    }

    @FXML
    void buy(ActionEvent event) {
        CafeRestaurant selected = restaurantTBL.getSelectionModel().getSelectedItem();

        if (selected != null) {
            PreOrder preorder = new PreOrder(costumer.getID(), selected.getID(), selected.getName(), 0L,
                    selected.getAddress());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("..\\view\\costumerselectitempage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CostumerAddItemPageController controller = loader.getController();
            boolean flag = false;

            for (int i = 0; i < costumer.getPreOrders().size(); i++) {

                if (costumer.getPreOrders().get(i).getCafeRestaurantID() == selected.getID()) {
                    flag = true;
                    preorder = costumer.getPreOrders().get(i);
                    break;
                }
            }
            if (!flag) {
                costumer.getPreOrders().add(preorder);
            }
            SetInFile.setcostumers(costumers);
            controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers, selected, preorder);
            this.stage.setScene(new Scene(loader.getRoot()));
        }
    }
}
