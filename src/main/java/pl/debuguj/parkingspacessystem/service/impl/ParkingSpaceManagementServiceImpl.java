package pl.debuguj.parkingspacessystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.repository.impl.ParkingSpaceRepoStub;
import pl.debuguj.parkingspacessystem.service.ParkingSpaceManagementService;
import pl.debuguj.parkingspacessystem.service.PaymentService;
import pl.debuguj.parkingspacessystem.service.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.service.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.service.exceptions.VehicleIsAlreadyActiveInSystemException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * Created by grzesiek on 09.10.17.
 */
@Service
public class ParkingSpaceManagementServiceImpl implements ParkingSpaceManagementService {

    private final ParkingSpaceRepoStub parkingSpaceRepo;

    private final PaymentService paymentService;

    public ParkingSpaceManagementServiceImpl(ParkingSpaceRepoStub parkingSpaceRepo, PaymentService paymentService) {
        this.parkingSpaceRepo = parkingSpaceRepo;
        this.paymentService = paymentService;
    }

    @Override
    public void reserveParkingSpace(final ParkingSpace ps) throws VehicleIsAlreadyActiveInSystemException {
        if(!parkingSpaceRepo.find(ps.getCarRegistrationNumber()).isPresent()){
            parkingSpaceRepo.save(ps);
        } else {
            throw new VehicleIsAlreadyActiveInSystemException();
        }
    }

    @Override
    public boolean checkVehicle(final String registrationNumber, final Date currentDate) {
         return parkingSpaceRepo.find(registrationNumber).isPresent();
    }

    @Override
    public BigDecimal stopParkingMeter(final String registrationNumber, final Date finishDate)
            throws IncorrectEndDateException, ParkingSpaceNotFoundException {

        Optional<ParkingSpace> ps = parkingSpaceRepo.find(registrationNumber);
        if(!validateFinishDate(ps.get(), finishDate)){
            throw new IncorrectEndDateException();
        };

        if(ps.isPresent()){
            parkingSpaceRepo.remove(ps.get().getUuid());
            ParkingSpace updatedPs = new ParkingSpace(ps.get().getCarRegistrationNumber(), ps.get().getDriverType(),ps.get().getBeginDate(), finishDate);
            parkingSpaceRepo.save(updatedPs);
            return paymentService.getFee(updatedPs);
        } else {
            throw new ParkingSpaceNotFoundException();
        }
    }

    private boolean validateFinishDate(final ParkingSpace ps, final Date finishDate) {
        return finishDate.after(ps.getBeginDate());
    }

    @Override
    public BigDecimal getIncomePerDay(final Date timestamp) {
        return parkingSpaceRepo.findAll(timestamp)
                .map(ps -> paymentService.getFee(ps))
                .reduce(BigDecimal.ZERO.setScale(1), (a,b) -> a.add(b).setScale(1));
    }
}
