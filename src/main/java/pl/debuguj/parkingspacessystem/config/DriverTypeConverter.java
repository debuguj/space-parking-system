package pl.debuguj.parkingspacessystem.config;

import pl.debuguj.parkingspacessystem.service.enums.DriverType;

import java.beans.PropertyEditorSupport;

/**
 * Created by grzesiek on 17.10.17.
 */
public class DriverTypeConverter extends PropertyEditorSupport {
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(DriverType.fromValue(text));
    }
}
