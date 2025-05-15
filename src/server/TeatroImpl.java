package server;
import java.util.*;

import common.Teatro;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class TeatroImpl extends UnicastRemoteObject implements Teatro {
    private final int totalAssentos;
    private final Map<Integer, Integer> reservas; // Assento -> Cliente
    private final Map<Integer, Queue<Integer>> filaDeEspera; // Assento -> Fila de espera

    public TeatroImpl(int totalAssentos) throws RemoteException {
        this.totalAssentos = totalAssentos;
        this.reservas = new HashMap<>();
        this.filaDeEspera = new HashMap<>();
        for (int i = 0; i < totalAssentos; i++) {
            filaDeEspera.put(i, new LinkedList<>());
        }
    }

    @Override
    public synchronized boolean reservarAssento(int clienteId, int assento) throws RemoteException {
        if (!reservas.containsKey(assento)) {
            reservas.put(assento, clienteId);
            System.out.println("Assento " + assento + " reservado pelo cliente " + clienteId);
            return true;
        } else {
            System.out.println("Assento " + assento + " ocupado. Cliente " + clienteId + " entrou na fila de espera.");
            filaDeEspera.get(assento).add(clienteId);
            return false;
        }
    }

    @Override
    public synchronized boolean cancelarReserva(int clienteId, int assento) throws RemoteException {
        if (reservas.getOrDefault(assento, -1) == clienteId) {
            reservas.remove(assento);
            System.out.println("Cliente " + clienteId + " cancelou a reserva do assento " + assento);

            // Notifica o próximo cliente na fila de espera
            Queue<Integer> fila = filaDeEspera.get(assento);
            if (!fila.isEmpty()) {
                int proximoCliente = fila.poll();
                reservas.put(assento, proximoCliente);
                System.out.println("Assento " + assento + " agora reservado pelo cliente " + proximoCliente);
            }
            return true;
        } else {
            System.out.println("Cliente " + clienteId + " não tinha reserva no assento " + assento);
            return false;
        }
    }

    @Override
    public synchronized String mostrarAssento() throws RemoteException {
        StringBuilder estado = new StringBuilder();
        for (int i = 0; i < totalAssentos; i++) {
            estado.append("Assento ").append(i).append(": ");
            if (reservas.containsKey(i)) {
                estado.append("Reservado pelo cliente ").append(reservas.get(i));
            } else {
                estado.append("Disponível");
            }
            estado.append("\n");
        }
        return estado.toString();
    }
}