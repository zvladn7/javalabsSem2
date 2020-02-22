package graphics.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import datasets.ProductDataSet;
import dbservices.DBService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @FXML
    void FilterByPriceButtonClick(ActionEvent event) {
        productsTable.getItems().clear();
        List<ProductDataSet> list = dbService.filterByPrice(new StringBuilder().append("filter_by_price ")
                .append(filterFromPrice.getText()).append(" ").append(filterToPrice.getText()).toString().split(" "));
        for (ProductDataSet pds : list) {
            productsTable.getItems().add(pds);
        }
    }

    @FXML
    void onAddButtonClick(ActionEvent event) {
        dbService.apply("/add " + addTitle.getText() + " " + addPrice.getText());
        addTitle.clear();
        addPrice.clear();
    }

    @FXML
    void onChangePriceButtonClick(ActionEvent event) {
        dbService.apply("/change_price " + changeTitle.getText() + " " +  changePrice.getText());
        changePrice.clear();
        changeTitle.clear();
    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) {
        dbService.apply("/delete " + deleteTitle.getText());
        deleteTitle.clear();
    }

    @FXML
    void onShowAllButtonClick(ActionEvent event) {
        productsTable.getItems().clear();
        List<ProductDataSet> list = dbService.showAll(new String[1]);
        for (ProductDataSet pds : list) {
            productsTable.getItems().add(pds);
        }
    }

    @FXML
    void onShowPriceButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(showPriceTitle.getText());
        alert.setContentText("Price: " + dbService.showPrice(("/price " + showPriceTitle.getText()).split(" ")));
        alert.showAndWait();
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
    }
}
