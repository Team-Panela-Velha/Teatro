package Model;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReservaTeatro extends Remote {
    boolean reservar(String nome) throws RemoteException;
    boolean cancelar(String nome) throws RemoteException;
}
