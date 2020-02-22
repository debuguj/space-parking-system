package pl.debuguj.parkingspacessystem.repository;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.debuguj.parkingspacessystem.service.exceptions.VehicleIsAlreadyActiveInSystemException;
import pl.debuguj.parkingspacessystem.service.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 12.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingSpaceStubTest {

    @Autowired
    ParkingSpaceRepo parkingSpaceRepo;

//    @Autowired
//    private Constants constants;

    private static SimpleDateFormat timeDateFormat;
    private static SimpleDateFormat dayDateFormat;

    private ParkingSpace parkingSpace;
    private String registrationNo;
    private Date beginDate;
    private Date endDate;

    @Before
    public void before() throws ParseException, IncorrectEndDateException {
//        timeDateFormat = new SimpleDateFormat(constants.properties.getTimeFormat());
//        dayDateFormat = new SimpleDateFormat(constants.properties.getDayFormat());
//
//        registrationNo = "12345";
//        beginDate = timeDateFormat.parse("2017-10-14 11:15:48");
//        endDate = timeDateFormat.parse("2017-10-14 21:35:12");
//        parkingSpace = new ParkingSpace(registrationNo, beginDate, endDate);

    }

//    @After
//    public void after(){
//        parkingSpaceRepo.removeAll();
//    }

    @Test
    public void testCreatingNewParkingSpace() throws Exception {

//        parkingSpaceRepo.create(parkingSpace);
//
//        Optional<ParkingSpace> psFromDao = parkingSpaceRepo.findByRegistrationNo(parkingSpace.getCarRegistrationNumber());
//
//        assertNotNull(psFromDao);
//
//        assertEquals(parkingSpace, psFromDao.get());
//        assertEquals(parkingSpace.getBeginTime(),psFromDao.get().getBeginTime());
//        assertEquals(parkingSpace.getDriverType(),psFromDao.get().getDriverType());
//        assertEquals(parkingSpace.getEndTime(),psFromDao.get().getEndTime());
    }

    @Test(expected = VehicleIsAlreadyActiveInSystemException.class)
    public void testCreatingNewParkingSpaceException() throws Exception {
//
//        parkingSpaceRepo.create(parkingSpace);
//
//        parkingSpaceRepo.create(parkingSpace);
    }
    @Test
    public void testFindingParkingSpaceByRegistrationNo() throws Exception {

//        parkingSpaceRepo.create(parkingSpace);
//
//        Optional<ParkingSpace> psFromDao = parkingSpaceRepo.findByRegistrationNo(parkingSpace.getCarRegistrationNumber());
//
//        assertTrue(psFromDao.isPresent());
//
//        String registrationNo = "997755";
//
//        psFromDao = parkingSpaceRepo.findByRegistrationNo(registrationNo);
//
//        assertFalse(psFromDao.isPresent());
    }

    @Test
    public void testGettingAllParkingSpaces() throws Exception {
        createTestItems();

   //     int size = parkingSpaceRepo.getAll().size();

   //     assertEquals("Should return 5 number od object", 5, size);
    }   

    @Test
    public void testUpdatingParkingSpaceEndTimeToNewCorrectEndTime() throws Exception  {

//        parkingSpaceRepo.create(parkingSpace);
//
//        Date newCorrectDate = timeDateFormat.parse("2017-10-14 12:15:48");
//        parkingSpaceRepo.updateEndTime(parkingSpace.getCarRegistrationNumber(), newCorrectDate);
//
//        Optional<ParkingSpace> psFromDao = parkingSpaceRepo.findByRegistrationNo(parkingSpace.getCarRegistrationNumber());
//
//        assertTrue(psFromDao.isPresent());
//        assertEquals(parkingSpace, psFromDao.get());
//        assertEquals(parkingSpace.getBeginTime(), psFromDao.get().getBeginTime());
//        assertEquals(parkingSpace.getDriverType(), psFromDao.get().getDriverType());
//        assertEquals(newCorrectDate, psFromDao.get().getEndTime());
    }

    @Test(expected = IncorrectEndDateException.class)
    public void testUpdatingParkingSpaceEndTimeToNewIncorrectEndTime() throws Exception  {
//
//        parkingSpaceRepo.create(parkingSpace);
//
//        Date newIncorrectDate = timeDateFormat.parse("2017-10-14 10:15:48");
//
//        parkingSpaceRepo.updateEndTime(parkingSpace.getCarRegistrationNumber(), newIncorrectDate);

    }

    @Test
    public void testRemovingAllItems() throws Exception {
        createTestItems();
//
//        parkingSpaceRepo.removeAll();
//
//        assertTrue(0 == parkingSpaceRepo.getAll().size());
    }


    @Test
    public void testFindingByDate() throws Exception{
//        createTestItems();
//
//        Date date = dayDateFormat.parse("2017-10-14");
//        Collection<ParkingSpace> list = parkingSpaceRepo.findByDate(date);
//
//        assertFalse(list.isEmpty());
//        assertEquals(2, list.size());
//
//        date = dayDateFormat.parse("2017-10-13");
//        list = parkingSpaceRepo.findByDate(date);
//
//        assertFalse(list.isEmpty());
//        assertEquals(3, list.size());
//
//        date = dayDateFormat.parse("2017-10-1");
//        list = parkingSpaceRepo.findByDate(date);
//
//        assertTrue(list.isEmpty());
//        assertEquals(0, list.size());

    }
    private void createTestItems() throws ParseException, IncorrectEndDateException, VehicleIsAlreadyActiveInSystemException {

//        parkingSpaceRepo.removeAll();
//
//        parkingSpaceRepo.create(new ParkingSpace("66666",
//                timeDateFormat.parse("2017-10-13 10:25:48"),
//                timeDateFormat.parse("2017-10-13 13:35:12")));
//        parkingSpaceRepo.create(new ParkingSpace("77777",
//                timeDateFormat.parse("2017-10-13 12:25:48"),
//                timeDateFormat.parse("2017-10-13 17:35:12")));
//        parkingSpaceRepo.create(new ParkingSpace("88888",
//                timeDateFormat.parse("2017-10-13 15:25:48"),
//                timeDateFormat.parse("2017-10-13 16:35:12")));
//        parkingSpaceRepo.create(new ParkingSpace("99999",
//                timeDateFormat.parse("2017-10-14 20:25:48"),
//                timeDateFormat.parse("2017-10-14 21:35:12")));
//        parkingSpaceRepo.create(new ParkingSpace("99998",
//                timeDateFormat.parse("2017-10-14 11:15:48"),
//                timeDateFormat.parse("2017-10-14 12:35:12")));
    }

}