package client;

import java.util.Random;

import common.Teatro;

public class ClienteTeatro implements Runnable{
    
    private int clienteId;
    private Teatro teatro;
    private Random random = new Random();

    public ClienteTeatro(int clientId, Teatro teatro) {
        this.clienteId = clientId;
        this.teatro = teatro;
    }
    
    public void run() {
         try {
            int assento = teatro.reservarAssento(clienteId);

            System.out.println("Cliente " + clienteId + " reservou o assento " + assento);
            Thread.sleep(1000);

            if (random.nextInt(100) < 50) { // 50% chance de cancelar
                teatro.cancelarReserva(clienteId, assento);

                System.out.println("Cliente " + clienteId + " cancelou o assento " + assento);
            }
        } catch (Exception e) {
            System.out.println("Erro no cliente " + clienteId);
            e.printStackTrace();
        }
    }
}