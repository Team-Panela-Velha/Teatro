package Model;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class ClienteTeatro2 {
    public static void main(String[] args) {
        try {
            // Conectando ao registro RMI na porta 1109
            Registry registry = LocateRegistry.getRegistry("localhost", 1109);
            Teatro teatro = (Teatro) registry.lookup("teatro");

            int totalClientes = 20;            // clientes de 10 a 20
            Random random = new Random();

            for (int i = 10; i < totalClientes; i++) {
                final int clienteId = i;

                new Thread(() -> {
                    try {
                        int tentativas = 0;
                        boolean sucesso = false;
                        int assentoEscolhido = random.nextInt(5);

                        while (!sucesso && tentativas < 5) {
                            sucesso = teatro.reservarAssento(clienteId, assentoEscolhido);
                            if (sucesso) {
                                System.out.println("Cliente " + clienteId + ": assento " + assentoEscolhido + " reservado com sucesso!");
                            } else {
                                System.out.println("Cliente " + clienteId + ": assento " + assentoEscolhido + " ocupado, aguardando...");
                                Thread.sleep(500);
                                tentativas++;
                            }
                        }

                        if (!sucesso) {
                            System.out.println("Cliente " + clienteId + ": desistiu após várias tentativas.");
                        } else {
                            // 30% de chance de cancelar a reserva após 1 segundo
                            if (random.nextInt(100) < 30) {
                                Thread.sleep(1000);
                                boolean cancelado = teatro.cancelarReserva(clienteId, assentoEscolhido);
                                if (cancelado) {
                                    System.out.println("Cliente " + clienteId + ": cancelou a reserva do assento " + assentoEscolhido);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro no cliente " + clienteId);
                        e.printStackTrace();
                    }
                }).start();
            }

            // Aguarda as threads terminarem (tempo estimado)
            Thread.sleep(10000);
            System.out.println("\nEstado final dos assentos:");
            System.out.println(teatro.mostrarAssento());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}