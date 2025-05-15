package Model;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FronteiraCliente {
    public static void main(String[] args) {
        try {
            // Conectando ao registro RMI na porta 1109
            Registry registry = LocateRegistry.getRegistry("localhost", 1109);
            Teatro teatro = (Teatro) registry.lookup("teatro");

            int totalClientes = 100;

            for (int i = 0; i < totalClientes; i++) {
                final int id = i;

                Runnable cliente = new ClienteTeatro(id, teatro);
                Thread thread = new Thread(cliente);
                thread.start();
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
