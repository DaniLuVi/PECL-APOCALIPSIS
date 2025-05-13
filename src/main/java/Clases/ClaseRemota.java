package Clases;

import com.example.trabajofinal.ControladorInterfazRemota;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClaseRemota extends UnicastRemoteObject implements InterfazRemota{
    private Zombie[] podio = new Zombie[3];
    private int[] humanosEnTuneles = new int[]{0,0,0,0};
    private int[] zombiesPorZona = new int[]{0,0,0,0};
    private int[] humanosPorZona = new int[]{0,0,0,0};
    private int humanosRefugio = 0;
    private Lock[] cerrojos = new ReentrantLock[14]; // Un cerrojo para cada contador, o usar los contadores atómicos.

    public ClaseRemota() throws RemoteException{
        for (int i = 0; i < 14; i++) {
            cerrojos[i] = new ReentrantLock();
        }
    }

    @Override
    public int mostrarPodio() throws RemoteException {
        return 0;
    }

    public void checkPodio(Zombie z, int muertes){
        try{
            cerrojos[0].lock();

            if (podio[2] == null || muertes> podio[2].getMuertes()){podio[2] = z;}
            else if (podio[1] == null || muertes> podio[1].getMuertes()){podio[2] = podio[1]; podio[1] = z; }
            else if (podio[0] == null || muertes> podio[0].getMuertes()){podio[2] = podio[1]; podio[1] = podio[0]; podio[0] = z;}
        // actualizarVista()
        } catch (Exception e){}
        finally {
            cerrojos[0].unlock();
        }

    }

    public void setZombiesPorZona(int zona , int zombies) {
        try {
            cerrojos[1 + zona].lock(); // Sé que es horrendo, pero para hacer ir probando funciona.
            this.zombiesPorZona[zona] = zombies;
        } catch (Exception e){}
        finally {
            cerrojos[1+zona].unlock();
        }
    }

    public void setHumanosPorZona(int zona, int humanos) {
        try {
            cerrojos[5 + zona].lock();
            this.humanosPorZona[zona] = humanos;
        } catch (Exception e){}
        finally {
            cerrojos[5+zona].unlock();
        }
    }

    public void setHumanosRefugio(int humanosRefugio) {
        try {
            cerrojos[9].lock();
            this.humanosRefugio = humanosRefugio;
        } catch (Exception e){}
        finally {
            cerrojos[9].unlock();
        }

    }

    public void setHumanosEnTuneles(int tunel, int humanosEnTuneles) {
        try {
            cerrojos[10+tunel].lock();
            this.humanosEnTuneles[tunel] = humanosEnTuneles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            cerrojos[10+tunel].unlock();
        }
    }

    public Zombie[] getPodio() {
        try {
            cerrojos[0].lock();
            return podio;
        } catch (Exception e){}
        finally {
            cerrojos[0].unlock();
        }
    return null;
    }

    public String[] getZombiesPorZona() {
        try {
            for (int i = 0; i < 4; i++) {
                cerrojos[1+i].lock();
            }
            String[] zombies= new String[4];
            for (int i = 0; i < 4; i++) {
                zombies[i] = zombiesPorZona[i]+"";
            }
            return zombies;
        } catch (Exception e){}
        finally {
            for (int i = 3; i >= 0; i--) {
                cerrojos[1+i].unlock();
            }
        }
    return null;
    }

    public String[] getHumanosPorZona() {
        try {
            for (int i = 0; i < 4; i++) {
                cerrojos[5+i].lock();
            }
            String[] humanos= new String[4];
            for (int i = 0; i < 4; i++) {
                humanos[i] = humanosPorZona[i]+"";
            }
            return humanos;
        } catch (Exception e){}
        finally {
            for (int i = 3; i >= 0; i--) {
                cerrojos[5+i].unlock();
            }
        }
    return null;
    }

    public int getHumanosRefugio() {
        try{cerrojos[9].lock();
        return humanosRefugio;
        }catch (Exception e){}
        finally {
            cerrojos[9].unlock();
        }
    return 0;
    }

    public String[] getHumanosEnTuneles() throws RemoteException{
        try {
            for (int i = 0; i < 4; i++) {
                cerrojos[10+i].lock();
            }
            String[] humanos = new String[4];
            for (int i = 0; i < 4; i++) {
                humanos[i] = humanosEnTuneles[i] + "";
            }
            return humanos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            for (int i = 3; i >= 0; i--) {
                cerrojos[10+i].unlock();
            }
        }

    }

    public String getInfo() {
        return "ClaseRemota{" +
                "podio=" + Arrays.toString(podio) +
                ", \nzombiesPorZona=" + Arrays.toString(zombiesPorZona) +
                ", humanosPorZona=" + Arrays.toString(humanosPorZona) +
                ", humanosRefugio=" + humanosRefugio +
                '}';
    }
}
