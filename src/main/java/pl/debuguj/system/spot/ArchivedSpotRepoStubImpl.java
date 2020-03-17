package pl.debuguj.system.spot;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ArchivedSpotRepoStubImpl implements ArchivedSpotRepo {
    private static Map<UUID, ArchivedSpot> mapParkingSpots = new ConcurrentHashMap<>();

    @Override
    public Optional<ArchivedSpot> save(final ArchivedSpot archivedSpot) {
        return Optional.ofNullable(mapParkingSpots.put(archivedSpot.getUuid(), archivedSpot));
    }

    @Override
    public Stream<ArchivedSpot> getAllByDay(final Date date) {
        final Date end = createEndDate(date);
        return mapParkingSpots.values()
                .stream()
                .filter(ps -> date.before(ps.getFinishDate()) && end.after(ps.getBeginDate()));
    }

    private Date createEndDate(final Date d) {
        final LocalDateTime endDateTime = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(endDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
    }
}
