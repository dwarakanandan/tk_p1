package group.ten.p1.shared;

public enum FlightStatus {
    B(0),
    D(1),
    I(2),
    L(3),
    M(4),
    S(5),
    X(6),
    Y(7),
    Z(8);

    int value;

    FlightStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FlightStatus fromInt(int id) {
        for (FlightStatus type : values()) {
            if (type.getValue() == id) {
                return type;
            }
        }
        return null;
    }
}