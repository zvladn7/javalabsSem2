package graphics.controllers;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import datasets.ProductDataSet;
import dbservices.DBService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import static dbservices.DBService.*;

public class MainWindow {

    private DBService dbService;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableView<ProductDataSet> productsTable;

    @FXML
    private TableColumn<ProductDataSet, Integer> columnID;

    @FXML
    private TableColumn<ProductDataSet, String> columnProductID;

    @FXML
    private TableColumn<ProductDataSet, String> columnTitle;

    @FXML
    private TableColumn<ProductDataSet, Double> columnPrice;


    @FXML
    private Button addButton;

    @FXML
    private TextField addTitle;

    @FXML
    private TextField addPrice;

    @FXML
    private Button changePriceButton;

    @FXML
    private TextField changeTitle;

    @FXML
    private TextField changePrice;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField deleteTitle;

    @FXML
    private TextField showPriceTitle;

    @FXML
    private Button showPriceButton;

    @FXML
    private Button showAllButton;

    @FXML
    private Button FilterByPriceButton;

    @FXML
    private TextField filterFromPrice;

    @FXML
    private TextField filterToPrice;
    private BufferedReader br;

    @FXML
    void FilterByPriceButtonClick(ActionEvent event) throws IOException {
        productsTable.getItems().clear();
        List<ProductDataSet> list = dbService.filterByPrice(new StringBuilder().append("filter_by_price ")
                .append(filterFromPrice.getText()).append(" ").append(filterToPrice.getText()).toString().split(" "));
        if (list != null) {
            for (ProductDataSet pds : list) {
                productsTable.getItems().add(pds);
            }
        }
        if (br.readLine().startsWith(WRONG_FORMAT_OF_NUMBER_MESSAGE)) {
            showAlert("Filter error", WRONG_FORMAT_OF_NUMBER_MESSAGE, Alert.AlertType.ERROR);
        }
        filterFromPrice.clear();
        filterToPrice.clear();
        skipLines();
    }

    @FXML
    void onAddButtonClick(ActionEvent event) throws IOException {
        dbService.apply("/add " + addTitle.getText() + " " + addPrice.getText());
        addTitle.clear();
        addPrice.clear();
        String s = br.readLine();
        if (s.startsWith(WRONG_FORMAT_OF_NUMBER_MESSAGE)) {
            showAlert("Price error", WRONG_FORMAT_OF_NUMBER_MESSAGE, Alert.AlertType.ERROR);
        }
        if (s.startsWith("This product has been already")) {
            showAlert("Product error", ADD_EXISTING_PRODCUT_MESSAGE, Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onChangePriceButtonClick(ActionEvent event) throws IOException {
        dbService.apply("/change_price " + changeTitle.getText() + " " +  changePrice.getText());
        changePrice.clear();
        changeTitle.clear();
        String str = br.readLine();
        if (str.startsWith(NO_PRODUCT_IN_DB_MESSAGE)) {
            showAlert("No product error", NO_PRODUCT_IN_DB_MESSAGE, Alert.AlertType.ERROR);
        }
        if (str.startsWith(WRONG_FORMAT_OF_NUMBER_MESSAGE)) {
            showAlert("Price error", WRONG_FORMAT_OF_NUMBER_MESSAGE, Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) throws IOException {
        dbService.apply("/delete " + deleteTitle.getText());
        deleteTitle.clear();
        if (br.readLine().startsWith("There is no")) {
            showAlert("Delete warning", DELETE_UNEXISTING_PRODUCT_MESSAGE, Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onShowAllButtonClick(ActionEvent event) {
        productsTable.getItems().clear();
        List<ProductDataSet> list = dbService.showAll(new String[1]);
        for (ProductDataSet pds : list) {
            productsTable.getItems().add(pds);
        }
        skipLines();
    }

    @FXML
    void onShowPriceButtonClick(ActionEvent event) throws IOException {
        double price = dbService.showPrice(("/price " + showPriceTitle.getText()).split(" "));
        if (price != -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(showPriceTitle.getText());
            alert.setContentText("Price: " + price);
            alert.showAndWait();
        } else {
            if (br.readLine().startsWith(NO_PRODUCT_IN_DB_MESSAGE)) {
                showAlert("Show price warning", NO_PRODUCT_IN_DB_MESSAGE, Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductID.setCellValueFactory(new PropertyValueFactory<>("prodId"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("cost"));
        try {
            PrintWriter out = new PrintWriter(new FileWriter(new File("inoutFile.txt")));
            dbService = new DBService(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br = new BufferedReader(new FileReader("inoutFile.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        console.Main.generate(dbService, 5);
    }

    private void showAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        Text text = new Text(content);
        text.setWrappingWidth(350);
        alert.getDialogPane().setContent(text);
        alert.showAndWait();
    }


    private void skipLines() {
        try {
            while (br.readLine() != null) {
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
