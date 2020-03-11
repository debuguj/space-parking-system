package pl.debuguj.system.spot;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by GB on 07.03.2020.
 */
@Repository
public interface SpotRepo {

    Optional<Spot> save(final Spot parkingSpaceActive);

    Optional<Spot> updateFinishDateByPlate(final String plate, final Date date);

    Optional<Spot> findActive(final String registrationNo);

    Stream<Spot> findAllFinished(final Date timestamp);
}
