package com.example.trabajofinal;

import Clases.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ControladorPantallaJuego implements Initializable {

    private Stage scene;

    private void comienzo() {
        ZonaInsegura zona = new ZonaInsegura();
        Tunel[] tunel = new Tunel[4];
        Refugio refugio = new Refugio(0, tunel);
        Zombie zombie = new Zombie("Z", zona);
        AtomicReference zombie0 = new AtomicReference(zombie);
        for (int i = 1; i < 1000; i++) {
            Humano humano = new Humano("H", refugio);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setStage(Stage s) {
        this.scene = s;
    }

    @FXML
    protected void play() {
        comienzo();
    }
}
