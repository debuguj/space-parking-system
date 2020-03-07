package pl.debuguj.parkingspacessystem.spot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by GB on 05.03.2020.
 */

public class SpotRepoStubImplTest {

    private final SpotRepoStubImpl parkingSpaceRepo = new SpotRepoStubImpl();

    private final SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Spot parkingSpaceActive;
    private String registrationNumber;
    private Date beginDate;
    private Date endDate;

    @BeforeEach
    public void before() throws ParseException {
        registrationNumber = "WZE12345";
        beginDate = timeDateFormat.parse("2017-10-14T11:15:48");
        endDate = timeDateFormat.parse("2017-10-14T21:35:12");
        parkingSpaceActive = new Spot(registrationNumber, DriverType.REGULAR, beginDate);
    }

    @AfterEach
    public void after() {

    }

    @Test
    public void shouldReturnEmptyOptional() {
        Optional<Boolean> opt = parkingSpaceRepo.save(parkingSpaceActive);

        assertFalse(opt.isPresent());
    }

    @Test
    public void shouldSaveNewActiveParkingSpace() {

        parkingSpaceRepo.save(parkingSpaceActive);

        Optional<Spot> psFromRepo = parkingSpaceRepo.find(registrationNumber);

        assertNotNull(psFromRepo);

        assertEquals(parkingSpaceActive, psFromRepo.get());
        assertEquals(parkingSpaceActive.getBeginDate(), psFromRepo.get().getBeginDate());
        assertEquals(parkingSpaceActive.getDriverType(), psFromRepo.get().getDriverType());
    }

    @Test
    public void testFindingParkingSpaceByRegistrationNo() {

        parkingSpaceRepo.save(parkingSpaceActive);
        Optional<Spot> psFromDao = parkingSpaceRepo.find(registrationNumber);

        assertTrue(psFromDao.isPresent());

        String registrationNo = "997755";
        psFromDao = parkingSpaceRepo.find(registrationNo);

        assertFalse(psFromDao.isPresent());
    }


//    @Test
//    public void testFindingByDate() throws Exception {
//
//        createTestItems();
//
//        Date date = dayDateFormat.parse("2017-10-14");
//        List<SpaceFinished> list = (List<SpaceFinished>) parkingSpaceRepo.findAllFinished(date);
//
//        assertEquals(2, list.size());
//
//        date = dayDateFormat.parse("2017-10-13");
//        list = (List<SpaceFinished>) parkingSpaceRepo.findAllFinished(date);
//
//        assertEquals(3, list.size());
//
//        date = dayDateFormat.parse("2017-10-1");
//        list = (List<SpaceFinished>) parkingSpaceRepo.findAllFinished(date);
//
//        assertEquals(0, list.size());
//
//        parkingSpaceRepo.removeAll();
//
//    }

    private void createTestItems() throws ParseException {

        Spot ps0 = new Spot("WWW66666", DriverType.REGULAR, timeDateFormat.parse("2017-10-13T10:25:48"));
        parkingSpaceRepo.save(ps0);
        parkingSpaceRepo.updateFinishDate(ps0.getVehicleRegistrationNumber(), timeDateFormat.parse("2017-10-13T13:35:12"));

        Spot ps1 = new Spot("WSQ77777", DriverType.REGULAR, timeDateFormat.parse("2017-10-13T12:25:48"));
        parkingSpaceRepo.save(ps1);
        parkingSpaceRepo.updateFinishDate(ps1.getVehicleRegistrationNumber(), timeDateFormat.parse("2017-10-13T17:35:12"));

        Spot ps2 = new Spot("QAZ88888", DriverType.REGULAR, timeDateFormat.parse("2017-10-13T15:25:48"));
        parkingSpaceRepo.save(ps2);
        parkingSpaceRepo.updateFinishDate(ps2.getVehicleRegistrationNumber(), timeDateFormat.parse("2017-10-13T16:35:12"));

        Spot ps3 = new Spot("EDC99999", DriverType.REGULAR, timeDateFormat.parse("2017-10-14T20:25:48"));
        parkingSpaceRepo.save(ps3);
        parkingSpaceRepo.updateFinishDate(ps3.getVehicleRegistrationNumber(), timeDateFormat.parse("2017-10-14T21:35:12"));

        Spot ps4 = new Spot("FDR99998", DriverType.REGULAR, timeDateFormat.parse("2017-10-14T11:15:48"));
        parkingSpaceRepo.save(ps4);
        parkingSpaceRepo.updateFinishDate(ps4.getVehicleRegistrationNumber(), timeDateFormat.parse("2017-10-14T12:35:12"));

//                parkingSpaceRepo.save(new ParkingSpaceActive("66666", DriverType.REGULAR,
//                timeDateFormat.parse("2017-10-13 10:25:48"),
//                timeDateFormat.parse("2017-10-13 13:35:12")));

//        parkingSpaceRepo.save(new ParkingSpaceActive("77777",DriverType.REGULAR,
//                timeDateFormat.parse("2017-10-13 12:25:48"),
//                timeDateFormat.parse("2017-10-13 17:35:12")));
//        parkingSpaceRepo.save(new ParkingSpaceActive("88888",DriverType.REGULAR,
//                timeDateFormat.parse("2017-10-13 15:25:48"),
//                timeDateFormat.parse("2017-10-13 16:35:12")));
//        parkingSpaceRepo.save(new ParkingSpaceActive("99999",DriverType.REGULAR,
//                timeDateFormat.parse("2017-10-14 20:25:48"),
//                timeDateFormat.parse("2017-10-14 21:35:12")));
//        parkingSpaceRepo.save(new ParkingSpaceActive("99998",DriverType.REGULAR,
//                timeDateFormat.parse("2017-10-14 11:15:48"),
//                timeDateFormat.parse("2017-10-14 12:35:12")));
    }

}