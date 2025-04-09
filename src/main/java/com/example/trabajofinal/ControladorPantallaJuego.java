package com.example.trabajofinal;

import Clases.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.currentThread;

public class ControladorPantallaJuego implements Initializable {
    @FXML
    protected GridPane tuneles;
    private Label[] centroTunel = new Label[4];
    private Label[] dentroTunel = new Label[4];
    private Label[] fueraTunel = new Label[4];
    // vamos a añadir los ids de los humanos y zombies con TextArea
    @FXML
    private TextArea[] txtTunel = new TextArea[4];

    @FXML
    private TextArea[] txtFuera = new TextArea[4];

    @FXML
    private TextArea[] txtDentro = new TextArea[4];

    private Stage scene;

    private void comienzo() {
        ZonaInsegura zona = new ZonaInsegura();
        Tunel[] tunel = new Tunel[4];
        for (int i = 0; i < 4; i++) {
            tunel[i] = new Tunel();
            tunel[i].nTunel = i + 1;
        }

        Refugio refugio = new Refugio(0, tunel);
        Zombie zombie = new Zombie(zona);
        AtomicReference zombie0 = new AtomicReference(zombie);
        System.out.println(zombie.getName());


        for (int i = 1; i <= 20; i++) {
            Humano humano = new Humano(i, refugio);
            System.out.println(humano.getName());
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                // Aquí podrías instanciar tu LetrasColoresGrid
                //LetrasColoresGrid customComponent = new LetrasColoresGrid();
                //mainGrid.add(customComponent, i, j);
                // Ejemplo simplificado con un Label
                Label placeholder = new Label("Celda " + i + "," + j);
                placeholder.setId(i + "_"+ j);
                placeholder.setText("Celda"+ i +","+j);
                placeholder.setMinSize(100, 60); // Tamaño mínimo para visualización
                placeholder.setStyle("-fx-border-color: black; -fx-text-alignment: center;");
                tuneles.add(placeholder, i, j);
                if (i == 0)dentroTunel[j] = placeholder;
                if (i == 1)centroTunel[j] = placeholder;
                if (i == 2)fueraTunel [j] = placeholder;


            }
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
