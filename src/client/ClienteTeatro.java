package client;

import java.util.Random;

import common.Teatro;

public class ClienteTeatro implements Runnable{
    
    private int clienteId;
    private boolean sucesso = false;
    private int tentativas = 0;
    private Teatro teatro;
    private Random random = new Random();

    public ClienteTeatro(int clientId, Teatro teatro) {
        this.clienteId = clientId;
        this.teatro = teatro;
    }
    
    public void run() {
        try {
            while (!sucesso) {
                int assentoEscolhido = random.nextInt(100); // Escolhe um assento aleatório
                sucesso = teatro.reservarAssento(clienteId, assentoEscolhido);

                if (sucesso) {
                    System.out.println("Cliente " + clienteId + ": assento " + assentoEscolhido + " reservado com sucesso!");

                    // 30% de chance de cancelar a reserva após 1 segundo
                    if (random.nextInt(100) < 30) {
                        Thread.sleep(1000);
                        boolean cancelado = teatro.cancelarReserva(clienteId, assentoEscolhido);
                        if (cancelado) {
                            System.out.println("Cliente " + clienteId + ": cancelou a reserva do assento " + assentoEscolhido);
                        }
                    }
                } else {
                    System.out.println("Cliente " + clienteId + ": assento " + assentoEscolhido + " ocupado, tentando outro...");
                    Thread.sleep(500); // Espera antes de tentar novamente
                    tentativas++;
                }

                // Limite de tentativas para evitar loops infinitos
                if (tentativas >= 10) {
                    System.out.println("Cliente " + clienteId + ": desistiu após várias tentativas.");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro no cliente " + clienteId);
            e.printStackTrace();
        }
    }
}