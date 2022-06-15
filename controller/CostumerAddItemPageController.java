package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.util.ArrayList;

public class CostumerAddItemPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
    @FXML
    private TableView<FoodCatagory> categoryTBL;
    private TableColumn<FoodCatagory, String> categoryName;
    @FXML
    private TableView<Item> itemsTBL;
    private TableColumn<Item, String> itemName;
    private TableColumn<Item, Double> itemPrice;
    private TableColumn<Item, Double> rating;
    @FXML
    private TableView<Item> myItemsTBL;
    private TableColumn<Item, String> myitemName;
    private TableColumn<Item, Double> myitemPrice;
    CafeRestaurant selectedRestaurant;
    ArrayList<Item> items = new ArrayList<Item>();
    PreOrder preorder;

    public CostumerAddItemPageController() {
    }

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers, CafeRestaurant selectedRestaurant,
            PreOrder preorder) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        this.selectedRestaurant = selectedRestaurant;
        this.preorder = preorder;
        categoryName = new TableColumn<>("Category Name");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("foodCatagoryName"));
        categoryTBL.getColumns().add(categoryName);
        categoryTBL.getItems().addAll(selectedRestaurant.getFoods());
        itemName = new TableColumn<>("Food");
        itemPrice = new TableColumn<>("Price");
        rating = new TableColumn<>("Score");
        itemName.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        rating.setCellValueFactory(new PropertyValueFactory<>("overallrating"));
        itemsTBL.getColumns().addAll(itemName, itemPrice, rating);
        myitemName = new TableColumn<>("Food");
        myitemPrice = new TableColumn<>("Price");
        myitemName.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        myitemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        myItemsTBL.getColumns().addAll(myitemName, myitemPrice);
        myItemsTBL.getItems().addAll(preorder.getItems());
    }

    @FXML
    void back(ActionEvent event) {
        if (preorder.getItems().size() == 0) {
            costumer.getPreOrders().remove(preorder);
        } else {
            double price = 0;
            for (int i = 0; i < preorder.getItems().size(); i++) {
                price = price + preorder.getItems().get(i).getPrice();
            }
            preorder.setPrice(price);
        }
        SetInFile.setcostumers(costumers);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumerrestaurantpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        costumerRestaurantPageController controller = loader.getController();
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void add(ActionEvent event) {
        Item selectedItem = itemsTBL.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            preorder.getItems().add(selectedItem);
            myItemsTBL.getItems().add(selectedItem);
        }
    }

    @FXML
    void clickCategoryTable(MouseEvent event) {
        FoodCatagory selectedCategory = categoryTBL.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            itemsTBL.getItems().removeAll();
            itemsTBL.getItems().addAll(selectedCategory.getItems());
        }
    }
}
