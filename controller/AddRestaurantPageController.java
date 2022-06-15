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

public class AddRestaurantPageController implements Initializable {
    Stage stage;
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();

    private IDcounter idcounter = new IDcounter();

    @FXML
    private Button addBTN;

    @FXML
    private RadioButton restaurantRB;
    @FXML
    private RadioButton cafeRB;

    @FXML
    private VBox addRestaurantVBX;

    @FXML
    private TextField addressTXF;

    @FXML
    private Button backBTN;

    @FXML
    private Button cancelBTN;

    @FXML
    private Button confirmBTN;

    @FXML
    private TextField nameTXF;

    @FXML
    private TextField placeTXF;

    @FXML
    private TableView<CafeRestaurant> restaurantTBL;
    private TableColumn<CafeRestaurant, String> name;
    private TableColumn<CafeRestaurant, String> placeName;
    private TableColumn<CafeRestaurant, String> address;
    private TableColumn<CafeRestaurant, CAFEORRESTAURANT> cafeOrRestaurant;

    @FXML
    private Button statusBTN;

    @FXML
    private ToggleGroup type;

    @FXML
    private Label errorLBL;

    public void initfunction(Stage stage, ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Delivery> deliveries,
            ArrayList<Costumer> costumers) {
        this.stage = stage;
        this.deliveries = deliveries;
        this.costumers = costumers;
        this.cafeRestaurants = cafeRestaurants;
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
        getCounter();
    }

    @FXML
    void add(ActionEvent event) {
        addRestaurantVBX.setDisable(false);
        addRestaurantVBX.setVisible(true);
        addBTN.setDisable(true);
        statusBTN.setDisable(true);
    }

    @FXML
    void cancel(ActionEvent event) {
        reset();
        addRestaurantVBX.setDisable(true);
        addRestaurantVBX.setVisible(false);
        addBTN.setDisable(false);
        statusBTN.setDisable(false);
    }

    @FXML
    void confirm(ActionEvent event) {
        Boolean name = Pattern.matches("[A-Za-z0-9 ]{3,100}", nameTXF.getText());
        Boolean placename = Pattern.matches("[A-Za-z0-9 ]{3,100}", placeTXF.getText());
        Boolean address = Pattern.matches("[A-Za-z0-9 ]{3,100}", addressTXF.getText());
        if (name && placename && address
                && checkRestaurant(nameTXF.getText(), placeTXF.getText(), addressTXF.getText())) {
            if (cafeRB.isSelected()) {
                CafeRestaurant restaurant = new CafeRestaurant(CAFEORRESTAURANT.Cafe, idcounter.getCafeRestaurantID(),
                        placeTXF.getText(), addressTXF.getText(), nameTXF.getText());
                cafeRestaurants.add(restaurant);
                restaurantTBL.getItems().add(restaurant);
                errorLBL.setText("Cafe Added!");
            } else {
                CafeRestaurant restaurant = new CafeRestaurant(CAFEORRESTAURANT.Restaurant,
                        idcounter.getCafeRestaurantID(), placeTXF.getText(), addressTXF.getText(), nameTXF.getText());
                errorLBL.setText("Restaurant Added");
                restaurantTBL.getItems().add(restaurant);
                cafeRestaurants.add(restaurant);
            }
            idcounter.AddCafeRestaurant();
            SetInFile.setCounter(idcounter);
            SetInFile.setCafeRestaurants(cafeRestaurants);
            reset();
            addRestaurantVBX.setDisable(true);
            addRestaurantVBX.setVisible(false);
            addBTN.setDisable(false);
            statusBTN.setDisable(false);
        } else {
            errorLBL.setText("Please Enter Correct Inputs");
        }
    }

    private boolean checkRestaurant(String name, String place, String address) {
        for (CafeRestaurant res : cafeRestaurants) {
            if (res.getName().equals(name) && res.getPlaceName().equals(place) && res.getAddress().equals(address)) {
                errorLBL.setText("This restaurant exist in this location!");
                return false;
            }
        }
        return true;
    }

    @FXML
    void status(ActionEvent event) {
        CafeRestaurant selectedCafeRestaurant = restaurantTBL.getSelectionModel().getSelectedItem();
        if (selectedCafeRestaurant != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("..\\view\\addcategory.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AddCategoryPageController controller = loader.getController();
            controller.initfunction(stage, cafeRestaurants, deliveries, costumers, selectedCafeRestaurant);
            this.stage.setScene(new Scene(loader.getRoot()));
        }
    }

    private void reset() {
        nameTXF.setText("");
        addressTXF.setText("");
        placeTXF.setText("");
    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\adminmainpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminMainPageController controller = loader.getController();
        controller.initfunction(stage, cafeRestaurants, deliveries, costumers);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRestaurantVBX.setDisable(true);
        addRestaurantVBX.setVisible(false);

    }
}