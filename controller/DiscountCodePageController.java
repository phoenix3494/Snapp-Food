package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.CafeRestaurant;
import model.Costumer;
import model.DiscountCode;
import model.Order;

import java.io.IOException;
import java.util.ArrayList;
public class DiscountCodePageController {
    private Stage stage;
    private Costumer costumer;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private  ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    @FXML
    private TableView<DiscountCode> codeTBL;
    @FXML
    private TextField codeTXF;
    private TableColumn<DiscountCode,String> discountcode;
    private TableColumn<DiscountCode,Integer> percentage;
    public void initfunction(Stage stage ,Costumer costumer ,ArrayList<Order> orders ,ArrayList<CafeRestaurant> cafeRestaurants,ArrayList<Costumer> costumers )
    {
        this.stage = stage;
        this.costumer =costumer;
        this.orders = orders;
        this.cafeRestaurants = cafeRestaurants;
        this.costumers = costumers;
        discountcode = new TableColumn<>("Discount Code");
        percentage = new TableColumn<>("Percentage");
        discountcode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        percentage.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));
        codeTBL.getColumns().addAll(discountcode,percentage);
        codeTBL.getItems().addAll(costumer.getCostumerDiscounts());
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
    void clickTBL(MouseEvent event) {
        DiscountCode selected = codeTBL.getSelectionModel().getSelectedItem();
        if(selected != null)
        {
            codeTXF.setText(selected.getCode());
        }
    }
}
