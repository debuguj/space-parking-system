package pl.debuguj.system.spot;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by GB on 05.03.2020.
 */
@Repository
public class SpotRepoStubImpl implements SpotRepo {

    //TODO: add limit for spot capacity
    private static Map<String, Spot> mapParkingSpots = new ConcurrentHashMap<>();

    @Override
    public Optional<Spot> save(final Spot spot) {

        Optional<Spot> found = mapParkingSpots.values()
                .stream()
                .filter(s -> spot.getVehiclePlate().equals(s.getVehiclePlate()))
                .findAny();

        if (found.isPresent() || null == spot) {
            return Optional.empty();
        } else {
            mapParkingSpots.put(spot.getVehiclePlate(), spot);
            return Optional.of(spot);
        }
    }

    @Override
    public Optional<Spot> findByPlate(final String vehiclePlate) {
        return mapParkingSpots.values()
                .stream()
                .filter(s -> vehiclePlate.equals(s.getVehiclePlate()))
                .findAny();
    }

    @Override
    public Optional<Spot> delete(String plate) {
        return Optional.ofNullable(mapParkingSpots.remove(plate));
    }

//    @Override
//    public Optional<Spot> updateFinishDateByPlate(final String plate, final Date date) {
//
//        Optional<Spot> os = mapParkingSpots.values()
//                .stream()
//                .filter(s -> plate.equals(s.getVehicleRegistrationNumber()))
//                .filter(s -> Objects.isNull(s.getFinishDate()))
//                .filter(s -> s.getBeginDate().before(date))
//                .findAny();
//
//        if (os.isPresent()) {
//            Spot newSpot = new Spot(os.get().getVehicleRegistrationNumber(), os.get().getDriverType(), os.get().getBeginDate(), date);
//            mapParkingSpots.remove(os.get().getUuid());
//            mapParkingSpots.put(newSpot.getUuid(), newSpot);
//            return Optional.of(newSpot);
//        }
//        return Optional.empty();
//
//    }

//    @Override
//    public Stream<Spot> findAllFinished(final Date date) {
//        final Date end = createEndDate(date);
//        return mapParkingSpots.values()
//                .stream()
//
//                .filter(s -> Objects.nonNull(s.getFinishDate()));
//    }


    public void clearRepo() {
        this.mapParkingSpots.clear();
    }
}
