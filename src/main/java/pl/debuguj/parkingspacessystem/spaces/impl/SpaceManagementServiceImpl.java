package pl.debuguj.parkingspacessystem.spaces.impl;

import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.spaces.SpaceActive;
import pl.debuguj.parkingspacessystem.spaces.SpaceFinished;
import pl.debuguj.parkingspacessystem.spaces.SpaceRepo;
import pl.debuguj.parkingspacessystem.spaces.SpaceManagementService;
import pl.debuguj.parkingspacessystem.spaces.PaymentCalculator;
import pl.debuguj.parkingspacessystem.spaces.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.VehicleIsAlreadyActiveInSystemException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public boolean reserveParkingSpace(final SpaceActive ps) throws VehicleIsAlreadyActiveInSystemException {
        Optional<SpaceActive> ops = parkingSpaceRepo.findActive(ps.getVehicleRegistrationNumber());
        if (!ops.isPresent()) {
            return parkingSpaceRepo.save(ps);
        } else {
            throw new VehicleIsAlreadyActiveInSystemException();
        }
    }

    @Override
    public boolean checkVehicle(final String registrationNumber, final Date currentDate) {
        return parkingSpaceRepo.findActive(registrationNumber).isPresent();
    }

    @Override
    public BigDecimal stopParkingMeter(final String registrationNumber, final Date finishDate)
            throws IncorrectEndDateException, ParkingSpaceNotFoundException {


        Optional<SpaceFinished> opsf = parkingSpaceRepo.updateToFinish(registrationNumber, finishDate);
        if (opsf.isPresent()) {
            if (!validateFinishDate(opsf.get())) {
                throw new IncorrectEndDateException();
            }
            return paymentCalculator.getFee(opsf.get());
        } else {
            throw new ParkingSpaceNotFoundException();
        }
    }

    private boolean validateFinishDate(final SpaceFinished psf) {
        return psf.getBeginDate().before(psf.getFinishDate());
    }

    @Override
    public BigDecimal getIncomePerDay(final Date timestamp) {
        Collection<SpaceFinished> c = parkingSpaceRepo.findAllFinished(timestamp);
        return c
                .stream()
                .map(paymentCalculator::getFee)
                .reduce(BigDecimal.ZERO.setScale(1, BigDecimal.ROUND_CEILING), (a, b) -> a.add(b).setScale(1, BigDecimal.ROUND_CEILING));
    }
}
