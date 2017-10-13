package pl.debuguj.parkingspacessystem.dao;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
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

    @BeforeClass
    public static void beforeClass(){
        timeDateFormat = new SimpleDateFormat(TIME_PATTERN);
    }

    @Before
    public void before() throws ParseException{

        parkingSpaceDao.add(new ParkingSpace("12345",
                    timeDateFormat.parse("2017-10-13 10:25:48"),
                    timeDateFormat.parse("2017-10-13 10:35:12")));
        parkingSpaceDao.add(new ParkingSpace("11212",
                    timeDateFormat.parse("2017-10-13 12:25:48"),
                    timeDateFormat.parse("2017-10-13 17:35:12")));
        parkingSpaceDao.add(new ParkingSpace("12344",
                    timeDateFormat.parse("2017-10-13 15:25:48"),
                    timeDateFormat.parse("2017-10-13 16:35:12")));
        parkingSpaceDao.add(new ParkingSpace("54345",
                    timeDateFormat.parse("2017-10-14 20:25:48"),
                    timeDateFormat.parse("2017-10-14 21:35:12")));
        parkingSpaceDao.add(new ParkingSpace("12333",
                    timeDateFormat.parse("2017-10-14 11:15:48"),
                    timeDateFormat.parse("2017-10-14 12:35:12")));

    }

    @After
    public void after(){
        parkingSpaceDao.removeAllItems();
    }

    @Test
    public void testAddNewParkingSpace() throws Exception {
        Date begin = timeDateFormat.parse("2017-10-14 11:15:48");
        Date end = timeDateFormat.parse("2017-10-14 21:35:12");

        ParkingSpace ps = new ParkingSpace("56788", begin, end);

        parkingSpaceDao.add(ps);

        ParkingSpace psFromDao = parkingSpaceDao.getParkingSpaceByRegistrationNo(ps.getCarRegistrationNumber());

        assertEquals("Should get the same object", ps, psFromDao);
    }

    @Test
    public void testGettingAllParkingSpaces() throws Exception {

        int size = parkingSpaceDao.getAllParkingSpaces().size();

        assertEquals("Should return 5 number od object", 5, size);
    }

    @Test
    public void testChangeParkingEndTime() throws Exception  {
        Date begin = timeDateFormat.parse("2017-10-14 11:15:48");
        Date end = timeDateFormat.parse("2017-10-14 13:15:48");

        ParkingSpace ps = new ParkingSpace("99999", begin, end);
        parkingSpaceDao.add(ps);

        Date changeDate = timeDateFormat.parse("2017-10-14 12:15:48");
        parkingSpaceDao.changeParkingSpaceEndTime(ps.getCarRegistrationNumber(), changeDate);

        ParkingSpace psFromDao = parkingSpaceDao.getParkingSpaceByRegistrationNo(ps.getCarRegistrationNumber());

        assertEquals("New end time should be the same", changeDate, psFromDao.getEndTime());
    }

    @Test
    public void testRemovingAllItems() throws Exception {
        parkingSpaceDao.removeAllItems();

        assertTrue(0 == parkingSpaceDao.getAllParkingSpaces().size());
    }

}