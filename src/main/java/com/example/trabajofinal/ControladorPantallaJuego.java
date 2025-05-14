package com.example.trabajofinal;

import Clases.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

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
    private TextArea[] esperando;

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
    private TextArea esperando1, esperando2, esperando3, esperando4;

    @FXML
    private Button play;
    @FXML
    private Button pausa;

    public Button getPausa() {
        return pausa;
    }

    public static Paso p;
    @FXML
    private Button sumacomida;

    private Refugio refugio;
    public static ClaseRemota remoto;

    static {
        try {
            remoto = new ClaseRemota();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    private Stage scene;

    private void comienzo() throws RemoteException {

        p = new Paso();
        esperando = new TextArea[]{esperando1, esperando2, esperando3, esperando4};

        dentro = new TextArea[]{dentro1, dentro2, dentro3, dentro4};
        izquierda = new TextArea[]{izquierda1, izquierda2, izquierda3, izquierda4};
        fuera = new TextArea[]{fuera1, fuera2, fuera3, fuera4};
        exterior = new TextArea[]{exterior1, exterior2, exterior3,exterior4};
        ZonaInsegura[] zonas = new ZonaInsegura[4];
        Tunel[] tunel = new Tunel[4];
        for (int i = 0; i < 4; i++) {
            zonas[i] = new ZonaInsegura(exterior[i],p, i );
            tunel[i] = new Tunel(izquierda[i], fuera[i], dentro[i], esperando[i], zonas[i], p);
            tunel[i].setnTunel(i + 1);


            /*Registry registroZona = LocateRegistry.createRegistry(1099);
            Registry registroTunel = LocateRegistry.createRegistry(1100);

            try {
                Naming.rebind("//127.0.0.1/ObjetoZona", zonas[i]);
                Naming.rebind("//127.0.0.1/ObjetoTunel", tunel[i]);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }*/
        }
        refugio = new Refugio(20, tunel, zona, comedor, ncomida, camas,p );

        //Registry registroRefugio = LocateRegistry.createRegistry(1101);

        /*try {
            Naming.rebind("//127.0.0.1/ObjetoRefugio", refugio);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }*/

        Zombie z = new Zombie(0, zonas);
        z.start();
        for (int i = 1; i <= 10000; i++) {
            p.mirar();
            Humano humano = new Humano(i, refugio);
            humano.start();
            try {
                sleep((int)(Math.random() * 1500) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        play.setOnAction(actionEvent -> {
            new Thread(() -> {
                try {
                    comienzo();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("EstadoApocalipsis", remoto);
        } catch (MalformedURLException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void sumacomida(){
        refugio.setComida(1);
    }


    @FXML
    public void pausa() {
        if (p.isCerrado()) {
            p.abrir();
            pausa.setText("Pausar");
        } else {
            p.cerrar();
            pausa.setText("Continuar");
        }
    }

    public void setStage(Stage s) {
        this.scene = s;
    }

}
