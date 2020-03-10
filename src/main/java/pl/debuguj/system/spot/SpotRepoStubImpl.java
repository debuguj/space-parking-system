package pl.debuguj.system.spot;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by GB on 05.03.2020.
 */
@Repository
public class SpotRepoStubImpl implements SpotRepo {

    //TODO: add limit for spot capacity
    private static Map<UUID, Spot> mapParkingSpots = new ConcurrentHashMap<>();

    @Override
    public Optional<Boolean> save(final Spot spot) {
        if (Objects.nonNull(spot)) {
            Optional<Spot> found = mapParkingSpots.values()
                    .stream()
                    .filter(s -> Objects.isNull(s.getFinishDate()))
                    .filter(s -> spot.getVehicleRegistrationNumber().equals(s.getVehicleRegistrationNumber()))
                    .findAny();

            if (found.isPresent()) {
                return Optional.of(Boolean.FALSE);
            } else {
                mapParkingSpots.put(spot.getUuid(), spot);
                return Optional.of(Boolean.TRUE);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Spot> findActive(final String registrationNumber) {
        return mapParkingSpots.values()
                .stream()
                .filter(s -> registrationNumber.equals(s.getVehicleRegistrationNumber()))
                .filter(s -> Objects.isNull(s.getFinishDate()))
                .findAny();
    }

    @Override
    public Optional<Spot> updateFinishDate(final String registrationNumber, final Date finishDate) {

        Optional<Spot> os = mapParkingSpots.values()
                .stream()
                .filter(s -> registrationNumber.equals(s.getVehicleRegistrationNumber()))
                .filter(s -> Objects.isNull(s.getFinishDate()))
                .findAny();

        if (os.isPresent()) {
            Spot newSpot = new Spot(os.get().getVehicleRegistrationNumber(), os.get().getDriverType(), os.get().getBeginDate(), finishDate);
            mapParkingSpots.remove(os.get().getUuid());
            mapParkingSpots.put(newSpot.getUuid(), newSpot);
            return Optional.of(newSpot);
        }
        return Optional.empty();

    }

    @Override
    public Stream<Spot> findAllFinished(final Date date) {
        final Date end = createEndDate(date);
        return mapParkingSpots.values()
                .stream()
                .filter(ps -> date.before(ps.getFinishDate()) && end.after(ps.getBeginDate()))
                .filter(s -> Objects.nonNull(s.getFinishDate()));
    }

    private Date createEndDate(final Date d) {
        final LocalDateTime endDateTime = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(endDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
    }

    public void clearRepo() {
        this.mapParkingSpots.clear();
    }
}
