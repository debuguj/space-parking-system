package pl.debuguj.parkingspacessystem.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.service.enums.Currency;
import pl.debuguj.parkingspacessystem.service.enums.DriverType;
import pl.debuguj.parkingspacessystem.service.impl.PaymentServiceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by GB on 12.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:constants.properties")
public class PaymentServiceImplTest {

    private final PaymentServiceImpl paymentService = new PaymentServiceImpl();
    @Value("${format.time}")
    private String timeFormat;

    private SimpleDateFormat timeDateFormat;

    private String registrationNo = "12345";

    @Before
    public void setup() throws Exception {
        timeDateFormat = new SimpleDateFormat(timeFormat);
        paymentService.setCurrency(Currency.PLN);
    }

    @Test
    public void shouldReturnCorrectCurrency() throws Exception {
        final Currency c = Currency.USD;
        paymentService.setCurrency(Currency.USD);

        assertEquals( c, paymentService.getCurrency());
    }

    @Test
    public void shouldReturnCorrectFeeForRegularDriver() throws Exception {

        String[] beginDates = {"2017-10-12 11:15:48","2017-10-12 11:15:48",
                               "2017-10-12 11:15:48","2017-10-12 11:15:48",
                               "2017-10-12 00:15:48","2017-10-12 11:15:48"
                                };
        String[] endDates = {"2017-10-12 11:35:12", "2017-10-12 12:35:12",
                             "2017-10-12 13:35:12", "2017-10-12 16:35:12",
                             "2017-10-12 15:35:12", "2017-10-13 11:14:12"
                            };
        BigDecimal[] returnedFee = {new BigDecimal("1.0"), new BigDecimal("3.0"),
                                    new BigDecimal("7.0"), new BigDecimal("63.0"),
                                    new BigDecimal("65535.0"), new BigDecimal("16777215.0")
                                };

        for(int i=0; i<beginDates.length; i++)
        {
            Date start = timeDateFormat.parse(beginDates[i]);
            Date stop = timeDateFormat.parse(endDates[i]);
            ParkingSpace ps = new ParkingSpace(registrationNo,DriverType.REGULAR, start, stop);

            BigDecimal fee = paymentService.getFee(ps);

            assertEquals(returnedFee[i], fee);
        }
    }

    @Test
    public void shouldReturnCorrectFeeForVipDriver() throws Exception {

        String[] beginDates = {"2017-10-12 11:15:48","2017-10-12 11:15:48",
                "2017-10-12 11:15:48","2017-10-12 11:15:48",
                "2017-10-12 00:15:48","2017-10-12 11:15:48"
        };
        String[] endDates = {"2017-10-12 11:35:12", "2017-10-12 12:35:12",
                "2017-10-12 13:35:12", "2017-10-12 16:35:12",
                "2017-10-12 15:35:12", "2017-10-13 11:14:12"
        };
        BigDecimal[] returnedFee = {new BigDecimal("0.0"), new BigDecimal("2.0"),
                new BigDecimal("5.0"), new BigDecimal("26.4"),
                new BigDecimal("1747.6"), new BigDecimal("44887.0")
        };

        for(int i=0; i<beginDates .length; i++)
        {
            Date start = timeDateFormat.parse(beginDates[i]);
            Date stop = timeDateFormat.parse(endDates[i]);
            ParkingSpace ps = new ParkingSpace(registrationNo, DriverType.VIP,start, stop);

            BigDecimal fee = paymentService.getFee(ps);

            assertEquals(returnedFee[i], fee);
        }
    }

}