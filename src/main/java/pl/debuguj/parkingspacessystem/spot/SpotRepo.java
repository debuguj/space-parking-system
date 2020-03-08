package pl.debuguj.parkingspacessystem.spot;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by GB on 07.03.2020.
 */
@Repository
public interface SpotRepo {

    Optional<Boolean> save(final Spot parkingSpaceActive);

    Optional<Spot> updateFinishDate(final String registrationNo, final Date finishDate);

    Optional<Spot> findActive(final String registrationNo);

    Stream<Spot> findAllFinished(final Date timestamp);
}
