package pl.debuguj.parkingspacessystem.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.debuguj.parkingspacessystem.ParkingspacessystemApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

/**
 * Created by grzesiek on 14.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingspacessystemApplication.class)
@WebAppConfiguration
public class ParkingSpaceControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testSystemReturnCorrectHttpStatusAndPayload() throws Exception {
        String getFeeWithParamsUrl = new StringBuilder()
                .append(ParkingSpaceController.URI_START_METER)
                .append("?registrationNumber=98765")
                .append("&driverType=VIP")
                .append("&startTime=2017-10-13 10:10:12")
                .append("&stopTime=2017-10-13 11:20:11")
                .toString();

        mockMvc.perform(get(getFeeWithParamsUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("2.0"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testSystemShouldReturnBadRequestBecauseOfBadDayFormat() throws Exception {
        String getFeeWithParamsUrl = new StringBuilder()
                .append(ParkingSpaceController.URI_START_METER)
                .append("?registrationNumber=98762")
                .append("&driverType=VIP")
                .append("&startTime=2017-10-13 10:10:12")
                .append("&stopTime=2017-10-13 11")
                .toString();

        mockMvc.perform(get(getFeeWithParamsUrl))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testSystemShouldReturnBadRequestBecauseOfBadDay() throws Exception {
        String getFeeWithParamsUrl = new StringBuilder()
                .append(ParkingSpaceController.URI_START_METER)
                .append("?registrationNumber=98762")
                .append("&driverType=VIP")
                .append("&startTime=2017-10-13 12:10:12")
                .append("&stopTime=2017-10-13 11:54:12")
                .toString();

        mockMvc.perform(get(getFeeWithParamsUrl))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testShouldReturnThatVehicleIsRegisteredInSystem() throws Exception {

        String getVehicleStatus = new StringBuilder()
                .append(ParkingSpaceController.URI_CHECK_VEHICLE)
                .append("?registrationNumber=11111")
                .append("&currentDate=2017-10-13 10:28:48")
                .toString();

        mockMvc.perform(get(getVehicleStatus))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("true"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testShouldReturnThatVehicleIsNotRegisteredInSystem() throws Exception {

        String getVehicleStatus = new StringBuilder()
                .append(ParkingSpaceController.URI_CHECK_VEHICLE)
                .append("?registrationNumber=11133")
                .append("&currentDate=2017-10-13 10:28:48")
                .toString();

        mockMvc.perform(get(getVehicleStatus))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testReturnNewValueOfFeeAfterStopMeter() throws Exception {

        String getVehicleStatus = new StringBuilder()
                .append(ParkingSpaceController.URI_STOP_METER)
                .append("?registrationNumber=11111")
                .append("&timeStamp=2017-10-13 11:40:48")
                .toString();

        mockMvc.perform(get(getVehicleStatus))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("3.0"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testReturnBadRequestAfterBadParametersWhenStopMeter() throws Exception {

        String getVehicleStatus = new StringBuilder()
                .append(ParkingSpaceController.URI_STOP_METER)
                .append("?registrationNumber=112323")
                .append("&timeStamp=2017-10-13 11:40:48")
                .toString();

        mockMvc.perform(get(getVehicleStatus))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testReturnCorrectValueOfDayIncome() throws Exception {

        String getVehicleStatus = new StringBuilder()
                .append(ParkingSpaceController.URI_CHECK_INCOME_PER_DAY)
                .append("?date=2017-10-14")
                .toString();

        mockMvc.perform(get(getVehicleStatus))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("6.0"))
                .andDo(print())
                .andReturn();
    }
}