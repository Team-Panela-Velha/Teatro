package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.Teatro;

public class ServidorTeatro extends UnicastRemoteObject implements Teatro {
    private boolean[] assentos = new boolean[100];

    public ServidorTeatro() throws RemoteException {
        super();
    }

    // Removed duplicate method to resolve the compile error

    @Override
    public synchronized boolean reservarAssento(int clienteId, int numero) throws RemoteException {
        if (numero < 0 || numero >= assentos.length) {
            System.out.println("Cliente " + clienteId + ": Tentativa de reserva inválida para o assento " + numero);
            return false;
        }
        if (assentos[numero]) {
            System.out.println("Cliente " + clienteId + ": Assento " + numero + " já está ocupado.");
            return false;
        }
        assentos[numero] = true;
        System.out.println("Cliente " + clienteId + ": Assento " + numero + " reservado com sucesso.");
        return true;
    }

    @Override
    public synchronized boolean cancelarReserva(int clienteId, int numero) throws RemoteException {
        if (numero < 0 || numero >= assentos.length) {
            System.out.println("Cliente " + clienteId + ": Tentativa de cancelamento inválida para o assento " + numero);
            return false;
        }
        if (!assentos[numero]) {
            System.out.println("Cliente " + clienteId + ": Assento " + numero + " já está livre.");
            return false;
        }
        assentos[numero] = false;
        System.out.println("Cliente " + clienteId + ": Reserva do assento " + numero + " cancelada com sucesso.");
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
        java.rmi.Naming.rebind("rmi://localhost:1109/teatro", teatro);
        System.out.println("Servidor do teatro pronto!");
    }
}
