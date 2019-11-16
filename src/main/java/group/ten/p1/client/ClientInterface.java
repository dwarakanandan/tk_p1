package group.ten.p1.client;

import group.ten.p1.shared.FlightDetails;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ClientInterface extends Remote{
    void receiveListOfFlights(HashMap<String, FlightDetails> flights) throws RemoteException;
    void receiveUpdatedFlight(FlightDetails flight, boolean deleted) throws RemoteException;
}
