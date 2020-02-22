package pl.debuguj.parkingspacessystem.repository.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.repository.ParkingSpaceRepo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by GB on 07.10.17.
 */
@Repository
public class ParkingSpaceRepoStub implements ParkingSpaceRepo {

    private static ConcurrentHashMap<UUID, ParkingSpace> mapParkingSpaces = new ConcurrentHashMap<UUID, ParkingSpace>();

    @Override
    public void save(final ParkingSpace ps) {
         mapParkingSpaces.put(ps.getUuid(), ps);
    }

    @Override
    public Optional<ParkingSpace> find(final String registrationNo) {
        return mapParkingSpaces.values()
                .stream()
                .filter(parkingSpace -> registrationNo.equals(parkingSpace.getCarRegistrationNumber()))
                .filter(parkingSpace -> Objects.nonNull(parkingSpace.getEndDate()))
                .findFirst();
    }

    @Override
    public void remove(final UUID uuid) {
        mapParkingSpaces.remove(uuid);
    }

    @Override
    public Stream<ParkingSpace> findAll(final Date date) {
        final Date end = createEndDate(date);
        return mapParkingSpaces.values()
                .stream()
                .filter(ps -> Objects.nonNull(ps.getEndDate()))
                .filter(ps -> date.before(ps.getEndDate()) && end.after(ps.getBeginDate()))
                .collect(Collectors.toList())
                        .stream();

    }

    private Date createEndDate(final Date d){
        final LocalDateTime endDateTime = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(endDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
    }

//    private boolean isVehicleActive(ParkingSpace ps) {
//        return mapParkingSpaces
//                .values()
//                .stream()
//                .anyMatch(parkingSpace -> (checkCondition(parkingSpace, ps.getCarRegistrationNumber())));
//    }
//
//    private boolean checkCondition(final ParkingSpace ps, final String registrationNo){
//        return registrationNo.equals(ps.getCarRegistrationNumber())&& Objects.nonNull(ps.getEndDate());
//    }

//    @Override
//    public void create(final ParkingSpace parkingSpace) throws CarRegisteredInSystemException {
//
//        Optional<ParkingSpace> ps =
//                findByRegistrationNo(parkingSpace.getCarRegistrationNumber());
//        if(!ps.isPresent())
//            listParkingSpaces.add(parkingSpace);
//        else
//            throw new CarRegisteredInSystemException("Car is registered into system");
//    }
//
//    @Override
//    public Optional<ParkingSpace> findByRegistrationNo(final String registrationNo)  {
//        return listParkingSpaces
//                .stream()
//                .filter(ps -> Objects.equals(registrationNo, ps.getCarRegistrationNumber()))
//                .findFirst();
//    }
//
//    @Override
//    public List<ParkingSpace> getAll() {
//        return Collections.unmodifiableList(listParkingSpaces);
//    }
//
//    @Override
//    public Optional<ParkingSpace> updateEndTime(
//            final String registrationNumber,
//            final Date timestamp) throws IncorrectEndDateException, ParkingSpaceNotFoundException {
//
//        Optional<ParkingSpace> ps = findByRegistrationNo(registrationNumber);
//
//        if(ps.isPresent())
//        {
//            if (!timestamp.after(ps.get().getBeginTime())) {
//                throw new IncorrectEndDateException("Incorrect update time");
//            }
//
//            listParkingSpaces
//                    .forEach(s -> {
//                        if (Objects.equals(registrationNumber, s.getCarRegistrationNumber())) {
//                            s.setEndTime(timestamp);
//                        }
//                    });
//
//            return findByRegistrationNo(registrationNumber);
//        }
//        throw new ParkingSpaceNotFoundException("Parking space not found");
//    }
//
//    @Override
//    public void removeAll() {
//        listParkingSpaces.clear();
//    }
//
//    @Override
//    public List<ParkingSpace> findByDate(final Date timestamp) {
//        final Date end = createEndDate(timestamp);
//        return Collections.unmodifiableList(
//                listParkingSpaces
//                .stream()
//                .filter(ps -> timestamp.before(ps.getBeginTime())
//                        && end.after(ps.getBeginTime()))
//                .collect(Collectors.toList()));
//
//    }
//
//    private Date createEndDate(final Date d){
//        return (new DateTime(d).plusDays(1)).toDate();
//    }

//    @Override
//    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//        try {
//            listParkingSpaces.clear();
//
//            listParkingSpaces.create(new ParkingSpace("11111",
//                    timeDateFormat.parse("2017-10-13 10:25:48"),
//                    timeDateFormat.parse("2017-10-13 10:35:12")));
//            listParkingSpaces.create(new ParkingSpace("22222",
//                    timeDateFormat.parse("2017-10-13 12:25:48"),
//                    timeDateFormat.parse("2017-10-13 13:35:12")));
//            listParkingSpaces.create(new ParkingSpace("33333",
//                    timeDateFormat.parse("2017-10-13 15:25:48"),
//                    timeDateFormat.parse("2017-10-13 16:35:12")));
//            listParkingSpaces.create(new ParkingSpace("44444",
//                    timeDateFormat.parse("2017-10-14 20:25:48"),
//                    timeDateFormat.parse("2017-10-14 21:35:12")));
//            listParkingSpaces.create(new ParkingSpace("55555",
//                    timeDateFormat.parse("2017-10-14 11:15:48"),
//                    timeDateFormat.parse("2017-10-14 12:35:12")));
//        } catch (ParseException e){
//            e.printStackTrace();
//        } catch (IncorrectEndDateException e) {
//            e.printStackTrace();
//        }
//    }
}
