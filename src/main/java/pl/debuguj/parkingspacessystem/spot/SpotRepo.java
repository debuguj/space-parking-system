package pl.debuguj.parkingspacessystem.spot;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Created by GB on 07.03.2020.
 */
public interface SpotRepo {

    Optional<Boolean> save(final Spot parkingSpaceActive);

    Optional<Spot> updateFinishDate(final String registrationNo, final Date finishDate);

    Optional<Spot> find(final String registrationNo);

    Collection<Spot> findAllFinished(final Date timestamp);
}
