package group.ten.p1.shared;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

public class FlightDetails implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String iATACode;
	private String operatingAirline;
	private String aircraftModel;
	private String departureAirport;
	private LocalDate originDate;
	private Instant scheduledDeparture;
	private int departureTerminal;
	private String departureGates;
	private Instant estimatedDeparture;
	private int checkinLocation;
	private Instant checkinStart;
	private int trackingNumber;
	private String arrivalAirport;
	private Instant scheduledArrival;
	private int arrivalTerminal;
	private String arrivalGates;
	private Instant estimatedArrival;
	private String checkinCounter;
    private Instant checkinEnd;
    private FlightStatus flightStatus;

    public FlightDetails() {
    }

    public String getIATACode() {
        return this.iATACode;
    }

    public void setIATACode(String iATACode) {
        this.iATACode = iATACode;
    }

    public String getOperatingAirline() {
        return this.operatingAirline;
    }

    public void setOperatingAirline(String operatingAirline) {
        this.operatingAirline = operatingAirline;
    }

    public String getAircraftModel() {
        return this.aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public String getDepartureAirport() {
        return this.departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public LocalDate getOriginDate() {
        return this.originDate;
    }

    public void setOriginDate(LocalDate originDate) {
        this.originDate = originDate;
    }

    public Instant getScheduledDeparture() {
        return this.scheduledDeparture;
    }

    public void setScheduledDeparture(Instant scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public int getDepartureTerminal() {
        return this.departureTerminal;
    }

    public void setDepartureTerminal(int departureTerminal) {
        this.departureTerminal = departureTerminal;
    }

    public String getDepartureGates() {
        return this.departureGates;
    }

    public void setDepartureGates(String departureGates) {
        this.departureGates = departureGates;
    }

    public Instant getEstimatedDeparture() {
        return this.estimatedDeparture;
    }

    public void setEstimatedDeparture(Instant estimatedDeparture) {
        this.estimatedDeparture = estimatedDeparture;
    }

    public int getCheckinLocation() {
        return this.checkinLocation;
    }

    public void setCheckinLocation(int checkinLocation) {
        this.checkinLocation = checkinLocation;
    }

    public Instant getCheckinStart() {
        return this.checkinStart;
    }

    public void setCheckinStart(Instant checkinStart) {
        this.checkinStart = checkinStart;
    }

    public int getTrackingNumber() {
        return this.trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getArrivalAirport() {
        return this.arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Instant getScheduledArrival() {
        return this.scheduledArrival;
    }

    public void setScheduledArrival(Instant scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public int getArrivalTerminal() {
        return this.arrivalTerminal;
    }

    public void setArrivalTerminal(int arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getArrivalGates() {
        return this.arrivalGates;
    }

    public void setArrivalGates(String arrivalGates) {
        this.arrivalGates = arrivalGates;
    }

    public Instant getEstimatedArrival() {
        return this.estimatedArrival;
    }

    public void setEstimatedArrival(Instant estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public String getCheckinCounter() {
        return this.checkinCounter;
    }

    public void setCheckinCounter(String checkinCounter) {
        this.checkinCounter = checkinCounter;
    }

    public Instant getCheckinEnd() {
        return this.checkinEnd;
    }

    public void setCheckinEnd(Instant checkinEnd) {
        this.checkinEnd = checkinEnd;
    }

    public FlightStatus getFlightStatus() {
        return this.flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    @Override
    public String toString() {
        return "{" +
            " iATACode='" + getIATACode() + "'" +
            ", operatingAirline='" + getOperatingAirline() + "'" +
            ", aircraftModel='" + getAircraftModel() + "'" +
            ", departureAirport='" + getDepartureAirport() + "'" +
            ", originDate='" + getOriginDate() + "'" +
            ", scheduledDeparture='" + getScheduledDeparture() + "'" +
            ", departureTerminal='" + getDepartureTerminal() + "'" +
            ", departureGates='" + getDepartureGates() + "'" +
            ", estimatedDeparture='" + getEstimatedDeparture() + "'" +
            ", checkinLocation='" + getCheckinLocation() + "'" +
            ", checkinStart='" + getCheckinStart() + "'" +
            ", trackingNumber='" + getTrackingNumber() + "'" +
            ", arrivalAirport='" + getArrivalAirport() + "'" +
            ", scheduledArrival='" + getScheduledArrival() + "'" +
            ", arrivalTerminal='" + getArrivalTerminal() + "'" +
            ", arrivalGates='" + getArrivalGates() + "'" +
            ", estimatedArrival='" + getEstimatedArrival() + "'" +
            ", checkinCounter='" + getCheckinCounter() + "'" +
            ", checkinEnd='" + getCheckinEnd() + "'" +
            ", flightStatus='" + getFlightStatus() + "'" +
            "}";
    }

}