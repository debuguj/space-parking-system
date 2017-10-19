package pl.debuguj.parkingspacessystem.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.debuguj.parkingspacessystem.config.Constants;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.enums.Currency;
import pl.debuguj.parkingspacessystem.enums.DriverType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by grzesiek on 12.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    private Constants constants;

    private static SimpleDateFormat timeDateFormat;

    private String registrationNo;

    @Before
    public void setup() throws Exception {
        timeDateFormat = new SimpleDateFormat(constants.getTimeFormat());

        paymentService.setCurrency(Currency.PLN);

        registrationNo = "12345";
    }

    @Test
    public void shouldReturnCorrectCurrency() throws Exception {
        Currency c = Currency.USD;
        paymentService.setCurrency(c);

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
            ParkingSpace ps = new ParkingSpace(registrationNo, start, stop);

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
            ParkingSpace ps = new ParkingSpace(registrationNo, start, stop);
            ps.setDriverType(DriverType.VIP);

            BigDecimal fee = paymentService.getFee(ps);

            assertEquals(returnedFee[i], fee);
        }
    }

}