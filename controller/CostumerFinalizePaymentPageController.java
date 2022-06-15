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

public class CostumerFinalizePaymentPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private  ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private PreOrder selectedPreOrder;
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    @FXML
    private TableView<PreOrder> ItemsTBL;
    @FXML
    private TableView<Item> myItemsTBL;
    @FXML
    private TextField discountCodeTXF;

    @FXML
    private Label errorLBL;

    @FXML
    private TableView<?> itemsTBL;

    @FXML
    private Label mymoneyLBL;

    @FXML
    private TextField addressTXF;
    @FXML
    private Label priceLBL;
    private TableColumn<Item,String> myitemName;
    private TableColumn<Item,Double> myitemPrice;


    private double orderPrice;
    private double priceAfterDiscount;
    private DiscountCode discountCode;
    private int discountPercentage = 0;


    public void initfunction(Stage stage ,Costumer costumer ,ArrayList<Order> orders ,ArrayList<CafeRestaurant> cafeRestaurants,ArrayList<Costumer> costumers,PreOrder selectedPreOrder )
    {
        this.stage = stage;
        this.costumer =costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        this.selectedPreOrder = selectedPreOrder;
        myitemName.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        myitemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        myItemsTBL.getColumns().addAll(myitemName,myitemPrice);
        myItemsTBL.getItems().addAll(selectedPreOrder.getItems());
        this.orderPrice = selectedPreOrder.getPrice();
        this.priceAfterDiscount = selectedPreOrder.getPrice();
        String s = Double.toString(orderPrice);
        String d = Double.toString(costumer.getMoney());
        this.priceLBL.setText(s);
        this.mymoneyLBL.setText(d);

    }
    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\costumerrestaurantpage.fxml"));
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
    void applyDiscount(ActionEvent event) {
            String discount = discountCodeTXF.getText().toString();
            for(int i = 0 ; i<costumer.getCostumerDiscounts().size() ; i++)
            {
                if(costumer.getCostumerDiscounts().get(i).getCode().equals(discount))
                {
                    errorLBL.setText("Discount Code Applied");
                    this.discountPercentage = costumer.getCostumerDiscounts().get(i).getDiscountPercentage();
                    this.discountCode = costumer.getCostumerDiscounts().get(i);


                }
            }
    }

    @FXML
    void buy(ActionEvent event) {

    }



}
