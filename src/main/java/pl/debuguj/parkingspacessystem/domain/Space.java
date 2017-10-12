package pl.debuguj.parkingspacessystem.domain;

import java.util.Date;

/**
 * Created by grzesiek on 12.10.17.
 */
public interface Space {

    String getCarRegistrationNumber();

    void setDriverType(DriverType dt);

    DriverType getDriverType();

    void setBeginTime(String time);

    Date getBeginTime();

    void setEndTime(String time);

    Date getEndTime();

    void setPeriod(String beginTime, String endTime);
}
