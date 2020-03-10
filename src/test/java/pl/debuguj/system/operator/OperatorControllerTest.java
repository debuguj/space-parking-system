package pl.debuguj.system.operator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.debuguj.system.ParkingSpacesSystemApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingSpacesSystemApplication.class)
@WebAppConfiguration
public class OperatorControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Value("${uri.check.vehicle}")
    private String uriCheckVehicle;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

//    @Test
//    public void shouldReturnCorrectHttpStatusAndPayload() throws Exception {
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(ParkingSpaceController.URI_START_METER)
//                .append("/11111")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-29 10:10:10")
//                .append("&stopTime=2017-10-29 23:20:59")
//                .toString();
//
//        //WHEN
//        mockMvc.perform(post(givenUrl))
//
//        //THEN
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(content().string("774.5"))
//                .andDo(print())
//                .andReturn();
//
//    }


//    @Test
//    public void shouldReturnBadRequestBecauseOfIncorrectEndTime() throws Exception {
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(ParkingSpaceController.URI_START_METER)
//                .append("/11111")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-13 23:10:12")
//                .append("&stopTime=2017-10-13 22:10:12")
//                .toString();
//        //WHEN
//        mockMvc.perform(post(givenUrl))
//        //THEN
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(""))
//                .andDo(print())
//                .andReturn();
//    }


    @Test
    public void shouldReturnThatVehicleIsNotRegisteredInSystem() throws Exception {
        String givenUrl = new StringBuilder()
                .append(uriCheckVehicle)
                .append("/22223")
                .append("?currentDate=2017-10-13 10:28:48")
                .toString();
        //WHEN
        mockMvc.perform(get(givenUrl))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestBecauseOfBadDateFormat() throws Exception {
        String givenUrl = new StringBuilder()
                .append(uriCheckVehicle)
                .append("/22222")
                .append("?currentDate=2017-10-13 10:28")
                .toString();
        //WHEN
        mockMvc.perform(get(givenUrl))
                //THEN
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void shouldReturnNotFoundBecauseOfBadRegistrationNumberFormat() throws Exception {
        String givenUrl = new StringBuilder()
                .append(uriCheckVehicle)
                .append("/2222")
                .append("?currentDate=2017-10-13 10:28:24")
                .toString();
        //WHEN
        mockMvc.perform(get(givenUrl))
                //THEN
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }


//    @Test
//    public void shouldReturnCorrectIncomePerDay() throws Exception {
//
//        //GIVEN
//        String givenUrlSetData1 = new StringBuilder()
//                .append(ParkingSpaceController.URI_START_METER)
//                .append("/44444")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-19 10:10:12")
//                .append("&stopTime=2017-10-19 11:15:12")
//                .toString();
//        mockMvc.perform(post(givenUrlSetData1));
//
//        String givenUrlSetData2 = new StringBuilder()
//                .append(ParkingSpaceController.URI_START_METER)
//                .append("/44445")
//                .append("?driverType=REGULAR")
//                .append("&startTime=2017-10-19 10:10:12")
//                .append("&stopTime=2017-10-19 11:15:12")
//                .toString();
//        mockMvc.perform(post(givenUrlSetData2));
//
//        String getVehicleStatus = new StringBuilder()
//                .append(ParkingSpaceController.URI_CHECK_INCOME_PER_DAY)
//                .append("?date=2017-10-19")
//                .toString();
//        //WHEN
//        mockMvc.perform(get(getVehicleStatus))
//        //THEN
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(content().string("5.0"))
//                .andDo(print())
//                .andReturn();
//    }
}
