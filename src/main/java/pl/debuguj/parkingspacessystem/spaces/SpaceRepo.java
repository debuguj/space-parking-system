package pl.debuguj.parkingspacessystem.spaces;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Created by GB on 07.10.17.
 */
public interface SpaceRepo {

    boolean save(final SpaceActive parkingSpaceActive);

    Optional<SpaceFinished> updateToFinish(final String registrationNo, final Date finishDate);

    Optional<SpaceActive> findActive(final String registrationNo);

    Collection<SpaceFinished> findAllFinished(final Date timestamp);
}
