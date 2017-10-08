package pl.debuguj.parkingspacessystem.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.debuguj.parkingspacessystem.controllers.ParkingSpaceController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
public class ParkingSpace {


    private final String carRegistrationNumber;
    private final DriverType driverType;
    private final Date beginTime;
    private Date endTime;

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    public ParkingSpace(final String registrationNumber, final DriverType driverType,
                        final Date startTime, Date endTime) {
        this.carRegistrationNumber = registrationNumber;
        this.driverType = driverType;
        this.beginTime = startTime;
        this.endTime = endTime;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingSpace that = (ParkingSpace) o;

        if (carRegistrationNumber != null ? !carRegistrationNumber.equals(that.carRegistrationNumber) : that.carRegistrationNumber != null)
            return false;
        if (driverType != that.driverType) return false;
        return beginTime != null ? beginTime.equals(that.beginTime) : that.beginTime == null;
    }

    @Override
    public int hashCode() {
        int result = carRegistrationNumber != null ? carRegistrationNumber.hashCode() : 0;
        result = 31 * result + (driverType != null ? driverType.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ParkingSpace{")
                .append(" carRegistrationNumber='").append(carRegistrationNumber)
                .append(" driverType='").append(driverType)
                .append(", beginTime=").append(simpleDateFormat.format(beginTime))
                .append(", endTime=").append(simpleDateFormat.format(endTime))
                .append('}');

        return sb.toString();
    }


}
