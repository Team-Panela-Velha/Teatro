package Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServidorTeatro extends UnicastRemoteObject implements Teatro {
    private boolean[] assentos = new boolean[100];

    public ServidorTeatro() throws RemoteException {
        super();
    }

    @Override
    public synchronized boolean reservarAssento(int numero) throws RemoteException {
        if (numero < 0 || numero >= assentos.length) return false;
        if (assentos[numero]) return false;
        assentos[numero] = true;
        return true;
    }

    @Override
    public synchronized boolean cancelarReserva(int numero) throws RemoteException {
        if (numero < 0 || numero >= assentos.length) return false;
        if (!assentos[numero]) return false;
        assentos[numero] = false;
        return true;
    }

    @Override
    public String mostrarAssento() throws RemoteException {
        StringBuilder sb = new StringBuilder("Assentos: ");
        for (int i = 0; i < assentos.length; i++) {
            sb.append(i).append(assentos[i] ? " [X] " : " [_] ");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        java.rmi.registry.LocateRegistry.createRegistry(1109);
        Teatro teatro = new ServidorTeatro();
        java.rmi.Naming.rebind("rmi://localhost/teatro", teatro);
        System.out.println("Servidor do teatro pronto!");
    }
}
