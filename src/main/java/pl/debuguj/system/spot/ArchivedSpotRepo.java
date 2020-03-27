package pl.debuguj.system.spot;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public interface ArchivedSpotRepo {

    Optional<ArchivedSpot> save(final ArchivedSpot archivedSpot);

    Stream<ArchivedSpot> getAllByDay(final Date date);

}
