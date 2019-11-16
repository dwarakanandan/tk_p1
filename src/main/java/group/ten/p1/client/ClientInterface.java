package group.ten.p1.client;

import group.ten.p1.shared.FlightDetails;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

public interface ClientInterface extends Remote{
    void receiveListOfFlights(LinkedHashMap<String, FlightDetails> flights) throws RemoteException;
    void receiveUpdatedFlight(FlightDetails flight, boolean deleted) throws RemoteException;
}
