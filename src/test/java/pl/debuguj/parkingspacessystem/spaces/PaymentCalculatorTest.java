package pl.debuguj.parkingspacessystem.spaces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.debuguj.parkingspacessystem.spaces.enums.Currency;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PaymentCalculatorTest {

    private final PaymentCalculator paymentCalculator = new PaymentCalculator();

    private SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String registrationNo = "12345";

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldReturnCorrectCurrency() throws Exception {
        final Currency c = Currency.USD;
        paymentCalculator.setCurrency(Currency.USD);

        assertEquals(c, paymentCalculator.getCurrency());
    }

    @Test
    public void shouldReturnCorrectFeeForRegularDriver() throws Exception {
        paymentCalculator.setCurrency(Currency.PLN);
        String[] beginDates = {"2017-10-12 11:15:48", "2017-10-12 11:15:48",
                "2017-10-12 11:15:48", "2017-10-12 11:15:48",
                "2017-10-12 00:15:48", "2017-10-12 11:15:48"
        };
        String[] endDates = {"2017-10-12 11:35:12", "2017-10-12 12:35:12",
                "2017-10-12 13:35:12", "2017-10-12 16:35:12",
                "2017-10-12 15:35:12", "2017-10-13 11:14:12"
        };
        BigDecimal[] returnedFee = {new BigDecimal("1.0"), new BigDecimal("3.0"),
                new BigDecimal("7.0"), new BigDecimal("63.0"),
                new BigDecimal("65535.0"), new BigDecimal("16777215.0")
        };

        for (int i = 0; i < beginDates.length; i++) {
            Date start = timeDateFormat.parse(beginDates[i]);
            Date stop = timeDateFormat.parse(endDates[i]);
            SpaceFinished ps = new SpaceFinished(registrationNo, DriverType.REGULAR, start, stop);

            BigDecimal fee = paymentCalculator.getFee(ps);

            assertEquals(returnedFee[i], fee);
        }
    }

    @Test
    public void shouldReturnCorrectFeeForVipDriver() throws Exception {
        paymentCalculator.setCurrency(Currency.PLN);
        String[] beginDates = {"2017-10-12 11:15:48", "2017-10-12 11:15:48",
                "2017-10-12 11:15:48", "2017-10-12 11:15:48",
                "2017-10-12 00:15:48", "2017-10-12 11:15:48"
        };
        String[] endDates = {"2017-10-12 11:35:12", "2017-10-12 12:35:12",
                "2017-10-12 13:35:12", "2017-10-12 16:35:12",
                "2017-10-12 15:35:12", "2017-10-13 11:14:12"
        };
        BigDecimal[] returnedFee = {new BigDecimal("0.0"), new BigDecimal("2.0"),
                new BigDecimal("5.0"), new BigDecimal("26.4"),
                new BigDecimal("1747.6"), new BigDecimal("44887.0")
        };

        for (int i = 0; i < beginDates.length; i++) {
            Date start = timeDateFormat.parse(beginDates[i]);
            Date stop = timeDateFormat.parse(endDates[i]);
            SpaceFinished ps = new SpaceFinished(registrationNo, DriverType.VIP, start, stop);

            BigDecimal fee = paymentCalculator.getFee(ps);

            assertEquals(returnedFee[i], fee);
        }
    }
}
