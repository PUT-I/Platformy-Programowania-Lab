package com.put.lab4.ex3

import com.put.lab3.coffee.Coffee
import com.put.lab3.coffee.CoffeeDao
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Font

class Controller implements Initializable {

    @FXML
    public AnchorPane mainPane

    @Override
    void initialize(URL url, ResourceBundle resourceBundle) {
        addCoffeeTableView()
    }

    private void addCoffeeTableView() {
        CoffeeDao dao = new CoffeeDao()
        ObservableList<Coffee> observableCoffeeList = FXCollections.observableArrayList(dao.getAll())

        final Label label = new Label("Coffee Book")
        label.setFont(new Font("Arial", 20))

        TableView<Coffee> table = new TableView<Coffee>()
        table.setEditable(true)

        TableColumn cofNameCol = createCoffeeColumn("Coffee Name", "name")
        TableColumn supIdCol = createCoffeeColumn("Supplier ID", "supplierId")
        TableColumn priceCol = createCoffeeColumn("Price", "price")
        TableColumn salesCol = createCoffeeColumn("Sales", "sales")
        TableColumn totalCol = createCoffeeColumn("Total", "total")

        table.setItems(observableCoffeeList)
        table.getColumns().addAll([cofNameCol, supIdCol, priceCol, salesCol, totalCol])

        mainPane.getChildren().addAll([label, table])
    }

    private static TableColumn createCoffeeColumn(String label, String coffeeVariable) {
        TableColumn column = new TableColumn(label)
        column.setCellValueFactory(new PropertyValueFactory<Coffee, String>(coffeeVariable))
        return column
    }
}
