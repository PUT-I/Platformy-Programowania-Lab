package com.put.lab4.ex2

import com.put.lab4.ex1.FunctionChart
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.chart.Chart
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane

class Controller implements Initializable {

    @FXML
    private GridPane grid

    @FXML
    private TextField param1

    @FXML
    private TextField param2

    @FXML
    private TextField param3

    @FXML
    private TextField function

    @FXML
    public Slider range

    @FXML
    public TextField rangeText

    @FXML
    public ComboBox chartType

    @Override
    void initialize(URL url, ResourceBundle resourceBundle) {
        updateFunction()
        updateRange()

        param1.textProperty().addListener(paramListener())
        param2.textProperty().addListener(paramListener())
        param3.textProperty().addListener(paramListener())
        range.valueProperty().addListener(sliderListener())

        chartType.getItems().addAll(["Line chart", "Area chart", "Point chart"])
        chartType.setValue("Line chart")
        chartType.valueProperty().addListener(paramListener())
    }

    ChangeListener sliderListener() {
        return new ChangeListener() {
            @Override
            void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                updateRange()
            }
        }
    }

    private ChangeListener<String> paramListener() {
        return new ChangeListener<String>() {
            @Override
            void changed(ObservableValue<? extends String> observableValue, String oldText, String newText) {
                updateFunction()
            }
        }
    }

    private int extractChartType() {
        switch (chartType.getValue()) {
            case "Area chart":
                return FunctionChart.AREA_CHART
            case "Point chart":
                return FunctionChart.POINT_CHART
            default:
                return FunctionChart.LINE_CHART
        }
    }

    private void createChart(double a, double b, double c) {
        final int range = range.getValue().toInteger()

        int chartTypeValue = extractChartType()

        Chart chart = FunctionChart.createChart(-range, range, chartTypeValue) {
            double x -> return a * x**2 + b * x + c
        }
        chart.setId('chart')
        grid.getChildren().removeIf { Node node -> node.id == 'chart' }
        grid.add(chart, 0, 1, 3, 2)
    }

    private void updateFunction() {
        final String a = param1.getText().isNumber() ? param1.getText() : '?'
        final String b = param2.getText().isNumber() ? param2.getText() : '?'
        final String c = param3.getText().isNumber() ? param3.getText() : '?'
        function.setText("${a}x^2 + ${b}x + ${c}")
        if (!function.getText().contains('?')) {
            createChart(a.toDouble(), b.toDouble(), c.toDouble())
        }
    }

    private void updateRange() {
        final long intVal = range.getValue().round()
        final String rangeStr = "(-${intVal},${intVal})"
        rangeText.setText(rangeStr)
        updateFunction()
    }
}
