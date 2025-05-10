package Model;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;

public class ReservaTeatroImpl extends UnicastRemoteObject implements ReservaTeatro {
    private final int capacidade;
    private final Set<String> reservas = new HashSet<>();
    private final Queue<String> filaEspera = new LinkedList<>();

    public ReservaTeatroImpl(int capacidade) throws RemoteException {
        this.capacidade = capacidade;
    }

    public synchronized boolean reservar(String nome) throws RemoteException {
        if (reservas.contains(nome)) {
            System.out.println(nome + " já tem uma reserva.");
            return false;
        }

        if (reservas.size() < capacidade) {
            reservas.add(nome);
            System.out.println(nome + " conseguiu reservar.");
            return true;
        } else {
            System.out.println(nome + " entrou na fila de espera.");
            filaEspera.add(nome);
            while (!reservas.contains(nome) && !Thread.currentThread().isInterrupted()) {
                try {
                    wait();
                    if (reservas.size() < capacidade && filaEspera.peek().equals(nome)) {
                        filaEspera.poll();
                        reservas.add(nome);
                        System.out.println(nome + " foi notificado e conseguiu reservar.");
                        return true;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return false;
    }

    public synchronized boolean cancelar(String nome) throws RemoteException {
        if (reservas.remove(nome)) {
            System.out.println(nome + " cancelou a reserva.");
            notifyAll(); // Notifica todos da fila
            return true;
        } else {
            System.out.println(nome + " não tinha reserva para cancelar.");
            return false;
        }
    }
}
