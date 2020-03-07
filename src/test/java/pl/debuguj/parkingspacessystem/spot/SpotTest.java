package pl.debuguj.parkingspacessystem.spot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class SpotTest {

    private Spot parkingSpotFinished;
    private final SimpleDateFormat simpleDataTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final String registrationNo = "WZE12345";
    private Date startDate;
    private Date finishDate;

    @BeforeEach
    public void setup() throws Exception {
        startDate = simpleDataTimeFormatter.parse("2017-10-12T10:15:10");
        finishDate = simpleDataTimeFormatter.parse("2017-10-12T14:15:10");
        parkingSpotFinished = new Spot(registrationNo, DriverType.REGULAR, startDate, finishDate);
    }

    @Test
    public void testSerialization() {
        Spot other = (Spot) SerializationUtils.deserialize(SerializationUtils.serialize(parkingSpotFinished));

        assertThat(other.getUuid()).isEqualTo(parkingSpotFinished.getUuid());
        assertThat(other.getVehicleRegistrationNumber()).isEqualTo(parkingSpotFinished.getVehicleRegistrationNumber());
        assertThat(other.getDriverType()).isEqualTo(parkingSpotFinished.getDriverType());
        assertThat(other.getBeginDate()).isEqualTo(parkingSpotFinished.getBeginDate());
        assertThat(other.getFinishDate()).isEqualTo(parkingSpotFinished.getFinishDate());
    }

    @Test
    public void shouldBeSetCorrectParameters() {
        assertThat(parkingSpotFinished.getVehicleRegistrationNumber()).isEqualTo(registrationNo);
        assertThat(parkingSpotFinished.getDriverType()).isEqualTo(DriverType.REGULAR);
        assertThat(parkingSpotFinished.getDriverType()).isNotEqualTo(DriverType.VIP);
        assertThat(parkingSpotFinished.getBeginDate()).isEqualTo(startDate);
        assertThat(parkingSpotFinished.getFinishDate()).isEqualTo(finishDate);
    }
}
