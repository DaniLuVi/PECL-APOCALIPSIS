package com.example.trabajofinal;

import Clases.InterfazRemota;
import javafx.application.Platform;
import Clases.Zombie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControladorInterfazRemota implements Initializable {

    @FXML
    protected TextField textoRefugio = new TextField();

    @FXML
    protected TextArea info;

    @FXML
    protected TextField[] textoTuneles;

    @FXML
    protected TextField[] humanosZonasInseguras;

    @FXML
    protected TextField[] zombiesZonasInseguras;

    @FXML
    protected TextField tuneles1, tuneles2, tuneles3, tuneles4;

    @FXML
    protected TextField humanosZona1, humanosZona2, humanosZona3, humanosZona4;

    @FXML
    protected TextField zombiesZona1, zombiesZona2, zombiesZona3, zombiesZona4;

    @FXML
    protected TextField podio;

    protected InterfazRemota interfazRemota;

    @FXML
    protected void botonPausarRemoto() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textoTuneles = new TextField[]{tuneles1, tuneles2, tuneles3, tuneles4};
        humanosZonasInseguras = new TextField[]{humanosZona1, humanosZona2, humanosZona3, humanosZona4};
        zombiesZonasInseguras = new TextField[]{zombiesZona1, zombiesZona2, zombiesZona3, zombiesZona4};
        System.out.println("ASDadad");

        try {
            interfazRemota = (InterfazRemota) Naming.lookup("EstadoApocalipsis");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            bucletmp();
        }).start();

    }
    public void bucletmp(){
        Zombie[] podio = new Zombie[3];
        int[] zombiesPorZona = new int[]{0,0,0,0};
        int[] humanosPorZona = new int[]{0,0,0,0};
        int humanosRefugio = 0;
        while (true) {
            try {
                    Platform.runLater(() -> {
                        try {
                            info.setText(interfazRemota.getInfo());
                            textoRefugio.setText(String.valueOf(interfazRemota.getHumanosRefugio()));
                            for (int j = 0; j < 4; j++) {
                                textoTuneles[j].setText(interfazRemota.getHumanosEnTuneles()[j]);
                                humanosZonasInseguras[j].setText(interfazRemota.getHumanosPorZona()[j]);
                                zombiesZonasInseguras[j].setText(interfazRemota.getZombiesPorZona()[j]);
                            }
                            } catch(RemoteException e){
                                throw new RuntimeException(e);
                            }
                    });

                System.out.println(interfazRemota.getInfo());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(125);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }




}
