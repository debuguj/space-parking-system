package pl.debuguj.parkingspacessystem.spaces;

import org.junit.jupiter.api.Test;
import pl.debuguj.parkingspacessystem.spaces.enums.Currency;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PaymentCalculatorTest {

    private final PaymentCalculator paymentCalculator = new PaymentCalculator();

    private final SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final String registrationNo = "WZE12345";

    @Test
    public void shouldReturnCorrectCurrency() {
        final Currency c = Currency.USD;
        paymentCalculator.setCurrency(Currency.USD);

        assertEquals(c, paymentCalculator.getCurrency());
    }

    @Test
    public void shouldReturnIncorrectCurrency() {
        final Currency c = Currency.USD;
        paymentCalculator.setCurrency(Currency.PLN);

        assertNotEquals(c, paymentCalculator.getCurrency());
    }

    @Test
    public void shouldReturnEmptyOptional() {
        assertEquals(Optional.empty(), paymentCalculator.getFee(null));
    }

    @Test
    public void shouldReturnCorrectFeeForRegularDriver() throws Exception {
        paymentCalculator.setCurrency(Currency.PLN);
        String[] startDates = {"2017-10-12T11:15:48", "2017-10-12T11:15:48",
                "2017-10-12T11:15:48", "2017-10-12T11:15:48",
                "2017-10-12T00:15:48", "2017-10-12T11:15:48"
        };
        String[] finishDates = {"2017-10-12T11:35:12", "2017-10-12T12:35:12",
                "2017-10-12T13:35:12", "2017-10-12T16:35:12",
                "2017-10-12T15:35:12", "2017-10-13T11:14:12"
        };
        BigDecimal[] returnedFee = {new BigDecimal("1.0"), new BigDecimal("3.0"),
                new BigDecimal("7.0"), new BigDecimal("63.0"),
                new BigDecimal("65535.0"), new BigDecimal("16777215.0")
        };

        for (int i = 0; i < startDates.length; i++) {
            Date start = timeDateFormat.parse(startDates[i]);
            Date stop = timeDateFormat.parse(finishDates[i]);
            SpaceFinished ps = new SpaceFinished(registrationNo, DriverType.REGULAR, start, stop);

            BigDecimal fee = paymentCalculator.getFee(ps).get();

            assertEquals(returnedFee[i], fee);
        }
    }

    @Test
    public void shouldReturnCorrectFeeForVipDriver() throws Exception {
        paymentCalculator.setCurrency(Currency.PLN);
        String[] beginDates = {"2017-10-12T11:15:48", "2017-10-12T11:15:48",
                "2017-10-12T11:15:48", "2017-10-12T11:15:48",
                "2017-10-12T00:15:48", "2017-10-12T11:15:48"
        };
        String[] endDates = {"2017-10-12T11:35:12", "2017-10-12T12:35:12",
                "2017-10-12T13:35:12", "2017-10-12T16:35:12",
                "2017-10-12T15:35:12", "2017-10-13T11:14:12"
        };
        BigDecimal[] returnedFee = {new BigDecimal("0.0"), new BigDecimal("2.0"),
                new BigDecimal("5.0"), new BigDecimal("26.4"),
                new BigDecimal("1747.6"), new BigDecimal("44887.0")
        };

        for (int i = 0; i < beginDates.length; i++) {
            Date start = timeDateFormat.parse(beginDates[i]);
            Date stop = timeDateFormat.parse(endDates[i]);
            SpaceFinished ps = new SpaceFinished(registrationNo, DriverType.VIP, start, stop);

            BigDecimal fee = paymentCalculator.getFee(ps).get();

            assertEquals(returnedFee[i], fee);
        }
    }
}
