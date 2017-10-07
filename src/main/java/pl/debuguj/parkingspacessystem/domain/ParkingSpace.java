package pl.debuguj.parkingspacessystem.domain;

import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
public class ParkingSpace {

    private final int id;
    private final String registrationNumber;
    private final Date startTime;
    private final Date stopTime;

    public ParkingSpace(final int id, final String registrationNumber,
                        final Date startTime, final Date stopTime) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public int getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingSpace that = (ParkingSpace) o;

        if (id != that.id) return false;
        return registrationNumber.equals(that.registrationNumber);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + registrationNumber.hashCode();
        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ParkingSpace{")
                .append("id=").append(id)
                .append(", registrationNumber='").append(registrationNumber)
                .append(", startTime=").append(startTime)
                .append(", stopTime=").append(stopTime)
                .append('}');

        return sb.toString();
    }
}
