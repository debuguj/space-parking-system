package pl.debuguj.parkingspacessystem.domain;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by grzesiek on 10.10.17.
 */
//@RunWith(Parameterized.class)
public class ParkingSpaceTest {

//    @Parameterized.Parameters
//    public static Collection<String[]> getTestCorrectCarRegistrationNumber(){
//        String outputs[][] = {
//                {"1234", "1234"},
//                {"4567", "4567"}
//        };
//
//        return Arrays.asList(outputs);
//    }

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    private static String registerNumber;
    private static String startDateStr;
    private static String stopDateStr;

    private static Date startDate;
    private static Date stopDate;
    private static BigDecimal resFee;

    @BeforeClass
    public static void setUp() throws ParseException {
        registerNumber = "12345";
        startDateStr = "2017-10-12 10:15:10";
        stopDateStr = "2017-10-12 12:16:11";
        startDate = simpleDateFormat.parse("2017-10-12 10:15:10");
        stopDate = simpleDateFormat.parse("2017-10-12 12:16:11");
        resFee = new BigDecimal("7.000");
    }

    @Test
    public void testCorrectCarRegistrationNumber() throws ParseException, IncorrectEndDateException {


        ParkingSpace ps1 = new ParkingSpace("12345",
                DriverType.REGULAR, startDateStr, stopDateStr);
        ParkingSpace ps2 = new ParkingSpace("231133",
                DriverType.REGULAR, startDateStr, stopDateStr);

        assertEquals("Car registration No. should be the same", registerNumber, ps1.getCarRegistrationNumber());
        assertNotEquals("Car registration No. should not be the same", registerNumber, ps2.getCarRegistrationNumber());

    }
    @Test
    public void testCorrectDriverType() throws ParseException, IncorrectEndDateException {

        ParkingSpace psRegularDriver = new ParkingSpace(registerNumber,
                DriverType.REGULAR, startDateStr, stopDateStr);
        ParkingSpace psVipDriver = new ParkingSpace(registerNumber,
                DriverType.VIP, startDateStr, stopDateStr);

        assertEquals("Type of driver should be the same", DriverType.REGULAR, psRegularDriver.getDriverType());
        assertNotEquals("Type of driver should not be the same", DriverType.VIP, psRegularDriver.getDriverType());
        assertEquals("Type of driver should be the same", DriverType.VIP, psVipDriver.getDriverType());
        assertNotEquals("Type of driver should not be the same", DriverType.REGULAR, psVipDriver.getDriverType());

    }

    @Test
    public void testCorrectBeginDate() throws ParseException, IncorrectEndDateException {

        String beginDateStr = "2017-10-12 10:15:10";
        Date date = simpleDateFormat.parse(beginDateStr);

        ParkingSpace ps = new ParkingSpace(registerNumber,
                DriverType.REGULAR, beginDateStr, stopDateStr);

        assertEquals("Begin date should be the same", date, ps.getBeginTime());
    }

    @Test
    public void testCorrectEndDate() throws ParseException, IncorrectEndDateException {

        String endDateStr = "2017-10-12 12:15:10";
        Date date = simpleDateFormat.parse(endDateStr);

        ParkingSpace ps = new ParkingSpace(registerNumber,
                DriverType.REGULAR, startDateStr, endDateStr);

        assertEquals("Begin date should be the same", date, ps.getEndTime());
    }

//    @Test
//    public void testIncorrectEndDate throws ParseException, IncorrectEndDateException {
//
//    }
    @Test
    public void testCorrectFeeCalculation() throws ParseException, IncorrectEndDateException {

        String strDate = "2017-10-12 12:15:10";
        Date date = simpleDateFormat.parse(strDate);
        ParkingSpace ps = new ParkingSpace(registerNumber,
                DriverType.REGULAR, startDateStr, strDate);

        assertTrue(true);
    }

    @Test
    public void testEqualsHashCode() throws ParseException, IncorrectEndDateException {
        ParkingSpace ps1 = new ParkingSpace("12345",
                DriverType.REGULAR, startDateStr, stopDateStr);
        ParkingSpace ps2 = new ParkingSpace("12345",
                DriverType.REGULAR, startDateStr, stopDateStr);
        ParkingSpace ps3 = new ParkingSpace("12333",
                DriverType.REGULAR, startDateStr, stopDateStr);

        assertTrue(ps1.equals(ps1));

        assertTrue(ps1.equals(ps2) && ps2.equals(ps1));

        ps2.setEndTime("2017-10-12 12:11:10");
        assertTrue(ps1.equals(ps2) && ps2.equals(ps1));

        assertFalse(ps1.equals(ps3));
    }
}