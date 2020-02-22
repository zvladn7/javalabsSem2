package graphics.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    }

    @FXML
    void onAddButtonClick(ActionEvent event) {

    }

    @FXML
    void onChangePriceButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onShowAllButtonClick(ActionEvent event) {

    }

    @FXML
    void onShowPriceButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert addTitle != null : "fx:id=\"addTitle\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert addPrice != null : "fx:id=\"addPrice\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert changePriceButton != null : "fx:id=\"changePriceButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert changeTitle != null : "fx:id=\"changeTitle\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert changePrice != null : "fx:id=\"changePrice\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert deleteTitle != null : "fx:id=\"deleteTitle\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert showPriceTitle != null : "fx:id=\"showPriceTitle\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert showPriceButton != null : "fx:id=\"showPriceButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert showAllButton != null : "fx:id=\"showAllButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert FilterByPriceButton != null : "fx:id=\"FilterByPriceButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert filterFromPrice != null : "fx:id=\"filterFromPrice\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert filterToPrice != null : "fx:id=\"filterToPrice\" was not injected: check your FXML file 'MainWindow.fxml'.";

    }
}
