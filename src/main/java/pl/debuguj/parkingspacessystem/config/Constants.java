package pl.debuguj.parkingspacessystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by grzesiek on 18.10.17.
 */
@Component
public class Constants {

    @Value("${constants.format.time}")
    private String timeFormat;
    @Value("${constants.format.day}")
    private String dayFormat;

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getDayFormat() {
        return dayFormat;
    }

    public void setDayFormat(String dayFormat) {
        this.dayFormat = dayFormat;
    }
}
