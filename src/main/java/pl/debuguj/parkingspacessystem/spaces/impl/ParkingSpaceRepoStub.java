package pl.debuguj.parkingspacessystem.spaces.impl;

import org.springframework.stereotype.Repository;
import pl.debuguj.parkingspacessystem.spaces.SpaceActive;
import pl.debuguj.parkingspacessystem.spaces.SpaceFinished;
import pl.debuguj.parkingspacessystem.spaces.SpaceRepo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by GB on 07.10.17.
 */
@Repository
public class ParkingSpaceRepoStub implements SpaceRepo {

    private static final int MAX_ACTIVE_PARKING_SPACE = 1_000;

    private static Map<String, SpaceActive> mapParkingSpacesActive = new ConcurrentHashMap<>();
    private static Map<UUID, SpaceFinished> mapParkingSpacesFinished = new ConcurrentHashMap<>();

    @Override
    public boolean save(final SpaceActive ps) {
        if (mapParkingSpacesActive.size() < MAX_ACTIVE_PARKING_SPACE) {
            mapParkingSpacesActive.put(ps.getVehicleRegistrationNumber(), ps);
            return true;
        }
        return false;
    }

    @Override
    public Optional<SpaceActive> findActive(final String registrationNo) {
        return mapParkingSpacesActive.values()
                .stream()
                .filter(parkingSpace -> registrationNo.equals(parkingSpace.getVehicleRegistrationNumber()))
                .findAny();
    }

    @Override
    public Optional<SpaceFinished> updateToFinish(final String registrationNo, final Date finishDate) {
        SpaceActive psa = mapParkingSpacesActive.get(registrationNo);

        if (Objects.nonNull(psa)) {
            SpaceFinished psf = new SpaceFinished(psa.getVehicleRegistrationNumber(), psa.getDriverType(), psa.getBeginDate(), finishDate);

            mapParkingSpacesActive.remove(registrationNo);
            mapParkingSpacesFinished.put(psf.getUuid(), psf);
            return Optional.of(psf);
        }
        return Optional.empty();

    }

    @Override
    public Collection<SpaceFinished> findAllFinished(final Date date) {
        final Date end = createEndDate(date);
        return mapParkingSpacesFinished.values()
                .stream()
                .filter(ps -> date.before(ps.getFinishDate()) && end.after(ps.getBeginDate()))
                .collect(Collectors.toList());
    }

    private Date createEndDate(final Date d) {
        final LocalDateTime endDateTime = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(endDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
    }

    public void removeAll() {
        mapParkingSpacesFinished.clear();
    }
}
