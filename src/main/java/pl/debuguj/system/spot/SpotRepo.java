package pl.debuguj.system.spot;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by GB on 07.03.2020.
 */

public interface SpotRepo {

    Optional<Spot> save(final Spot parkingSpaceActive);

    Optional<Spot> findByPlate(final String vehiclePlate);

    Optional<Spot> delete(final String plate);
}
