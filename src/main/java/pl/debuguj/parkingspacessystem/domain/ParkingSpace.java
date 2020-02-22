package pl.debuguj.parkingspacessystem.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.debuguj.parkingspacessystem.service.enums.DriverType;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by grzesiek on 07.10.17.
 */
@AllArgsConstructor
@Getter
public class ParkingSpace {

    private final UUID uuid = UUID.randomUUID();
    private final String carRegistrationNumber;
    private final DriverType driverType ;
    private final Date beginDate;
    private final Date endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpace that = (ParkingSpace) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ParkingSpace{")
                .append(" carRegistrationNumber='").append(carRegistrationNumber)
                .append(", driverType='").append(driverType)
                .append(", beginTime=").append(beginDate)
                .append(", endTime=").append(endDate)
                .append('}');

        return sb.toString();
    }

}
