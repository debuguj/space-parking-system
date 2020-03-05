package pl.debuguj.parkingspacessystem.spaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by GB on 22.02.20.
 */
public class SpaceActiveTest {

    private SpaceActive parkingSpaceActive;
    private final SimpleDateFormat simpleDataTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final String registrationNo = "WZE12345";
    private Date startDate;

    @BeforeEach
    public void setup() throws Exception {
        startDate = simpleDataTimeFormatter.parse("2017-10-12T10:15:10");
        parkingSpaceActive = new SpaceActive(registrationNo, DriverType.REGULAR, startDate);
    }

    @Test
    public void testSerialization() {
        SpaceActive other = (SpaceActive) SerializationUtils.deserialize(SerializationUtils.serialize(parkingSpaceActive));

        assertThat(other.getVehicleRegistrationNumber()).isEqualTo(parkingSpaceActive.getVehicleRegistrationNumber());
        assertThat(other.getDriverType()).isEqualTo(parkingSpaceActive.getDriverType());
        assertThat(other.getBeginDate()).isEqualTo(parkingSpaceActive.getBeginDate());
    }

    @Test
    public void shouldBeSetCorrectParameters() {
        assertThat(parkingSpaceActive.getVehicleRegistrationNumber()).isEqualTo(registrationNo);
        assertThat(parkingSpaceActive.getDriverType()).isEqualTo(DriverType.REGULAR);
        assertThat(parkingSpaceActive.getDriverType()).isNotEqualTo(DriverType.VIP);
        assertThat(parkingSpaceActive.getBeginDate()).isEqualTo(startDate);
    }

}