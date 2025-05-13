package com.example.trabajofinal;

import Clases.InterfazRemota;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ControladorInterfazRemota implements Initializable {

    @FXML
    protected TextField textoRefugio;

    @FXML
    protected TextArea info;

    @FXML
    protected TextField[] textoTuneles;

    @FXML
    protected TextField[] humanosZonasInseguras;

    @FXML
    protected TextField[] zombiesZonasInseguras;

    protected InterfazRemota interfazRemota;

    @FXML
    protected void botonPausarRemoto() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        while (true) {
            try {
                info.setText(interfazRemota.getInfo());
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
