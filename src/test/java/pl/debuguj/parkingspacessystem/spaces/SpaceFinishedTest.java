package pl.debuguj.parkingspacessystem.spaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class SpaceFinishedTest {

    private SpaceFinished parkingSpaceFinished;
    private final SimpleDateFormat simpleDataTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final String registrationNo = "WZE12345";
    private Date startDate;
    private Date finishDate;

    @BeforeEach
    public void setup() throws Exception {
        startDate = simpleDataTimeFormatter.parse("2017-10-12T10:15:10");
        finishDate = simpleDataTimeFormatter.parse("2017-10-12T14:15:10");
        parkingSpaceFinished = new SpaceFinished(registrationNo, DriverType.REGULAR, startDate, finishDate);
    }

    @Test
    public void testSerialization() {
        SpaceFinished other = (SpaceFinished) SerializationUtils.deserialize(SerializationUtils.serialize(parkingSpaceFinished));

        assertThat(other.getUuid()).isEqualTo(parkingSpaceFinished.getUuid());
        assertThat(other.getVehicleRegistrationNumber()).isEqualTo(parkingSpaceFinished.getVehicleRegistrationNumber());
        assertThat(other.getDriverType()).isEqualTo(parkingSpaceFinished.getDriverType());
        assertThat(other.getBeginDate()).isEqualTo(parkingSpaceFinished.getBeginDate());
        assertThat(other.getFinishDate()).isEqualTo(parkingSpaceFinished.getFinishDate());
    }

    @Test
    public void shouldBeSetCorrectParameters() {
        assertThat(parkingSpaceFinished.getVehicleRegistrationNumber()).isEqualTo(registrationNo);
        assertThat(parkingSpaceFinished.getDriverType()).isEqualTo(DriverType.REGULAR);
        assertThat(parkingSpaceFinished.getDriverType()).isNotEqualTo(DriverType.VIP);
        assertThat(parkingSpaceFinished.getBeginDate()).isEqualTo(startDate);
        assertThat(parkingSpaceFinished.getFinishDate()).isEqualTo(finishDate);
    }
}
