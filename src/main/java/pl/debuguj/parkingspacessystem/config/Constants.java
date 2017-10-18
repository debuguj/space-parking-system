package pl.debuguj.parkingspacessystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by grzesiek on 18.10.17.
 */
@Component
public class Constants {

    @Value("${constants.format.time:yyyy-MM-dd HH:mm:ss}")
    private String timeFormat;

    @Value("${constants.format.day:yyyy-MM-dd}")
    private String dayFormat;

    public String getTimeFormat() {
        return timeFormat;
    }

    public String getDayFormat() {
        return dayFormat;
    }

}
