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

import static java.lang.Thread.sleep;

public class ControladorPantallaJuego implements Initializable {
    @FXML
    protected GridPane tuneles;
    @FXML
    private TextArea[] dentro;
    @FXML
    private TextArea[] izquierda;
    @FXML
    private TextArea[] fuera;
    @FXML
    private TextArea comedor;
    @FXML
    private TextArea camas;

    @FXML
    private Label ncomida;


    @FXML
    private TextArea dentro1, dentro2, dentro3, dentro4;

    @FXML
    private TextArea izquierda1, izquierda2, izquierda3, izquierda4;
    @FXML
    private TextArea fuera1, fuera2,fuera3, fuera4;

    @FXML
    private TextArea[] exterior;
    @FXML
    private TextArea exterior1,exterior2,exterior3,exterior4;
    @FXML
    private TextArea zona;

    @FXML
    private Button play;





    private Label[] izquierdaTunel = new Label[4];
    private Label[] fueraTunel = new Label[4];

    private Stage scene;

    private void comienzo() {
        dentro = new TextArea[]{dentro1, dentro2, dentro3, dentro4};
        izquierda = new TextArea[]{izquierda1, izquierda2, izquierda3, izquierda4};
        fuera = new TextArea[]{fuera1, fuera2, fuera3, fuera4};
        exterior = new TextArea[]{exterior1, exterior2, exterior3,exterior4};
        ZonaInsegura[] zonas = new ZonaInsegura[4];
        Tunel[] tunel = new Tunel[4];
        for (int i = 0; i < 4; i++) {
            zonas[i] = new ZonaInsegura(exterior[i]);
            tunel[i] = new Tunel(izquierda[i], fuera[i], dentro[i], zonas[i]);
            tunel[i].nTunel = i + 1;

        }
        Refugio refugio = new Refugio(20, tunel, zona, comedor, ncomida, camas);
        //Zombie zombie = new Zombie(zona);
        //AtomicReference zombie0 = new AtomicReference(zombie);
        //System.out.println(zombie.getName());

        Zombie z = new Zombie(0000, zonas);
        z.start();
        for (int i = 1; i <= 10; i++) {
            Humano humano = new Humano(i, refugio);
            humano.start();
            try {
                sleep((int)(Math.random() * 1500) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*for (int i = 0; i < 3; i++) {
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
                if (i == 0) izquierdaTunel[j] = placeholder;
                if (i == 1)centroTunel[j] = placeholder;
                if (i == 2)fueraTunel [j] = placeholder;


            }
        }*/

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setStage(Stage s) {
        this.scene = s;
    }

    @FXML
    protected void play() {
        play.setOnAction(actionEvent -> {
            new Thread(() -> {
                comienzo();
            }).start();
        });
    }
}
