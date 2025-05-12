package com.example.trabajofinal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorInterfazRemota implements Initializable {

    @FXML
    protected TextField textoRefugio;

    @FXML
    protected TextField[] textoTuneles;

    @FXML
    protected TextField[] humanosZonasInseguras;

    @FXML
    protected TextField[] zombiesZonasInseguras;

    @FXML
    protected void botonPausarRemoto() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
