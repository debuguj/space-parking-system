package pl.debuguj.system.driver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
//@ActiveProfiles("test")
public class DriverControllerTest {

    @Value("${uri.start.meter}")
    private String uriStartMeter;
    @Value("${uri.stop.meter}")
    private String uriStopMeter;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldReturnCorrectHttpStatusAndPayload() throws Exception {

        //WHEN
        mockMvc.perform(post(uriStartMeter)
                
                .param("vehiclePlate", "WCI12346")
                .param("driverType", "REGULAR")
                .param("beginDate", "2020-12-12T10:10:10")
                .contentType(MediaType.APPLICATION_JSON_UTF8))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("774.5"))
                .andDo(print())
                .andReturn();

    }
//
//    @Test
//    public void shouldReturnBadRequestBecauseOfBadDayFormat() throws Exception {
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(uriStartMeter)
//                .append("/11111")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-13 10:10:12")
//                .append("&stopTime=2017-10-13 11")
//                .toString();
//        //WHEN
//        mockMvc.perform(post(givenUrl))
//                //THEN
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(""))
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    public void shouldReturnBadRequestBecauseOfIncorrectDriverType() throws Exception {
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(uriStartMeter)
//                .append("/11111")
//                .append("?driverType=VI")
//                .append("&startTime=2017-10-13 10:10:12")
//                .append("&stopTime=2017-10-13 23:10:12")
//                .toString();
//        //WHEN
//        mockMvc.perform(post(givenUrl))
//                //THEN
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(""))
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    public void shouldReturnNotFoundBecauseOfIncorrectRegistrationNumber() throws Exception {
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(uriStartMeter)
//                .append("/1111")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-13 10:10:12")
//                .append("&stopTime=2017-10-13 23:10:12")
//                .toString();
//        //WHEN
//        mockMvc.perform(post(givenUrl))
//                //THEN
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(""))
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    public void shouldReturnBadRequestBecauseOfIncorrectEndTime() throws Exception {
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(uriStartMeter)
//                .append("/11111")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-13 23:10:12")
//                .append("&stopTime=2017-10-13 22:10:12")
//                .toString();
//        //WHEN
//        mockMvc.perform(post(givenUrl))
//                //THEN
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(""))
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    public void shouldReturnNewFeeValue() throws Exception {
//
//        //GIVEN
//        String givenUrlSetData = new StringBuilder()
//                .append(uriStartMeter)
//                .append("/33333")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-11 10:10:12")
//                .append("&stopTime=2017-10-11 12:15:12")
//                .toString();
//
//        mockMvc.perform(post(givenUrlSetData));
//
//        String givenUrl = new StringBuilder()
//                .append(uriStopMeter)
//                .append("/33333")
//                .append("?timeStamp=2017-10-11 10:28:48")
//                .toString();
//        //WHEN
//        mockMvc.perform(put(givenUrl))
//                //THEN
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(content().string("0.0"))
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    public void shouldReturnBadRequestBecauseOfIncorrectNewTime() throws Exception {
//
//        //GIVEN
//        String givenUrlSetData = new StringBuilder()
//                .append(uriStartMeter)
//                .append("/33333")
//                .append("?driverType=VIP")
//                .append("&startTime=2017-10-10 10:10:12")
//                .append("&stopTime=2017-10-10 12:15:12")
//                .toString();
//
//        mockMvc.perform(post(givenUrlSetData));
//
//        String givenUrl = new StringBuilder()
//                .append(uriStopMeter)
//                .append("/33333")
//                .append("?timeStamp=2017-10-10 10:05:48")
//                .toString();
//        //WHEN
//        mockMvc.perform(put(givenUrl))
//                //THEN
//                .andExpect(status().isBadRequest())
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    public void shouldReturnNotFoundBecauseOfBadRegistrationNumberFormatWhenEndDateUpdate() throws Exception {
//
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(uriStopMeter)
//                .append("/3333")
//                .append("?timeStamp=2017-10-13 10:28:48")
//                .toString();
//        //WHEN
//        mockMvc.perform(put(givenUrl))
//                //THEN
//                .andExpect(status().isNotFound())
//                .andDo(print())
//                .andReturn();
//    }
//
//    @Test
//    public void shouldReturnBadRequestBecauseOfBadDateFormatWhenEndDateUpdate() throws Exception {
//
//        //GIVEN
//        String givenUrl = new StringBuilder()
//                .append(uriStopMeter)
//                .append("/33333")
//                .append("?timeStamp=2017-10-13 10:28")
//                .toString();
//        //WHEN
//        mockMvc.perform(put(givenUrl))
//                //THEN
//                .andExpect(status().isBadRequest())
//                .andDo(print())
//                .andReturn();
//    }
}
