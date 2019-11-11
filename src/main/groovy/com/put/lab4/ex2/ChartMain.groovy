package com.put.lab4.ex2

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class ChartMain extends Application {

    static void main(String[] args) {
        launch(ChartMain, args)
    }

    @Override
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chart.fxml"))
        primaryStage.setTitle("Chart")
        primaryStage.setScene(new Scene(root, 800, 600))
        primaryStage.show()
    }

}
