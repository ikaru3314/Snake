package com.example.snakegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {

        this.stage = stage;

        mostrarMenu();

        stage.setTitle("Snake");
        stage.setResizable(false);
        stage.show();
    }

    // ==============================
    // MENU
    // ==============================

    private void mostrarMenu() {

        Pane menu = new Pane();

        menu.setPrefSize(600, 600);

        menu.setStyle(
                "-fx-background-color: #111111;"
        );

        Label titulo = new Label("SNAKE");

        titulo.setLayoutX(240);
        titulo.setLayoutY(100);

        titulo.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 40px;" +
                        "-fx-font-weight: bold;"
        );

        Button jogarButton = new Button("JOGAR");

        jogarButton.setLayoutX(220);
        jogarButton.setLayoutY(250);

        jogarButton.setPrefWidth(160);
        jogarButton.setPrefHeight(50);

        jogarButton.setStyle(
                "-fx-background-color: #32CD32;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;"
        );

        jogarButton.setOnAction(event -> iniciarJogo());

        menu.getChildren().addAll(
                titulo,
                jogarButton
        );

        Scene menuScene = new Scene(menu, 600, 600);

        stage.setScene(menuScene);
    }

    // ==============================
    // INICIAR JOGO
    // ==============================

    private void iniciarJogo() {

        Pane gameRoot = new Pane();

        Scene gameScene = new Scene(
                gameRoot,
                600,
                600
        );

        new GameController(
                gameRoot,
                gameScene,
                this::mostrarMenu
        );

        stage.setScene(gameScene);

        gameRoot.requestFocus();
    }

    // ==============================
    // MAIN
    // ==============================

    public static void main(String[] args) {

        launch(args);
    }
}