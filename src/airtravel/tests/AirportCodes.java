package airtravel.tests;

public enum AirportCodes {
    CLE,
    GRE,
    LAS,
    NWE,
    SWD;

    @Override
    public String toString() {
        return this.name();
    }
}
