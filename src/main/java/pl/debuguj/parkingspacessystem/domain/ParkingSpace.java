package pl.debuguj.parkingspacessystem.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.debuguj.parkingspacessystem.controllers.ParkingSpaceController;
import pl.debuguj.parkingspacessystem.enums.DriverType;
import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
public class ParkingSpace implements Space {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceController.class);

    private final String carRegistrationNumber;
    private DriverType driverType = DriverType.REGULAR;
    private Date beginDate;
    private Date endDate;



    public ParkingSpace(String carRegistrationNumber,
                        Date beginDate,
                        Date endDate) throws IncorrectEndDateException {
        this.carRegistrationNumber = carRegistrationNumber;
        this.beginDate = beginDate;
        this.endDate = checkEndDate(endDate);
    }

    @Override
    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    @Override
    public DriverType getDriverType() {
        return driverType;
    }

    @Override
    public void setDriverType( DriverType dt) {
        if(null != dt)
            this.driverType = dt;
    }


    @Override
    public Date getBeginTime() {
        return beginDate;
    }

    @Override
    public void setEndTime(Date timestamp)  {

        try {
            if(null != timestamp)
                endDate = checkEndDate(timestamp);
        } catch (IncorrectEndDateException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public Date getEndTime() {
        return endDate;
    }

    private Date checkEndDate(Date newTime) throws IncorrectEndDateException {
        if(newTime.compareTo(beginDate) > 0)
            return newTime;
        throw new IncorrectEndDateException("Date "+newTime+" should be after " + beginDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingSpace that = (ParkingSpace) o;

        return carRegistrationNumber != null ? carRegistrationNumber.equals(that.carRegistrationNumber) : that.carRegistrationNumber == null;
    }

    @Override
    public int hashCode() {
        return carRegistrationNumber != null ? carRegistrationNumber.hashCode() : 0;
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
