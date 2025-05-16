package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Teatro extends Remote {
    int reservarAssento(int clienteId) throws RemoteException;
    void cancelarReserva(int clienteId, int assento) throws RemoteException;
    String mostrarAssento() throws RemoteException;
}