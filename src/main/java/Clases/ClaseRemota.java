package Clases;

import javafx.scene.control.Button;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.trabajofinal.ControladorPantallaJuego.p;

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



    public void checkPodio(Zombie z, int muertes) {


            try {
                cerrojos[0].lock();

                Zombie[] tempPodio = new Zombie[4];
                System.arraycopy(podio, 0, tempPodio, 0, 3); // Copia la array podio en la array tmpPodio, como hacerlo elemento a elemento pero más rápido.

                boolean exists = false;
                for (Zombie existing : podio) {
                    if (existing != null && existing.getName().equals(z.getName())) {
                        exists = true;
                        break;
                    }
                }

                // Si no esta en el podio, se añade, y se ordena la lista por muertes
                if (!exists) {
                    tempPodio[3] = z;
                }

                // Reordenar el arreglo temporal por número de muertes en orden descendente
                Arrays.sort(tempPodio, (a, b) -> {
                    if (a == null) return 1;
                    if (b == null) return -1;
                    return Integer.compare(b.getMuertes(), a.getMuertes());
                });

                System.arraycopy(tempPodio, 0, podio, 0, 3);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cerrojos[0].unlock();
            }

        /*try {
            cerrojos[0].lock();

for (int i = 0; i < 3; i++) {
    // Verificar si el zombie ya está en el podio
    boolean exists = false;
    for (Zombie existing : podio) {
        if (existing != null && existing.getName().equals(z.getName())) {
            exists = true;
            break;
        }
    }
    if (exists) {
        break; // Si ya está en el podio, no hacemos nada
    }
    if (podio[i] == null || muertes > podio[i].getMuertes()) {
        System.arraycopy(podio, i, podio, i + 1, 2 - i);
        podio[i] = z;
        break;
    }
}


            /*
             if (podio[0] == null ){podio[0] = z;}
             else if (muertes> podio[0].getMuertes()){ if(podio[0].getName().equals(z.getName())){}podio[2] = podio[1]; podio[1] = podio[0]; podio[0] = z;}
             else if (podio[1] == null || muertes> podio[1].getMuertes()){if (podio[1].getName().equals(z.getName())) {}podio[2] = podio[1]; podio[1] = z; }
             else if (podio[2] == null || (muertes> podio[2].getMuertes() && !podio[2].getName().equals(z.getName()))){podio[2] = z;}
             *//*
                // actualizarVista()
            } catch (Exception e) {
            } finally {
                cerrojos[0].unlock();
            }
*/
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

    public String[] getPodio() {
        try {
            cerrojos[0].lock();
            String[] podiotmp = new String[3];
            for (int i = 0; i < 3; i++) {
                podiotmp[i] = podio[i] == null ? null : podio[i].getName() + " : " + podio[i].getMuertes();
            }
            return podiotmp;
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

    @Override
    public boolean pausa() throws RemoteException {
        if(p.isCerrado()){
            p.abrir();
            return true;
        }
        else {
            p.cerrar();
            return false;
        }
    }
}
