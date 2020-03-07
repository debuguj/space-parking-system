package pl.debuguj.parkingspacessystem.spaces;

import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.spaces.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.ParkingSpaceNotFoundException;

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

    private final SpaceRepo parkingSpaceRepo;
    private final PaymentCalculator paymentCalculator = new PaymentCalculator();

    public SpaceManagementServiceImpl(SpaceRepo parkingSpaceRepo) {
        this.parkingSpaceRepo = parkingSpaceRepo;
    }

    @Override
    public boolean reserveParkingSpace(final SpaceActive ps) {
        Optional<SpaceActive> osa = parkingSpaceRepo.findActive(ps.getVehicleRegistrationNumber());
        if (!osa.isPresent()) {
            return parkingSpaceRepo.save(ps).get();
        }
        return false;
    }

    @Override
    public boolean checkVehicle(final String registrationNumber, final Date currentDate) {
        return parkingSpaceRepo.findActive(registrationNumber).isPresent();
    }

    @Override
    public BigDecimal stopParkingMeter(final String registrationNumber, final Date finishDate)
            throws IncorrectEndDateException, ParkingSpaceNotFoundException {


        Optional<SpaceFinished> osf = parkingSpaceRepo.updateToFinish(registrationNumber, finishDate);
        if (osf.isPresent()) {
            if (!validateFinishDate(osf.get())) {
                throw new IncorrectEndDateException();
            }
            return paymentCalculator.getFee(osf.get()).get();
        } else {
            throw new ParkingSpaceNotFoundException();
        }
    }

    private boolean validateFinishDate(final SpaceFinished sf) {
        return sf.getBeginDate().before(sf.getFinishDate());
    }

    @Override
    public BigDecimal getIncomePerDay(final Date timestamp) {
        Collection<SpaceFinished> c = parkingSpaceRepo.findAllFinished(timestamp);
        return c
                .stream()
                .map(paymentCalculator::getFee)
                .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
                .reduce(BigDecimal.ZERO.setScale(1, BigDecimal.ROUND_CEILING), (a, b) -> a.add(b).setScale(1, BigDecimal.ROUND_CEILING));
    }
}
