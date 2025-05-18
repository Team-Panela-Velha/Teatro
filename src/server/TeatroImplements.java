package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

import common.Teatro;

public class TeatroImplements extends UnicastRemoteObject implements Teatro{
    private boolean[] assentos = new boolean[100];
    private Set<Integer> fila = new HashSet<>();

    public TeatroImplements() throws RemoteException {
        super();
    }


    @Override
    public synchronized int reservarAssento(int clienteId) throws RemoteException {
        while (true) {
            for (int i = 0; i < assentos.length; i++) {
                if (!assentos[i]) {
                    assentos[i] = true;
                    fila.remove(clienteId);
                    System.out.println("Cliente " + clienteId + ": Assento " + i + " reservado com sucesso.");

                    return i;
                }
            }

            if (!fila.contains(clienteId)) {
                fila.add(clienteId);
                System.out.println("Cliente " + clienteId + " entrou na fila de espera...");
            }

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RemoteException("Thread interrompida", e);
            }
        }
    }

    @Override
    public synchronized void cancelarReserva(int clienteId, int assento) throws RemoteException {
        if (assento < 0 || assento >= assentos.length);

        assentos[assento] = false;
        System.out.println("Cliente " + clienteId + " cancelou o assento " + assento);
        notifyAll(); // avisa quem está esperando
    }

    @Override
    public String mostrarAssento() throws RemoteException {
        StringBuilder sb = new StringBuilder("Assentos: ");
        for (int i = 0; i < assentos.length; i++) {
            sb.append(i).append(assentos[i] ? " [X] " : " [_] ");
        }

        String reservas = sb.toString();

        return reservas;
    }
}
