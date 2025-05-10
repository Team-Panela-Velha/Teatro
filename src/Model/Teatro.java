package Model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Teatro extends Remote {
    boolean reservarAssento(int clienteId, int numero) throws RemoteException;
    boolean cancelarReserva(int clienteId, int numero) throws RemoteException;
    String mostrarAssento() throws RemoteException;
}