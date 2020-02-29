package pl.debuguj.parkingspacessystem.spaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class SpaceFinishedTest {

    private SpaceFinished spaceFinished;

    private SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String registrationNo = "12345";

    @BeforeEach
    public void setup() throws Exception {

        Date begin = timeDateFormat.parse("2017-10-12 10:15:10");
        Date end = timeDateFormat.parse("2017-10-12 14:15:10");
        spaceFinished = new SpaceFinished(registrationNo, DriverType.REGULAR, begin, end);
    }

    @Test
    public void testSerialization() throws Exception {
        SpaceFinished other = (SpaceFinished) SerializationUtils.deserialize(SerializationUtils.serialize(spaceFinished));

        assertThat(other.getUuid()).isEqualTo(spaceFinished.getUuid());
        assertThat(other.getVehicleRegistrationNumber()).isEqualTo(spaceFinished.getVehicleRegistrationNumber());
        assertThat(other.getDriverType()).isEqualTo(spaceFinished.getDriverType());
        assertThat(other.getBeginDate()).isEqualTo(spaceFinished.getBeginDate());
        assertThat(other.getFinishDate()).isEqualTo(spaceFinished.getFinishDate());
    }
}
