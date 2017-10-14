package pl.debuguj.parkingspacessystem.dao;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by grzesiek on 12.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingSpaceDaoMockImplTest {

    @Autowired
    ParkingSpaceDao parkingSpaceDao;

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
    public void before() throws ParseException, IncorrectEndDateException {
        parkingSpaceDao.removeAllItems();
        parkingSpaceDao.add(new ParkingSpace("66666",
                    timeDateFormat.parse("2017-10-13 10:25:48"),
                    timeDateFormat.parse("2017-10-13 13:35:12")));
        parkingSpaceDao.add(new ParkingSpace("77777",
                    timeDateFormat.parse("2017-10-13 12:25:48"),
                    timeDateFormat.parse("2017-10-13 17:35:12")));
        parkingSpaceDao.add(new ParkingSpace("88888",
                    timeDateFormat.parse("2017-10-13 15:25:48"),
                    timeDateFormat.parse("2017-10-13 16:35:12")));
        parkingSpaceDao.add(new ParkingSpace("99999",
                    timeDateFormat.parse("2017-10-14 20:25:48"),
                    timeDateFormat.parse("2017-10-14 21:35:12")));
        parkingSpaceDao.add(new ParkingSpace("99998",
                    timeDateFormat.parse("2017-10-14 11:15:48"),
                    timeDateFormat.parse("2017-10-14 12:35:12")));

    }

    @After
    public void after(){
        parkingSpaceDao.removeAllItems();
    }

    @Test
    public void testAddNewParkingSpaceAndFindingParkingSpaceByRegistrationNumber() throws Exception {
        Date begin = timeDateFormat.parse("2017-10-14 11:15:48");
        Date end = timeDateFormat.parse("2017-10-14 21:35:12");

        ParkingSpace ps = new ParkingSpace("56788", begin, end);

        parkingSpaceDao.add(ps);

        ParkingSpace psFromDao = parkingSpaceDao.findParkingSpaceByRegistrationNo(ps.getCarRegistrationNumber());

        assertEquals("Should get the same object", ps, psFromDao);
    }


    @Test
    public void testGettingAllParkingSpaces() throws Exception {

        int size = parkingSpaceDao.getAllParkingSpaces().size();

        assertEquals("Should return 5 number od object", 5, size);
    }

    @Test
    public void testChangingParkingEndTimeToNewCorrectEndTime() throws Exception  {
        Date begin = timeDateFormat.parse("2017-10-14 11:15:48");
        Date end = timeDateFormat.parse("2017-10-14 13:15:48");

        String carRegistrationNo = "99997";

        ParkingSpace ps = new ParkingSpace(carRegistrationNo, begin, end);
        parkingSpaceDao.add(ps);

        Date newCorrectDate = timeDateFormat.parse("2017-10-14 12:15:48");
        parkingSpaceDao.changeParkingSpaceEndTime(carRegistrationNo, newCorrectDate);

        ParkingSpace psFromDao = parkingSpaceDao.findParkingSpaceByRegistrationNo(carRegistrationNo);

        assertEquals("New end time should be the same", newCorrectDate, psFromDao.getEndTime());
    }

    @Test(expected = IncorrectEndDateException.class)
    public void testChangingParkingEndTimeToNewIncorrectEndTime() throws Exception  {
        Date begin = timeDateFormat.parse("2017-10-14 11:15:48");
        Date end = timeDateFormat.parse("2017-10-14 13:15:48");

        String carRegistrationNo = "99998";

        ParkingSpace ps = new ParkingSpace(carRegistrationNo, begin, end);
        parkingSpaceDao.add(ps);

        Date newIncorrectDate = timeDateFormat.parse("2017-10-14 10:15:48");

        parkingSpaceDao.changeParkingSpaceEndTime(carRegistrationNo, newIncorrectDate);

    }
    @Test
    public void testRemovingAllItems() throws Exception {
        parkingSpaceDao.removeAllItems();

        assertTrue(0 == parkingSpaceDao.getAllParkingSpaces().size());
    }

    @Test
    public void testFindingParkingSpacesByDate() throws Exception {

        Date timestamp = dayDateFormat.parse("2017-10-17");
        List<ParkingSpace> list = parkingSpaceDao.findParkingSpacesByDate(timestamp);

        assertEquals("Should return 0 objects", 0, list.size());

        timestamp = dayDateFormat.parse("2017-10-13");
        list = parkingSpaceDao.findParkingSpacesByDate(timestamp);

        assertEquals("Should return objects", 3, list.size());

        timestamp = dayDateFormat.parse("2017-10-14");
        list = parkingSpaceDao.findParkingSpacesByDate(timestamp);

        assertEquals("Should return objects", 2, list.size());
    }

}