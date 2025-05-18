package server;

import common.Teatro;

public class ServidorTeatro{
    public static void main(String[] args) throws Exception {
        java.rmi.registry.LocateRegistry.createRegistry(1109);
        Teatro teatro = new TeatroImplements();
        java.rmi.Naming.rebind("rmi://localhost:1109/teatro", teatro);
        System.out.println("Servidor do teatro pronto!");
    }
}
