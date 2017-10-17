package pl.debuguj.parkingspacessystem.services;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
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

    private ParkingSpace parkingSpace;
    private String registrationNo;
    private Date beginDate;
    private Date endDate;

    @BeforeClass
    public static void beforeClass(){
        timeDateFormat = new SimpleDateFormat(TIME_PATTERN);
        dayDateFormat = new SimpleDateFormat(DAY_PATTERN);
    }

    @Before
    public void before() throws Exception{

        registrationNo = "12345";
        beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
        endDate = timeDateFormat.parse("2017-10-13 13:35:12");
        parkingSpace = new ParkingSpace(registrationNo, beginDate, endDate);

        parkingSpaceManagementService.removeAllParkingSpaces();

        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("66666",
                timeDateFormat.parse("2017-10-13 10:25:48"),
                timeDateFormat.parse("2017-10-13 10:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("77777",
                timeDateFormat.parse("2017-10-13 12:25:48"),
                timeDateFormat.parse("2017-10-13 14:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("88888",
                timeDateFormat.parse("2017-10-13 15:25:48"),
                timeDateFormat.parse("2017-10-13 16:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("99999",
                timeDateFormat.parse("2017-10-14 20:25:48"),
                timeDateFormat.parse("2017-10-14 21:35:12")));
        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("99998",
                timeDateFormat.parse("2017-10-14 11:15:48"),
                timeDateFormat.parse("2017-10-14 12:35:12")));

    }

    @After
    public void after(){
        parkingSpaceManagementService.removeAllParkingSpaces();
    }


    @Test
    public void testCorrectFeeEstimation() throws Exception {

        BigDecimal fee = new BigDecimal("7.0");
        BigDecimal feeFromService = parkingSpaceManagementService.reserveParkingSpace(parkingSpace);

        assertEquals("Parking fee should be the same", fee, feeFromService);
    }

    @Test
    public void testIfVehicleIsRegisteredIntoSystemByTimestamp() throws Exception {

        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);

        Date currentDate = timeDateFormat.parse("2017-10-13 12:10:10");
        boolean result = parkingSpaceManagementService.checkVehicle(registrationNo, currentDate);
        assertTrue(result);

        currentDate = timeDateFormat.parse("2017-10-13 23:55:10");
        result = parkingSpaceManagementService.checkVehicle(registrationNo, currentDate);
        assertFalse(result);
    }

    @Test
    public void testIfVehicleIsRegisteredIntoSystemByRegistrationNo() throws Exception {

        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);

        Date currentDate = timeDateFormat.parse("2017-10-13 12:10:10");
        boolean result = parkingSpaceManagementService.checkVehicle(registrationNo, currentDate);

        assertTrue(result);

        String tempRegistrationNo = "354677";
        currentDate = timeDateFormat.parse("2017-10-13 12:10:10");
        result = parkingSpaceManagementService.checkVehicle(tempRegistrationNo, currentDate);

        assertFalse(result);

    }

    @Test
    public void testReturnedFeeAfterStopParkingMeter() throws Exception {

        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);

        Date date = timeDateFormat.parse("2017-10-13 13:40:10");
        BigDecimal fee = new BigDecimal("7.0");
        BigDecimal feeFromSystem = parkingSpaceManagementService
                .stopParkingMeter(parkingSpace.getCarRegistrationNumber(), date);


        assertEquals("Returned values should be equal", fee, feeFromSystem);

    }

    @Test(expected = IncorrectEndDateException.class)
    public void testThrowingExceptionWhenIncorrectEndTime() throws Exception {

        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);

        Date date = timeDateFormat.parse("2017-10-13 11:00:10");

        parkingSpaceManagementService.stopParkingMeter(parkingSpace.getCarRegistrationNumber(), date);
    }

    @Test
    public void testIncomePerDay() throws Exception {

        Date date = dayDateFormat.parse("2017-10-14");
        BigDecimal incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
        BigDecimal income = new BigDecimal("6.0");

        assertEquals("Income should be the same", income, incomeFromSystem);

        date = dayDateFormat.parse("2017-10-18");
        incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
        income = new BigDecimal("0.0");

        assertEquals("Income should be the same", income, incomeFromSystem);

        date = dayDateFormat.parse("2017-10-13");
        incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
        income = new BigDecimal("11.0");

        assertEquals("Income should be the same", income, incomeFromSystem);
    }

    @Test
    public void testGettingCountOfReservedSpaces() throws Exception {

        int sizeFromSystem = parkingSpaceManagementService.getReservedSpacesCount();

        assertEquals("Returned size should be 5",5 ,sizeFromSystem);
    }

    @Test
    public void testRemovingAllParkingSpacesFromSystem() throws Exception {

        parkingSpaceManagementService.removeAllParkingSpaces();

        int size = parkingSpaceManagementService.getReservedSpacesCount();

        assertEquals("List should be empty size==0", 0,size);
    }

}