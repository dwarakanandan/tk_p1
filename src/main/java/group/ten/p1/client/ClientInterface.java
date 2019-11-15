package group.ten.p1.client;

import group.ten.p1.shared.FlightDetails;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote{

    void receiveListOfFlights(List<FlightDetails> flights) throws RemoteException;
    void receiveUpdatedFlight(FlightDetails flight, boolean deleted) throws RemoteException;
}
