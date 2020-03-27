package pl.debuguj.system.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.debuguj.system.spot.DriverType;
import pl.debuguj.system.spot.Spot;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DriverControllerTest {

    @Value("${uri.driver.start}")
    private String uriStartMeter;
    @Value("${uri.driver.stop}")
    private String uriStopMeter;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        //MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void shouldReturnCorrectPayloadAndFormatAndValue() throws Exception {
        //GIVEN
        Spot spot = new Spot("WCC12345", DriverType.REGULAR, new Date());
        //WHEN
        mockMvc.perform(post(uriStartMeter)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(spot)))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(objectMapper.writeValueAsString(spot)))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestBecauseOfBadDayFormat() throws Exception {
        //GIVEN
        Spot spot = new Spot("WCC12346", DriverType.REGULAR, new Date());
        mockMvc.perform(post(uriStartMeter)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(spot)));
        //WHEN
        mockMvc.perform(post(uriStartMeter)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(spot)))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
    }

//    @Test
//    public void shouldReturnBadRequestBecauseOfBadDayFormat() throws Exception {
//        //GIVEN
//        Spot spot = new Spot("WCC12349", DriverType.REGULAR, new Date());
//        mockMvc.perform(post(uriStartMeter)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsString(spot)));
//        //WHEN
//        mockMvc.perform(post(uriStartMeter)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsString(spot)))
//        //THEN
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
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
