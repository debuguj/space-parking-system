package pl.debuguj.parkingspacessystem.spot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.debuguj.parkingspacessystem.spot.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spot.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.spot.exceptions.VehicleIsAlreadyActiveInSystemException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by GB on 12.10.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpotManagementServiceImplTest {

    @Mock
    private SpotRepo spotRepo;

    private SpaceManagementServiceImpl spaceManagementService;

    private static SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Spot parkingSpaceActive;
    private String registrationNo = "12345";
    private Date beginDate;
    private Date endDate;

    @Before
    public void before() throws Exception {
        //MockitoAnnotations.initMocks(this);
        spaceManagementService = new SpaceManagementServiceImpl(spotRepo);

        beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
        endDate = timeDateFormat.parse("2017-10-13 13:35:12");
        parkingSpaceActive = new Spot(registrationNo, DriverType.REGULAR, beginDate, endDate);

//        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("66666",
//                timeDateFormat.parse("2017-10-13 10:25:48"),
//                timeDateFormat.parse("2017-10-13 10:35:12")));
//        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("77777",
//                timeDateFormat.parse("2017-10-13 12:25:48"),
//                timeDateFormat.parse("2017-10-13 14:35:12")));
//        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("88888",
//                timeDateFormat.parse("2017-10-13 15:25:48"),
//                timeDateFormat.parse("2017-10-13 16:35:12")));
//        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("99999",
//                timeDateFormat.parse("2017-10-14 20:25:48"),
//                timeDateFormat.parse("2017-10-14 21:35:12")));
//        parkingSpaceManagementService.reserveParkingSpace(new ParkingSpace("99998",
//                timeDateFormat.parse("2017-10-14 11:15:48"),
//                timeDateFormat.parse("2017-10-14 12:35:12")));

    }

    @Test
    public void testReturnTrueBecauseOfSavingToStorage() throws Exception {
//        //GIVEN
//        Date beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
//        Date finishDate = timeDateFormat.parse("2017-10-13 13:35:12");
//
//        SpaceActive psa = new SpaceActive("12345", DriverType.REGULAR, beginDate);
//        //WHEN
//        when(spaceRepo.findActive(psa.getVehicleRegistrationNumber())).thenReturn(Optional.empty());
//        when(spaceRepo.save(psa)).thenReturn(true);
//        //THEN
//        boolean isStored = spaceManagementService.reserveParkingSpace(psa);
//        assertTrue("Parking space should be saved to repo and return true", isStored);
    }

    @Test
    public void testReturnFalseBecauseOfSaveToRepoWasImpossible() throws Exception {
//        //GIVEN
//        Date beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
//        Date finishDate = timeDateFormat.parse("2017-10-13 13:35:12");
//
//        SpaceActive psa = new SpaceActive("12345", DriverType.REGULAR, beginDate);
//        //WHEN
//        when(spaceRepo.findActive(psa.getVehicleRegistrationNumber())).thenReturn(Optional.empty());
//        when(spaceRepo.save(psa)).thenReturn(false);
//        //THEN
//        boolean isStored = spaceManagementService.reserveParkingSpace(psa);
//        assertFalse("Parking space shouldn't be saved to repo and return false", isStored);
    }

    @Test(expected = VehicleIsAlreadyActiveInSystemException.class)
    public void testReturnVehicleIsAlreadyActiveInSystemException() throws Exception {
//        //GIVEN
//        Date beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
//        Date finishDate = timeDateFormat.parse("2017-10-13 13:35:12");
//
//        SpaceActive psa = new SpaceActive("12345", DriverType.REGULAR, beginDate);
//        //WHEN
//        when(spaceRepo.findActive(psa.getVehicleRegistrationNumber())).thenReturn(Optional.of(psa));
//        when(spaceRepo.save(psa)).thenReturn(false);
//        //THEN
//        spaceManagementService.reserveParkingSpace(psa);

    }

    @Test
    public void testShouldReturnTrueWhenCheckingActivationOfVehicle() throws Exception {
        //GIVEN
        Date beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
        Date currentDate = timeDateFormat.parse("2017-10-13 13:34:12");

        Spot psa = new Spot("12345", DriverType.REGULAR, beginDate, null);
        //WHEN
        when(spotRepo.find(psa.getVehicleRegistrationNumber())).thenReturn(Optional.of(psa));
        //THEN
        boolean exists = spaceManagementService.checkVehicle(psa.getVehicleRegistrationNumber(), currentDate);
        assertTrue("Vehicle should exists in database", exists);
    }

    @Test
    public void testShouldReturnFalseWhenCheckingActivationOfVehicle() throws Exception {
        //GIVEN
        Date beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
        Date finishDate = timeDateFormat.parse("2017-10-13 13:35:12");
        Date currentDate = timeDateFormat.parse("2017-10-13 13:34:12");

        Spot psa = new Spot("12345", DriverType.REGULAR, beginDate, null);
        //WHEN
        when(spotRepo.find(psa.getVehicleRegistrationNumber())).thenReturn(Optional.empty());
        //THEN
        boolean exists = spaceManagementService.checkVehicle(psa.getVehicleRegistrationNumber(), currentDate);
        assertFalse("Vehicle shouldn't exists in database", exists);
    }

    @Test
    public void testCorrectFeeEstimation() throws Exception {
        //GIVEN
        BigDecimal fee = new BigDecimal("7.0");

        Date beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
        Date finishDate = timeDateFormat.parse("2017-10-13 13:35:12");

        Spot psa = new Spot("12345", DriverType.REGULAR, beginDate, null);
        Spot spotFinishedUpdated = new Spot(psa.getVehicleRegistrationNumber(), psa.getDriverType()
                , psa.getBeginDate(), finishDate);
        //WHEN
        when(spotRepo.updateFinishDate(psa.getVehicleRegistrationNumber(), finishDate)).thenReturn(Optional.of(spotFinishedUpdated));

        //THEN,
        BigDecimal feeFromService = spaceManagementService.stopParkingMeter(parkingSpaceActive.getVehicleRegistrationNumber(), finishDate, Currency.PLN);

        assertEquals("Parking fee should be the same", fee, feeFromService);
    }

    @Test(expected = ParkingSpaceNotFoundException.class)
    public void testShouldThrowParkingSpaceNotFoundException() throws Exception {
        //GIVEN
        Date beginDate = timeDateFormat.parse("2017-10-13 11:15:48");
        Date finishDate = timeDateFormat.parse("2017-10-13 13:35:12");

        Spot psa = new Spot("12345", DriverType.REGULAR, beginDate, null);
        //WHEN
        when(spotRepo.updateFinishDate(psa.getVehicleRegistrationNumber(), finishDate)).thenReturn(Optional.empty());
        //THEN
        BigDecimal feeFromService = spaceManagementService.stopParkingMeter(psa.getVehicleRegistrationNumber(), finishDate, Currency.PLN);
    }

    @Test(expected = IncorrectEndDateException.class)
    public void testShouldThrowIncorrectEndDateException() throws Exception {
        //GIVEN
        Date beginDate = timeDateFormat.parse("2017-10-13 14:15:48");
        Date finishDate = timeDateFormat.parse("2017-10-13 13:35:12");

        Spot psa = new Spot("12345", DriverType.REGULAR, beginDate, null);
        Spot spotFinishedUpdated = new Spot(psa.getVehicleRegistrationNumber(), psa.getDriverType()
                , psa.getBeginDate(), finishDate);
        //WHEN
        when(spotRepo.updateFinishDate(psa.getVehicleRegistrationNumber(), finishDate)).thenReturn(Optional.of(spotFinishedUpdated));
        //THEN
        BigDecimal feeFromService = spaceManagementService.stopParkingMeter(psa.getVehicleRegistrationNumber(), finishDate, Currency.PLN);
    }

    @Test
    public void testReturningCorrectIncomePerDay() throws Exception {
        //GIVEN
        Date date = dayDateFormat.parse("2017-10-14");
        BigDecimal income = new BigDecimal("6.0");

        List<Spot> list = new ArrayList<>();
        list.add(new Spot("99998", DriverType.REGULAR, timeDateFormat.parse("2017-10-14 11:15:48"), timeDateFormat.parse("2017-10-14 12:35:12")));
        list.add(new Spot("99999", DriverType.REGULAR, timeDateFormat.parse("2017-10-14 20:25:48"), timeDateFormat.parse("2017-10-14 21:35:12")));

        //WHEN
        when(spotRepo.findAllFinished(date)).thenReturn(list);

        BigDecimal incomeFromSystem = spaceManagementService.getIncomePerDay(date, Currency.PLN);
        //THEN
        assertEquals("Income should be the same", income, incomeFromSystem);


//        date = dayDateFormat.parse("2017-10-18");
//        incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
//        income = new BigDecimal("0.0");
//
//        assertEquals("Income should be the same", income, incomeFromSystem);
//
//        date = dayDateFormat.parse("2017-10-13");
//        incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
//        income = new BigDecimal("11.0");
//        assertEquals("Income should be the same", income, incomeFromSystem);
    }
//    @Test
//    public void testIfVehicleIsRegisteredIntoSystemByTimestamp() throws Exception {
//
//        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);
//
//        Date currentDate = timeDateFormat.parse("2017-10-13 12:10:10");
//        boolean result = parkingSpaceManagementService.checkVehicle(registrationNo, currentDate);
//        assertTrue(result);
//
//        currentDate = timeDateFormat.parse("2017-10-13 23:55:10");
//        result = parkingSpaceManagementService.checkVehicle(registrationNo, currentDate);
//        assertFalse(result);
//    }
//
//    @Test
//    public void testIfVehicleIsRegisteredIntoSystemByRegistrationNo() throws Exception {
//
//        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);
//
//        Date currentDate = timeDateFormat.parse("2017-10-13 12:10:10");
//        boolean result = parkingSpaceManagementService.checkVehicle(registrationNo, currentDate);
//
//        assertTrue(result);
//
//        String tempRegistrationNo = "354677";
//        currentDate = timeDateFormat.parse("2017-10-13 12:10:10");
//        result = parkingSpaceManagementService.checkVehicle(tempRegistrationNo, currentDate);
//
//        assertFalse(result);
//
//    }
//
//    @Test
//    public void testReturnedFeeAfterStopParkingMeter() throws Exception {
//
//        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);
//
//        Date date = timeDateFormat.parse("2017-10-13 13:40:10");
//        BigDecimal fee = new BigDecimal("7.0");
//        BigDecimal feeFromSystem = parkingSpaceManagementService
//                .stopParkingMeter(parkingSpace.getCarRegistrationNumber(), date);
//
//
//        assertEquals("Returned values should be equal", fee, feeFromSystem);
//
//    }
//
//    @Test(expected = IncorrectEndDateException.class)
//    public void testThrowingExceptionWhenIncorrectEndTime() throws Exception {
//
//        parkingSpaceManagementService.reserveParkingSpace(parkingSpace);
//
//        Date date = timeDateFormat.parse("2017-10-13 11:00:10");
//
//        parkingSpaceManagementService.stopParkingMeter(parkingSpace.getCarRegistrationNumber(), date);
//    }
//
//    @Test
//    public void testIncomePerDay() throws Exception {
//
//        Date date = dayDateFormat.parse("2017-10-14");
//        BigDecimal incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
//        BigDecimal income = new BigDecimal("6.0");
//
//        assertEquals("Income should be the same", income, incomeFromSystem);
//
//        date = dayDateFormat.parse("2017-10-18");
//        incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
//        income = new BigDecimal("0.0");
//
//        assertEquals("Income should be the same", income, incomeFromSystem);
//
//        date = dayDateFormat.parse("2017-10-13");
//        incomeFromSystem = parkingSpaceManagementService.getIncomePerDay(date);
//        income = new BigDecimal("11.0");
//
//        assertEquals("Income should be the same", income, incomeFromSystem);
//    }

//    @Test
//    public void testGettingCountOfReservedSpaces() throws Exception {
//
//        int sizeFromSystem = parkingSpaceManagementService.getReservedSpacesCount();
//
//        assertEquals("Returned size should be 5",5 ,sizeFromSystem);
//    }
//
//    @Test
//    public void testRemovingAllParkingSpacesFromSystem() throws Exception {
//
//        parkingSpaceManagementService.removeAllParkingSpaces();
//
//        int size = parkingSpaceManagementService.getReservedSpacesCount();
//
//        assertEquals("List should be empty size==0", 0,size);
//    }

}