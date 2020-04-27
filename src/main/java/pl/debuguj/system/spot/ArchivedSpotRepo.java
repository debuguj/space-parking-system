package pl.debuguj.system.spot;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArchivedSpotRepo {

    Optional<ArchivedSpot> save(final ArchivedSpot archivedSpot);

    List<ArchivedSpot> getAllByDay(final Date date);

}
