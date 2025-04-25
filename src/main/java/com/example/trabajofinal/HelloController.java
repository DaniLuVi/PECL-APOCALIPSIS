package com.example.trabajofinal;

import Clases.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    private static Logger log;

    @FXML
    protected void iniciarJuego() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pantalla_juego.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Pantalla de juego");
            stage.setMaximized(true);
            ControladorPantallaJuego controlador = fxmlLoader.getController();
            controlador.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}