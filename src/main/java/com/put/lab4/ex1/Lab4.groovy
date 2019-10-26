package com.put.lab4.ex1


import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.chart.Chart
import javafx.stage.Stage

class Lab4 extends Application {

    private static createStageWithChart(Chart chart, String title) {
        Stage stage = new Stage()
        Scene scene = new Scene(chart, 800, 600)
        stage.setTitle(title)
        stage.setScene(scene)
        stage.show()
    }

    static void ex1() {
        Chart chartA = FunctionChart.createLineChart(-1, 1) {
            double x -> return 0
        }
        Chart chartB = FunctionChart.createLineChart(-4, 4) {
            double x -> return -(x**2)
        }
        Chart chartC = FunctionChart.createChart(-4, 4, FunctionChart.AREA_CHART) {
            double x -> return -(x**2) - x + 3
        }
        Chart chartD = FunctionChart.createChart(-4, 4, FunctionChart.POINT_CHART) {
            double x -> return Math.sin(x)
        }

        createStageWithChart(chartA, "Chart A")
        createStageWithChart(chartB, "Chart B")
        createStageWithChart(chartC, "Chart C")
        createStageWithChart(chartD, "Chart D")
    }

    static void main(String[] args) {
        launch(Lab4, [] as String[])
    }

    @Override
    void start(Stage stage) throws Exception {
        ex1()
    }
}
