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

public class AddCategoryPageController implements Initializable {
    Stage stage;
    @FXML
    private Button addBTN;

    @FXML
    private VBox addCategoryVBX;

    @FXML
    private Button backBTN;

    @FXML
    private Button cancelBTN;

    @FXML
    private TableView<FoodCatagory> categoryTBL;

    @FXML
    private Button confirmBTN;

    @FXML
    private Label errorLBL;

    @FXML
    private TextField nameTXF;

    @FXML
    private Button statusBTN;
    private ArrayList<CafeRestaurant> cafeRestaurants = new ArrayList<CafeRestaurant>();
    private ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
    private ArrayList<Costumer> costumers = new ArrayList<Costumer>();
    private TableColumn<FoodCatagory, String> foodCtagoryName;
    private IDcounter idcounter = new IDcounter();
    private CafeRestaurant selectedRestaurant;

    public void initfunction(Stage stage, ArrayList<CafeRestaurant> cafeRestaurants, ArrayList<Delivery> deliveries,
            ArrayList<Costumer> costumers, CafeRestaurant selectedRestaurant) {
        this.stage = stage;
        this.deliveries = deliveries;
        this.costumers = costumers;
        this.cafeRestaurants = cafeRestaurants;
        this.selectedRestaurant = selectedRestaurant;
        foodCtagoryName = new TableColumn<>("foodCatagory Name");
        foodCtagoryName.setCellValueFactory(new PropertyValueFactory<>("foodCatagoryName"));
        categoryTBL.getColumns().addAll(foodCtagoryName);
        categoryTBL.getItems().addAll(selectedRestaurant.getFoods());
        getCounter();
    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\adminrestaurantspage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddRestaurantPageController controller = loader.getController();
        controller.initfunction(stage, cafeRestaurants, deliveries, costumers);

        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void add(ActionEvent event) {
        addCategoryVBX.setVisible(true);
        addCategoryVBX.setDisable(false);
        addBTN.setDisable(true);
        statusBTN.setDisable(true);
    }

    @FXML
    void cancel(ActionEvent event) {
        addCategoryVBX.setVisible(false);
        addCategoryVBX.setDisable(true);
        reset();
        addBTN.setDisable(false);
        statusBTN.setDisable(false);
        errorLBL.setText("");
    }

    @FXML
    void confirm(ActionEvent event) {
        String name = nameTXF.getText();
        if (name.equals("")) {
            errorLBL.setText("Enter A name!");
            return;
        }
        if (checkName(name)) {
            FoodCatagory catagory = new FoodCatagory(this.idcounter.getFoodcatagoryID(), name);
            this.selectedRestaurant.getFoods().add(catagory);
            SetInFile.setCafeRestaurants(cafeRestaurants);
            idcounter.addFoodCatagory();
            SetInFile.setCounter(idcounter);
            categoryTBL.getItems().add(catagory);
            cancel(event);
        } else {
            errorLBL.setText("This Category Exits!");
        }
    }

    private boolean checkName(String name) {
        for (int i = 0; i < selectedRestaurant.getFoods().size(); i++) {
            if (selectedRestaurant.getFoods().get(i).getFoodCatagoryName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void showItem(ActionEvent event) {
        FoodCatagory selectedFoodCategory = categoryTBL.getSelectionModel().getSelectedItem();
        if (selectedFoodCategory != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("..\\view\\adminadditem.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AdminAddItemPageController controller = loader.getController();
            controller.initfunction(stage, cafeRestaurants, deliveries, costumers, selectedRestaurant,
                    selectedFoodCategory);
            this.stage.setScene(new Scene(loader.getRoot()));
        }

    }

    private void reset() {
        nameTXF.setText("");
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
        addCategoryVBX.setDisable(true);

    }
}
