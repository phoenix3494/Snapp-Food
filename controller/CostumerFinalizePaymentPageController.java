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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CostumerFinalizePaymentPageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private PreOrder selectedPreOrder;
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    @FXML
    private TextField discountCodeTXF;

    @FXML
    private Label errorLBL;

    @FXML
    private TableView<Item> itemsTBL;

    @FXML
    private Label mymoneyLBL;

    @FXML
    private TextField addressTXF;
    @FXML
    private Label priceLBL;
    private TableColumn<Item, String> myitemName;
    private TableColumn<Item, Double> myitemPrice;
    private double orderPrice;
    private double priceAfterDiscount;
    private DiscountCode discountCode = null;
    private int discountPercentage = 0;
    private IDcounter idcounter = new IDcounter();

    public void initfunction(Stage stage, Costumer costumer, ArrayList<Order> orders,
            ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Costumer> costumers, PreOrder selectedPreOrder) {
        this.stage = stage;
        this.costumer = costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        this.selectedPreOrder = selectedPreOrder;
        myitemName = new TableColumn<>("Name");
        myitemPrice = new TableColumn<>("Price");
        myitemName.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        myitemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemsTBL.getColumns().addAll(myitemName, myitemPrice);
        itemsTBL.getItems().addAll(selectedPreOrder.getItems());
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
        loader.setLocation(getClass().getResource("..\\view\\costumercart.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CostumerCartPageController controller = loader.getController();
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void applyDiscount(ActionEvent event) {
        String discount = discountCodeTXF.getText().toString();
        for (int i = 0; i < costumer.getCostumerDiscounts().size(); i++) {
            if (costumer.getCostumerDiscounts().get(i).getCode().equals(discount)) {
                errorLBL.setText("Discount Code Applied");
                this.discountPercentage = costumer.getCostumerDiscounts().get(i).getDiscountPercentage();
                this.discountCode = costumer.getCostumerDiscounts().get(i);
                this.priceAfterDiscount = priceAfterDiscountttt(this.orderPrice, this.discountPercentage);
                priceLBL.setText(Double.toString(priceAfterDiscount));
                return;
            }
        }
        errorLBL.setText("Invalid Discount code");
    }

    @FXML
    void buy(ActionEvent event) {
        if (priceAfterDiscount > costumer.getMoney()) {
            errorLBL.setText("You dont have Enough Money! go and charge your account!");
            return;
        }
        if (addressTXF.getText().toString().equals("")) {
            errorLBL.setText("Please Enter an Address");
            return;
        }
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (int i = 0; i < selectedPreOrder.getItems().size(); i++) {
            orderItems
                    .add(new OrderItem(
                            priceAfterDiscountttt(selectedPreOrder.getItems().get(i).getPrice(),
                                    this.discountPercentage),
                            selectedPreOrder.getItems().get(i).getItemID(),
                            selectedPreOrder.getItems().get(i).getFoodCatagoryID(),
                            selectedPreOrder.getItems().get(i).getRestaurantID(),
                            selectedPreOrder.getItems().get(i).getItem_name()));
        }
        getCounter();
        Order order = new Order(idcounter.getOrderID(), costumer.getID(), selectedPreOrder.getCafeRestaurantAddress(),
                addressTXF.getText(),
                selectedPreOrder.getCafeRestaurantName(), orderItems, this.priceAfterDiscount, costumer.getName(),
                costumer.getLastName(), costumer.getPhoneNumber());
        costumer.setMoney(costumer.getMoney() - priceAfterDiscount);
        if (discountCode != null) {
            costumer.getCostumerDiscounts().remove(discountCode);
        }
        idcounter.AddOrder();
        orders.add(order);
        costumer.getOrders().add(order);
        goToThanksPage();
    }

    private double priceAfterDiscountttt(double price, int discount) {
        return price - ((price * discount) / 100);
    }

    private void goToThanksPage() {
        costumer.getPreOrders().remove(this.selectedPreOrder);
        SetInFile.setCounter(idcounter);
        SetInFile.setcostumers(costumers);
        SetInFile.setOrders(orders);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\thanks.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ThanksPageController controller = loader.getController();
        controller.initfunction(stage, costumer, orders, cafeRestaurants, costumers);
        this.stage.setScene(new Scene(loader.getRoot()));
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

}
