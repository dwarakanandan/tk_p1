package group.ten.p1.client;

import group.ten.p1.server.ServerInterface;
import group.ten.p1.shared.Constants;
import group.ten.p1.shared.FlightDetails;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JTable;

public class ClientCommunicator implements ClientInterface, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    HashMap<String, FlightDetails> flights = new HashMap<>();
    String clientId;
    ServerInterface serverHandle;
    JTable table;
    
    public ClientCommunicator() {
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMI_PORT);
            this.serverHandle = (ServerInterface) registry.lookup(Constants.RMI_IDENTIFIER);
            this.clientId = Long.toString(System.currentTimeMillis());
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void login() {
        try {
            serverHandle.login(this.clientId, UnicastRemoteObject.exportObject(this, Constants.RMI_PORT + new Random().nextInt(100)));
        } catch (RemoteException e) {
            System.err.println("Client login exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void logout() {
        try {
            serverHandle.logout(this.clientId);
        } catch (RemoteException e) {
            System.err.println("Client logout exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void updateFlight(FlightDetails flight) {
        try {
            serverHandle.updateFlight(this.clientId, flight);
        } catch (RemoteException e) {
            System.err.println("Client updateFlight exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void deleteFlight(FlightDetails flight) {
        try {
            serverHandle.deleteFlight(this.clientId, flight);
        } catch (RemoteException e) {
            System.err.println("Client deleteFlight exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void receiveListOfFlights(HashMap<String, FlightDetails> flights) throws RemoteException {
        System.out.println("Got list of flights");
        this.flights = flights;
    }

    @Override
    public void receiveUpdatedFlight(FlightDetails flight, boolean deleted) throws RemoteException {
        System.out.println("Got flight update");
        String flightKey = flight.getIATACode() + flight.getTrackingNumber();
        flights.remove(flightKey);
        if (deleted) {
            flights.remove(flightKey);
        } else {
            flights.replace(flightKey, flight);
        }
        this.table.repaint();
    }

}