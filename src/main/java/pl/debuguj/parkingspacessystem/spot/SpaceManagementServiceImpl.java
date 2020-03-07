package pl.debuguj.parkingspacessystem.spot;

import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.spot.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spot.exceptions.ParkingSpaceNotFoundException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by GB on 09.10.17.
 */
@Service
public class SpaceManagementServiceImpl implements SpaceManagementService {

    private final SpotRepo parkingSpotRepo;

    public SpaceManagementServiceImpl(SpotRepo parkingSpotRepo) {
        this.parkingSpotRepo = parkingSpotRepo;
    }

    @Override
    public boolean reserveParkingSpace(final Spot ps) {
        Optional<Spot> osa = parkingSpotRepo.find(ps.getVehicleRegistrationNumber());
        if (!osa.isPresent()) {
            return parkingSpotRepo.save(ps).get();
        }
        return false;
    }

    @Override
    public boolean checkVehicle(final String registrationNumber, final Date currentDate) {
        return parkingSpotRepo.find(registrationNumber).isPresent();
    }

    @Override
    public BigDecimal stopParkingMeter(final String registrationNumber, final Date finishDate, final Currency currency)
            throws IncorrectEndDateException, ParkingSpaceNotFoundException {


        Optional<Spot> osf = parkingSpotRepo.updateFinishDate(registrationNumber, finishDate);
        if (osf.isPresent()) {
            if (!validateFinishDate(osf.get())) {
                throw new IncorrectEndDateException();
            }
            //TODO update this ugly staff
            return osf.get().getFee(currency).get();
        } else {
            throw new ParkingSpaceNotFoundException();
        }
    }

    private boolean validateFinishDate(final Spot sf) {
        return sf.getBeginDate().before(sf.getFinishDate());
    }

    @Override
    public BigDecimal getIncomePerDay(final Date timestamp, final Currency currency) {
        Collection<Spot> c = parkingSpotRepo.findAllFinished(timestamp);
        return c
                .stream()
                .map(s -> s.getFee(currency))
                .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
                .reduce(BigDecimal.ZERO.setScale(1, BigDecimal.ROUND_CEILING), (a, b) -> a.add(b).setScale(1, BigDecimal.ROUND_CEILING));
    }
}
