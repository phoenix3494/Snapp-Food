package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminAddItemPageController implements Initializable {
    Stage stage;
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private IDcounter idcounter = new IDcounter();
    private FoodCatagory selectedCategory;
    private CafeRestaurant selectedRestaurant;
    @FXML
    private Button addBTN;

    @FXML
    private VBox addItemVBX;

    @FXML
    private Button backBTN;

    @FXML
    private Button cancelBTN;

    @FXML
    private Button confirmBTN;

    @FXML
    private TableView<Item> itemTBL;
    private TableColumn<Item, String> myitemName;
    private TableColumn<Item, Double> myitemPrice;

    @FXML
    private TextField nameTXF;

    @FXML
    private Label errorLBL;

    @FXML
    private TextField priceTXF;

    public void initfunction(Stage stage, ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Delivery> deliveries,
            ArrayList<Costumer> costumers, CafeRestaurant selectedRestaurant, FoodCatagory selectedCategory) {
        this.stage = stage;
        this.deliveries = deliveries;
        this.costumers = costumers;
        this.cafeRestaurants = cafeRestaurants;
        this.selectedRestaurant = selectedRestaurant;
        this.selectedCategory = selectedCategory;
        myitemName = new TableColumn<>("Food");
        myitemPrice = new TableColumn<>("Price");
        myitemName.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        myitemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemTBL.getColumns().addAll(myitemName, myitemPrice);
        itemTBL.getItems().addAll(selectedCategory.getItems());
        getCounter();
    }

    @FXML
    void add(ActionEvent event) {
        DisableOrEnable(false);
    }

    @FXML
    void cancel(ActionEvent event) {
        DisableOrEnable(true);
    }

    @FXML
    void confirm(ActionEvent event) {
        Boolean checkName = Pattern.matches("[a-zA-Z0-9 ]{3,35}", nameTXF.getText());
        Boolean checkPrice = Pattern.matches("[0-9.]{1,10}", priceTXF.getText());
        if (checkName && checkPrice) {
            if (checkItemName(nameTXF.getText())) {
                Item item = new Item(nameTXF.getText(), Double.parseDouble(priceTXF.getText()),
                        selectedCategory.getID(), idcounter.getItemsID(), selectedRestaurant.getID(),
                        selectedRestaurant.getName());
                idcounter.addItem();
                this.selectedCategory.getItems().add(item);
                itemTBL.getItems().add(item);
                SetInFile.setCounter(idcounter);
                SetInFile.setCafeRestaurants(cafeRestaurants);
            } else {
                errorLBL.setText("This Item Exist!");
            }
        } else {
            errorLBL.setText("Please Enter Correct Name & Price");
        }

    }

    private boolean checkItemName(String name) {
        for (int i = 0; i < selectedCategory.getItems().size(); i++) {
            if (selectedCategory.getItems().get(i).getItem_name().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public void DisableOrEnable(boolean b) {
        addItemVBX.setDisable(b);
        addItemVBX.setVisible(!b);
        addBTN.setDisable(!b);
        errorLBL.setText("");
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

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\addcategory.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddCategoryPageController controller = loader.getController();
        controller.initfunction(stage, cafeRestaurants, deliveries, costumers, selectedRestaurant);
        this.stage.setScene(new Scene(loader.getRoot()));

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        addItemVBX.setDisable(true);
        addItemVBX.setVisible(false);

    }
}
