package group.ten.p1.client;

import group.ten.p1.server.ServerInterface;
import group.ten.p1.shared.Constants;
import group.ten.p1.shared.FlightDetails;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClientCommunicator implements ClientInterface, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String CLIENT_TAG = "[CLIENT] : ";
    LinkedHashMap<String, FlightDetails> flights = new LinkedHashMap<>();
    String clientId;
    ServerInterface serverHandle;
    JTable table;
    JFrame frame;
    ArrayList<FlightDetails> flightDetailsTable;
    
    
    public ClientCommunicator() {
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMI_PORT);
            this.serverHandle = (ServerInterface) registry.lookup(Constants.RMI_IDENTIFIER);
            this.clientId = Long.toString(System.currentTimeMillis());
            this.CLIENT_TAG =  "[CLIENT] [" + this.clientId+ "] :";
        } catch (Exception e) {
            //System.err.println("Client exception: " + e.toString());
        }
    }

    public void setAttributes(JTable table, ArrayList<FlightDetails> flightDetailsTable) {
        this.table = table;
        this.flightDetailsTable = flightDetailsTable;
    }

    public void login() {
        try {
            int portNum = Constants.RMI_PORT;
            for(int i=0;i<100;i++) {
                if (available(portNum)) break;
                portNum++;
            }
            serverHandle.login(this.clientId, UnicastRemoteObject.exportObject(this, portNum));
        } catch (RemoteException e) {
            //System.err.println("Client login exception: " + e.toString());
        }
    }

    private boolean available(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    public void logout() {
        try {
            serverHandle.logout(this.clientId);
        } catch (RemoteException e) {
            //System.err.println("Client logout exception: " + e.toString());
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
    public void receiveListOfFlights(LinkedHashMap<String, FlightDetails> flights) throws RemoteException {
        System.out.println(CLIENT_TAG + "Got list of flights. Number flights loaded: " + flights.size() + "\n");
        this.flights = flights;
    }

    @Override
    public void receiveUpdatedFlight(FlightDetails flight, boolean deleted) throws RemoteException {
        String flightKey = flight.getUniqueCode();
        System.out.println(CLIENT_TAG + "Got flight update for "+flightKey+" deleted: " + deleted);
        int pos = new ArrayList<String>(flights.keySet()).indexOf(flightKey);
        
        if (pos>=0) {
            ((DefaultTableModel) table.getModel()).removeRow(pos);
            flightDetailsTable.remove(pos);
        }
        
        flights.remove(flightKey);

        if (! deleted) {
            flights.put(flightKey, flight);
            ((DefaultTableModel)table.getModel()).addRow(new Object[]{flight.getOperatingAirline(), flight.getIATACode(), flight.getTrackingNumber(),flight.getDepartureAirport(),
                flight.getArrivalAirport(), flight.getOriginDate().toString(), flight.getEstimatedDeparture(), flight.getEstimatedArrival(), flight.getFlightStatus().toString()});
            flightDetailsTable.add(flight);
        }
    }

}