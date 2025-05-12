package Model;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class ClienteTeatro {
    public static void main(String[] args) {
        try {
            // Conectando ao registro RMI na porta 1109
            Registry registry = LocateRegistry.getRegistry("localhost", 1109);
            Teatro teatro = (Teatro) registry.lookup("teatro");

            int totalClientes = 100;
            Random random = new Random();

            for (int i = 0; i < totalClientes; i++) {
                final int clienteId = i;

                new Thread(() -> {
                    try {
                        boolean sucesso = false;
                        int tentativas = 0;

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