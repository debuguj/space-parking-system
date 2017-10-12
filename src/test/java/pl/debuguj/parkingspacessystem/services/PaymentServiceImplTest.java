package pl.debuguj.parkingspacessystem.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.enums.Currency;
import pl.debuguj.parkingspacessystem.enums.DriverType;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

/**
 * Created by grzesiek on 12.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat timeDateFormat;

    @Before
    public void setup() throws ParseException {
        timeDateFormat = new SimpleDateFormat(TIME_PATTERN);
        paymentService.setCurrency(Currency.PLN);
    }

    @Test
    public void testSettingAndGettingCurrency() throws Exception {
        Currency c = Currency.USD;
        paymentService.setCurrency(c);

        assertEquals("Should have the same currency", c, paymentService.getCurrency());
    }

    @Test
    public void testCorrectFee() throws Exception {

        //TODO: more test data
        Date begin = timeDateFormat.parse("2017-10-14 10:15:48");
        Date end = timeDateFormat.parse("2017-10-14 11:35:12");
        ParkingSpace ps = new ParkingSpace("12414", begin, end);


        BigDecimal fee = new BigDecimal("3.0");
        BigDecimal returnValue = paymentService.getFee(ps);
        assertEquals("Should return the same fee", fee, returnValue);

    }

}