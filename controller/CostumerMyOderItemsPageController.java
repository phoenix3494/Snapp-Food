package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CAFEORRESTAURANT;
import model.CafeRestaurant;
import model.Costumer;
import model.FoodCatagory;
import model.Order;
import model.OrderItem;
import model.OrderStatus;

public class CostumerMyOderItemsPageController implements Initializable {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    Order selectedOrder;
    OrderItem selectedItem;
    @FXML
    private RadioButton fiveRB;
    @FXML
    private RadioButton fourRB;
    @FXML
    private TableView<OrderItem> itemTBL;
    private TableColumn<OrderItem, String> name;
    private TableColumn<OrderItem, Double> itemrating;
    private TableColumn<OrderItem, Double> price;
    @FXML
    private RadioButton oneRB;

    @FXML
    private ToggleGroup rate;

    @FXML
    private VBox rateVBX;

    @FXML
    private RadioButton threeRB;

    @FXML
    private RadioButton twoRB;
    int Score = 0;

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers, Order selectedOrder) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        this.selectedOrder = selectedOrder;
        name = new TableColumn<>("Item's Name");
        itemrating = new TableColumn<>("My Rating");
        price = new TableColumn<>("Price");
        name.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        itemrating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemTBL.getColumns().addAll(name, price, itemrating);
        itemTBL.getItems().addAll(selectedOrder.getItems());

    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumermyorders.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CostumerMyOrdersPageController controller = loader.getController();
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void cancel(ActionEvent event) {
        rateVBX.setDisable(true);
        rateVBX.setVisible(false);
        selectedItem = null;
    }

    @FXML
    void clickitems(MouseEvent event) {
        OrderItem selected = itemTBL.getSelectionModel().getSelectedItem();
        if (!selectedOrder.getStatus().equals(OrderStatus.Delivered)) {
            return;
        }
        if (selected != null && selected.getRating() == 0 && selectedOrder.getStatus().equals(OrderStatus.Delivered)) {
            rateVBX.setDisable(false);
            rateVBX.setVisible(true);
            selectedItem = selected;
        }
    }

    @FXML
    void confirm(ActionEvent event) {
        if (oneRB.isSelected()) {
            Score = 1;
        } else if (twoRB.isSelected()) {
            Score = 2;
        } else if (threeRB.isSelected()) {
            Score = 3;
        } else if (fourRB.isSelected()) {
            Score = 4;
        } else if (fiveRB.isSelected()) {
            Score = 5;
        }
        selectedItem.setRating(Score);
        addRating(Score);
        SetInFile.setCafeRestaurants(cafeRestaurants);
        SetInFile.setcostumers(costumers);
        SetInFile.setOrders(orders);
        Score = 0;
        itemTBL.getItems().clear();
        itemTBL.getItems().addAll(selectedOrder.getItems());
        cancel(event);
    }

    private void addRating(int rate) {
        FoodCatagory cat;
        int restaurantIndex = 0;
        int categoryIndex = 0;
        int foodIndex = 0;
        for (int i = 0; i < cafeRestaurants.size(); i++) {
            if (selectedItem.getRestaurantId() == cafeRestaurants.get(i).getID()) {
                restaurantIndex = i;
                break;
            }
        }
        CafeRestaurant res = cafeRestaurants.get(restaurantIndex);
        for (int i = 0; i < res.getFoods().size(); i++) {
            if (res.getFoods().get(i).getID() == selectedItem.getFoodCatagoryID()) {
                categoryIndex = i;
                break;
            }
        }
        for (int i = 0; i < res.getFoods().get(categoryIndex).getItems().size(); i++) {
            if (selectedItem.getItemID() == res.getFoods().get(categoryIndex).getItems().get(i).getItemID()) {
                foodIndex = i;
                break;
            }
        }
        cafeRestaurants.get(categoryIndex).getFoods().get(categoryIndex).getItems().get(foodIndex).addScore(rate);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        rateVBX.setDisable(true);
        rateVBX.setVisible(false);
    }
}
