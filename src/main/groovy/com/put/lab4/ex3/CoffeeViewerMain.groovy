package com.put.lab4.ex3

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class CoffeeViewerMain extends Application {

    static void main(String[] args) {
        launch(CoffeeViewerMain, args)
    }

    @Override
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/coffeeViewer.fxml"))
        primaryStage.setTitle("Coffee Viewer")
        primaryStage.setScene(new Scene(root, 800, 600))
        primaryStage.show()
    }

}
