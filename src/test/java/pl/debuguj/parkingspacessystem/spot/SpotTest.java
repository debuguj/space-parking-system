package pl.debuguj.parkingspacessystem.spot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class SpotTest {

    private Spot spot;
    private final SimpleDateFormat simpleDataTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final String registrationNo = "WZE12345";
    private Date startDate;
    private Date finishDate;

    @BeforeEach
    public void setup() throws Exception {
        startDate = simpleDataTimeFormatter.parse("2017-10-12T10:15:10");
        finishDate = simpleDataTimeFormatter.parse("2017-10-12T14:15:10");
        spot = new Spot(registrationNo, DriverType.REGULAR, startDate, finishDate);
    }

    @Test
    public void testSerialization() {
        Spot other = (Spot) SerializationUtils.deserialize(SerializationUtils.serialize(spot));

        assertThat(other.getUuid()).isEqualTo(spot.getUuid());
        assertThat(other.getVehicleRegistrationNumber()).isEqualTo(spot.getVehicleRegistrationNumber());
        assertThat(other.getDriverType()).isEqualTo(spot.getDriverType());
        assertThat(other.getBeginDate()).isEqualTo(spot.getBeginDate());
        assertThat(other.getFinishDate()).isEqualTo(spot.getFinishDate());
    }

    @Test
    public void shouldBeSetCorrectParameters() {
        assertThat(spot.getVehicleRegistrationNumber()).isEqualTo(registrationNo);
        assertThat(spot.getDriverType()).isEqualTo(DriverType.REGULAR);
        assertThat(spot.getDriverType()).isNotEqualTo(DriverType.VIP);
        assertThat(spot.getBeginDate()).isEqualTo(startDate);
        assertThat(spot.getFinishDate()).isEqualTo(finishDate);
    }
}
