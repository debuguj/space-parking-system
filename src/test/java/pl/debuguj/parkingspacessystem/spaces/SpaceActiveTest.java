package pl.debuguj.parkingspacessystem.spaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by GB on 22.02.20.
 */
public class SpaceActiveTest {

    private SpaceActive parkingSpaceActive;

    private SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String registrationNo = "12345";

    @BeforeEach
    public void setup() throws Exception {

        Date begin = timeDateFormat.parse("2017-10-12 10:15:10");
        parkingSpaceActive = new SpaceActive(registrationNo, DriverType.REGULAR, begin);
    }

    @Test
    public void testSerialization() throws Exception {
        SpaceActive other = (SpaceActive) SerializationUtils.deserialize(SerializationUtils.serialize(parkingSpaceActive));

        assertThat(other.getVehicleRegistrationNumber()).isEqualTo(parkingSpaceActive.getVehicleRegistrationNumber());
        assertThat(other.getDriverType()).isEqualTo(parkingSpaceActive.getDriverType());
        assertThat(other.getBeginDate()).isEqualTo(parkingSpaceActive.getBeginDate());

    }

//    @Test
//    public void testCorrectRegistrationNumbe(){
//
//    }
//    @Test
//    public void testCorrectRegistrationNumbe(){
//
//    }
//    @Test
//    public void testCorrectRegistrationNumbe(){
//
//    }
//    @Test
//    public void testCorrectRegistrationNumbe(){
//
//    }
}