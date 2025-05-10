package Model;
import java.rmi.Naming;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            ReservaTeatro teatro = (ReservaTeatro) Naming.lookup("rmi://localhost/reserva");
            Scanner sc = new Scanner(System.in);

            System.out.print("Seu nome: ");
            String nome = sc.nextLine();

            System.out.println("1 - Reservar\n2 - Cancelar");
            int opcao = sc.nextInt();

            if (opcao == 1) {
                teatro.reservar(nome);
            } else if (opcao == 2) {
                teatro.cancelar(nome);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
