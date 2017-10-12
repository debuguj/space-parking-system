package pl.debuguj.parkingspacessystem.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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

    @Before
    public void setup() throws ParseException{
        timeDateFormat = new SimpleDateFormat(TIME_PATTERN);
    }

    @Test
    public void testAddNewParkingSpace() throws ParseException {
        Date begin = timeDateFormat.parse("2017-10-14 11:15:48");
        Date end = timeDateFormat.parse("2017-10-14 21:35:12");

        ParkingSpace ps = new ParkingSpace("56788", begin, end);

        parkingSpaceDao.add(ps);

        ParkingSpace psFromDao = parkingSpaceDao.getParkingSpaceByRegistrationNo(ps.getCarRegistrationNumber());

        assertEquals("Should get the same object", ps, psFromDao);
    }

    @Test
    public void testGettingAllParkingSpaces() {
        List<ParkingSpace> list = parkingSpaceDao.getAllParkingSpaces();
        int objectNumber = 5;

        assertEquals("Should return equals number od object", objectNumber, list.size() );
    }

    @Test
    public void testChangeParkingEndTime() throws ParseException {
        Date begin = timeDateFormat.parse("2017-10-14 11:15:48");
        Date end = timeDateFormat.parse("2017-10-14 13:15:48");

        ParkingSpace ps = new ParkingSpace("99999", begin, end);
        parkingSpaceDao.add(ps);

        Date changeDate = timeDateFormat.parse("2017-10-14 12:15:48");
        parkingSpaceDao.changeParkingSpaceEndTime(ps.getCarRegistrationNumber(), changeDate);

        ParkingSpace psFromDao = parkingSpaceDao.getParkingSpaceByRegistrationNo(ps.getCarRegistrationNumber());

        assertEquals("New end time should be the same", changeDate, psFromDao.getEndTime());
    }

}