package pl.debuguj.parkingspacessystem.spot;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by GB on 05.03.2020.
 */
@Repository
public class SpotRepoStub implements SpotRepo {

    private int maxActiveSpaces = 1_000;

    private static Map<String, Spot> mapParkingSpacesActive = new ConcurrentHashMap<>();
    private static Map<UUID, Spot> mapParkingSpacesFinished = new ConcurrentHashMap<>();

    @Override
    public Optional<Boolean> save(final Spot ps) {
        if (Objects.nonNull(ps)) {
            if (mapParkingSpacesActive.size() < maxActiveSpaces) {
                mapParkingSpacesActive.put(ps.getVehicleRegistrationNumber(), ps);
                return Optional.of(Boolean.TRUE);
            }
            return Optional.of(Boolean.FALSE);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Spot> find(final String registrationNo) {
        return mapParkingSpacesActive.values()
                .stream()
                .filter(parkingSpace -> registrationNo.equals(parkingSpace.getVehicleRegistrationNumber()))
                .findAny();
    }

    @Override
    public Optional<Spot> updateFinishDate(final String registrationNo, final Date finishDate) {
        Spot psa = mapParkingSpacesActive.get(registrationNo);

        if (Objects.nonNull(psa)) {
            Spot psf = new Spot(psa.getVehicleRegistrationNumber(), psa.getDriverType(), psa.getBeginDate(), finishDate);

            mapParkingSpacesActive.remove(registrationNo);
            mapParkingSpacesFinished.put(psf.getUuid(), psf);
            return Optional.of(psf);
        }
        return Optional.empty();

    }

    @Override
    public Collection<Spot> findAllFinished(final Date date) {
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
}
