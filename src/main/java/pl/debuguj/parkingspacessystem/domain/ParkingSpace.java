package pl.debuguj.parkingspacessystem.domain;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
public class ParkingSpace implements Space {

    public ParkingSpace(String carRegistrationNumber) {
    }

    @Override
    public String getCarRegistrationNumber() {
        return null;
    }

    @Override
    public DriverType getDriverType() {
        return null;
    }

    @Override
    public void setDriverType(DriverType dt) {

    }

    @Override
    public void setBeginTime(String time) {

    }

    @Override
    public Date getBeginTime() {
        return null;
    }

    @Override
    public void setEndTime(String time) {

    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public void setPeriod(String beginTime, String endTime) {

    }



//    private final String carRegistrationNumber;
//    private final DriverType driverType;
//    private final Date beginTime;
//    private Date endTime;
//    private BigDecimal fee;
//
//    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
//    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
//
//    public ParkingSpace(final String registrationNumber,
//                        final DriverType driverType,
//                        final String startTime,
//                        String endTime) throws ParseException, IncorrectEndDateException {
//        this.carRegistrationNumber = registrationNumber;
//        this.driverType = driverType;
//        this.beginTime = simpleDateFormat.parse(startTime);
//        this.endTime = checkEndDate(simpleDateFormat.parse(endTime));
//        this.fee = PaymentManager.getFee(this);
//    }
//
//    private Date checkEndDate(Date d) throws IncorrectEndDateException {
//        if(d.compareTo(this.getBeginTime()) > 0)
//            return d;
//        throw new IncorrectEndDateException();
//    }
//
//    public String getCarRegistrationNumber() {
//        return carRegistrationNumber;
//    }
//
//    public DriverType getDriverType() {
//        return driverType;
//    }
//
//    public Date getBeginTime() {
//        return beginTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) throws ParseException {
//        this.endTime = simpleDateFormat.parse(endTime);
//        fee = PaymentManager.getFee(this);
//    }
//
//    public BigDecimal getFee() {
//        return fee;
//    }



}
