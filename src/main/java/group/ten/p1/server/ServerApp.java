package group.ten.p1.server;

import group.ten.p1.shared.FlightDetails;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerApp implements ServerInterface{

    public static void main(String[] args) {
        System.out.println("Starting Server.");

        try {
            ServerApp server = new ServerApp();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("TK Airport", stub);
        } catch (Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public void login(String clientName){};
    public void logout(String clientName){};
    public void updateFlight(String clientName, FlightDetails flight){};
    public void deleteFlight(String clientName, FlightDetails flight){};
}
