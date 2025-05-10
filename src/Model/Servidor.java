package Model;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ReservaTeatroImpl obj = new ReservaTeatroImpl(3);
            Naming.rebind("reserva", obj);
            System.out.println("Servidor pronto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
