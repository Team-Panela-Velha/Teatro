package Model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Teatro extends Remote {
    boolean reservarAssento(int numero) throws RemoteException;
    boolean cancelarReserva(int numero) throws RemoteException;
    String mostrarAssento() throws RemoteException;
}