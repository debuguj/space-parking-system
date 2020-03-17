package pl.debuguj.system.spot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SpotTest {

    private final SimpleDateFormat simpleDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final String registrationNumber = "WZE12345";
    private Date startDate;
    private Date finishDate;
    private Date invalidFinishDate;

    private Validator validator;

    public SpotTest() {
        try {
            startDate = simpleDateTimeFormatter.parse("2017-10-12T10:15:10");
            finishDate = simpleDateTimeFormatter.parse("2017-10-12T14:15:10");
            invalidFinishDate = simpleDateTimeFormatter.parse("2017-10-12T09:15:10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void testSerialization() {
        Spot spot = new Spot(registrationNumber, DriverType.REGULAR, startDate);
        Spot other = (Spot) SerializationUtils.deserialize(SerializationUtils.serialize(spot));

//        assertThat(other.getUuid()).isEqualTo(spot.getUuid());
        assertThat(other.getVehiclePlate()).isEqualTo(spot.getVehiclePlate());
        assertThat(other.getDriverType()).isEqualTo(spot.getDriverType());
        assertThat(other.getBeginDate()).isEqualTo(spot.getBeginDate());
//        assertThat(other.getFinishDate()).isEqualTo(spot.getFinishDate());
    }

    @Test
    public void shouldBeSetCorrectParameters() {
        Spot spot = new Spot(registrationNumber, DriverType.REGULAR, startDate);

        assertThat(spot.getVehiclePlate()).isEqualTo(registrationNumber);
        assertThat(spot.getDriverType()).isEqualTo(DriverType.REGULAR);
        assertThat(spot.getDriverType()).isNotEqualTo(DriverType.VIP);
        assertThat(spot.getBeginDate()).isEqualTo(startDate);
        //      assertThat(spot.getFinishDate()).isEqualTo(finishDate);
    }

    @Test
    public void testValidationForWholeValidSpot() {
        Spot spot1 = new Spot(registrationNumber, DriverType.REGULAR, startDate);

        Set<ConstraintViolation<Spot>> violations = this.validator.validate(spot1);
        assertTrue(violations.isEmpty());

        Spot spot2 = new Spot(registrationNumber, DriverType.REGULAR, startDate);

        Set<ConstraintViolation<Spot>> violations2 = this.validator.validate(spot2);
        assertTrue(violations2.isEmpty());
    }

    @Test
    public void shouldReturnOneViolationBecauseOfRegistrationNumberAsNullParameter() {
        Spot spot = new Spot(null, DriverType.REGULAR, startDate);

        Set<ConstraintViolation<Spot>> violations = this.validator.validate(spot);
        assertTrue(violations.size() > 0);
    }

    @Test
    void shouldReturnOneViolationBecauseOfDriverTypeAsNull() {
        Spot spot = new Spot(registrationNumber, null, startDate);

        Set<ConstraintViolation<Spot>> violations = this.validator.validate(spot);
        assertTrue(violations.size() > 0);
    }

    @Test
    void shouldReturnOneViolationBecauseOfStartDateAsNull() {
        Spot spot = new Spot(registrationNumber, DriverType.REGULAR, null);

        Set<ConstraintViolation<Spot>> violations = this.validator.validate(spot);
        assertTrue(violations.size() > 0);
    }

//    @Test
//    void shouldNotReturnViolationsBecauseOfDateFinishAsNull() {
//        Spot spot = new Spot(registrationNumber, DriverType.REGULAR, startDate, null);
//
//        Set<ConstraintViolation<Spot>> violations = this.validator.validate(spot);
//        assertTrue(violations.isEmpty());
//    }

//    @Test
//    void shouldReturnViolationBecauseOfInvalidFinishDate() {
//        Spot spot = new Spot(registrationNumber, DriverType.REGULAR, startDate, invalidFinishDate);
//
//        Set<ConstraintViolation<Spot>> violations = this.validator.validate(spot);
//        assertTrue(!violations.isEmpty());
//    }

    @Test
    void shouldReturnViolationBecauseOfIncorrectRegistrationNumber() {
        String[] registrationNumbers = {
                "e12345",
                "12345",
                "qeee12345",
                "registrationNo",
                "qwe123456",
                "qwe123",
                "E12345",
                "12345",
                "QEEE12345",
                "registrationNo",
                "QWE123456",
                "QWE123"};

        for (int i = 0; i < registrationNumbers.length; i++) {
            Spot spot = new Spot(registrationNumbers[i], DriverType.REGULAR, startDate);

            Set<ConstraintViolation<Spot>> violations = this.validator.validate(spot);
            assertTrue(violations.size() == 1);
        }
    }

//    @Test
//    public void shouldReturnEmptyOptionalBecauseOfNullFinishDate() {
//        Spot spot = new Spot(registrationNumber, DriverType.REGULAR, startDate, null);
//
//        assertEquals(Optional.empty(), spot.getFee(Currency.PLN));
//    }

//    @Test
//    public void shouldReturnCorrectFeeForRegularDriver() throws Exception {
//        String[] startDates = {"2017-10-12T11:15:48", "2017-10-12T11:15:48",
//                "2017-10-12T11:15:48", "2017-10-12T11:15:48",
//                "2017-10-12T00:15:48", "2017-10-12T11:15:48", "2020-12-12T10:10:10"
//        };
//        String[] finishDates = {"2017-10-12T11:35:12", "2017-10-12T12:35:12",
//                "2017-10-12T13:35:12", "2017-10-12T16:35:12",
//                "2017-10-12T15:35:12", "2017-10-13T11:14:12", "2020-12-12T22:13:10"
//        };
//        BigDecimal[] returnedFee = {new BigDecimal("1.0"), new BigDecimal("3.0"),
//                new BigDecimal("7.0"), new BigDecimal("63.0"),
//                new BigDecimal("65535.0"), new BigDecimal("16777215.0"), new BigDecimal("8191.0")
//        };
//
//        for (int i = 0; i < startDates.length; i++) {
//            Date start = simpleDateTimeFormatter.parse(startDates[i]);
//            Date stop = simpleDateTimeFormatter.parse(finishDates[i]);
//            Spot spot = new Spot(registrationNumber, DriverType.REGULAR, start, stop);
//
//            assertEquals(returnedFee[i], spot.getFee(Currency.PLN).get());
//        }
//    }
//
//    @Test
//    public void shouldReturnCorrectFeeForVipDriver() throws Exception {
//        String[] beginDates = {"2017-10-12T11:15:48", "2017-10-12T11:15:48",
//                "2017-10-12T11:15:48", "2017-10-12T11:15:48",
//                "2017-10-12T00:15:48", "2017-10-12T11:15:48"
//        };
//        String[] endDates = {"2017-10-12T11:35:12", "2017-10-12T12:35:12",
//                "2017-10-12T13:35:12", "2017-10-12T16:35:12",
//                "2017-10-12T15:35:12", "2017-10-13T11:14:12"
//        };
//        BigDecimal[] returnedFee = {new BigDecimal("0.0"), new BigDecimal("2.0"),
//                new BigDecimal("5.0"), new BigDecimal("26.4"),
//                new BigDecimal("1747.6"), new BigDecimal("44887.0")
//        };
//
//        for (int i = 0; i < beginDates.length; i++) {
//            Date start = simpleDateTimeFormatter.parse(beginDates[i]);
//            Date stop = simpleDateTimeFormatter.parse(endDates[i]);
//            Spot spot = new Spot(registrationNumber, DriverType.VIP, start, stop);
//
//            assertEquals(returnedFee[i], spot.getFee(Currency.PLN).get());
//        }
//    }

    //TODO: add test for other currencies
}
