package pl.debuguj.parkingspacessystem.services;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.enums.Currency;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by grzesiek on 12.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingSpaceManagementServiceImplTest {

    @Autowired
    ParkingSpaceManagementService parkingSpaceManagementService;

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat timeDateFormat;

    private static final String DAY_PATTERN = "yyyy-MM-dd";
    private static SimpleDateFormat dayDateFormat;

    @BeforeClass
    public static void beforeClass(){
        timeDateFormat = new SimpleDateFormat(TIME_PATTERN);
        dayDateFormat = new SimpleDateFormat(DAY_PATTERN);
    }

    @Before
    public void before() throws Exception{
        parkingSpaceManagementService.removeAllParkingSpaces();
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("12345",
                timeDateFormat.parse("2017-10-13 10:25:48"),
                timeDateFormat.parse("2017-10-13 10:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("11212",
                timeDateFormat.parse("2017-10-13 12:25:48"),
                timeDateFormat.parse("2017-10-13 17:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("12344",
                timeDateFormat.parse("2017-10-13 15:25:48"),
                timeDateFormat.parse("2017-10-13 16:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("54345",
                timeDateFormat.parse("2017-10-14 20:25:48"),
                timeDateFormat.parse("2017-10-14 21:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("12333",
                timeDateFormat.parse("2017-10-14 11:15:48"),
                timeDateFormat.parse("2017-10-14 12:35:12")));

    }

    @After
    public void after(){
        parkingSpaceManagementService.removeAllParkingSpaces();
    }


    @Test
    public void testCorrectFeeEstimation() throws Exception {
        Date begin = timeDateFormat.parse("2017-10-12 10:15:10");
        Date end = timeDateFormat.parse("2017-10-12 11:16:10");

        ParkingSpace ps = new ParkingSpace("12345", begin, end);

        BigDecimal fee = new BigDecimal("3.0");
        BigDecimal feeFromService = parkingSpaceManagementService.reserveParkingSpace(ps);

        assertEquals("Parking fee should be the same", fee, feeFromService);
    }

    @Test
    public void testIfVehicleIsRegisteredIntoSystem() throws Exception {
        Date begin = timeDateFormat.parse("2017-10-12 10:15:10");
        Date end = timeDateFormat.parse("2017-10-12 23:16:10");

        ParkingSpace ps = new ParkingSpace("12345", begin, end);

        parkingSpaceManagementService.reserveParkingSpace(ps);

        //TODO: remove this small date cheating
        Date currentDate = timeDateFormat.parse("2017-10-12 23:10:10");
        boolean result = parkingSpaceManagementService.checkVehicle(ps.getCarRegistrationNumber(), currentDate);
        assertTrue(result);
    }

    @Test
    public void testReturnedFeeAfterStopParkingMeter() throws Exception {
        //TODO: throw exception when new timestamp is smaller than begin time
        Date begin = timeDateFormat.parse("2017-10-12 10:15:10");
        Date end = timeDateFormat.parse("2017-10-12 11:16:10");

        ParkingSpace ps = new ParkingSpace("9999", begin, end);

        parkingSpaceManagementService.reserveParkingSpace(ps);

        Date date = timeDateFormat.parse("2017-10-12 11:12:10");
        BigDecimal fee = new BigDecimal("1.0");
        BigDecimal feeFromSystem = parkingSpaceManagementService.stopParkingMeter(ps.getCarRegistrationNumber(), date);

        assertEquals("Returned values should be equal", fee, feeFromSystem);

    }

    @Test
    public void testIncomePerDay() throws Exception {

        Date date = dayDateFormat.parse("2017-10-14");

        BigDecimal incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);

        BigDecimal income = new BigDecimal("6.0");

        assertEquals("Should income be the same", income, incomeFromSystem);

    }

    @Test
    public void testGettingCountOfReservedSpaces() throws Exception{
        int sizeFromSystem = parkingSpaceManagementService.getReservedSpacesCount();

        assertEquals("Returned size should be 5",5 ,sizeFromSystem);
    }
    @Test
    public void testRemovingAllParkingSpacesFromSystem() throws Exception{
        parkingSpaceManagementService.removeAllParkingSpaces();

        int size = parkingSpaceManagementService.getReservedSpacesCount();

        assertEquals("List should empty size==0", 0,size);
    }

}