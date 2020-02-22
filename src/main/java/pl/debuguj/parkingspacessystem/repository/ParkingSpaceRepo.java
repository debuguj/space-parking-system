package pl.debuguj.parkingspacessystem.repository;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by grzesiek on 07.10.17.
 */
public interface ParkingSpaceRepo {

    void save(final ParkingSpace parkingSpace) ;

    void remove(final UUID uuid) ;

    Optional<ParkingSpace> find(final String registrationNo);

    Stream<ParkingSpace> findAll(final Date timestamp);
}
