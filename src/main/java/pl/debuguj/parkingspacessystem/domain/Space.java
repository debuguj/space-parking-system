package pl.debuguj.parkingspacessystem.domain;

import pl.debuguj.parkingspacessystem.enums.DriverType;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by grzesiek on 12.10.17.
 */
public interface Space {

    String getCarRegistrationNumber();

    void setDriverType(DriverType dt);

    DriverType getDriverType();

    Date getBeginTime();

    void setEndTime(Date timestamp) throws ParseException, IncorrectEndDateException;

    Date getEndTime();

}
