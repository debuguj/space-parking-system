package pl.debuguj.system.spot;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by GB on 05.03.2020.
 */
@Repository
public class SpotRepoStubImpl implements SpotRepo {

    //TODO: add limit for parking capacity
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

    public void clearRepo() {
        this.mapParkingSpots.clear();
    }
}
