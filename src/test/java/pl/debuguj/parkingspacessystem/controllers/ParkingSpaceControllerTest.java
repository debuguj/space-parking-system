package pl.debuguj.parkingspacessystem.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.debuguj.parkingspacessystem.ParkingspacessystemApplication;
import pl.debuguj.parkingspacessystem.services.ParkingSpaceManagementService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by grzesiek on 13.10.17.
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

//    @Test
//    public void checkVehicle() throws Exception {
//        assertTrue(true);
//    }
//
//    @Test
//    public void stopParkingMeter() throws Exception {
//
//    }
//
//    @Test
//    public void checkIncomePerDay() throws Exception {
//
//    }

}