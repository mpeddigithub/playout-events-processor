package com.myorg.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.domain.PlayoutEvent;
import com.myorg.domain.PlayoutEventType;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayoutEventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private PlayoutEvent startEvent, stopEvent;
    private PlayoutEventType startEventType, stopEventType;
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        startEventType = new PlayoutEventType("START", "u6", "c6");
        startEvent = new PlayoutEvent(1576063768, "s6", startEventType);

        stopEventType = new PlayoutEventType("STOP");
        stopEvent = new PlayoutEvent(1576064468, "s6", stopEventType);
    }

    @Test
    public void testProcessPlayoutEvent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/event")
                .content(mapper.writeValueAsString(startEvent))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/event")
                .content(mapper.writeValueAsString(stopEvent))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contentwatched")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is("u6")));
    }

    //TODO: can be implemented more test cases to validate duration less than 10 minutes and duration more than 8 hours

    @After
    public void tearDown() {
        mapper = null;
        startEvent = null;
        stopEvent = null;
        startEventType = null;
        stopEventType = null;
    }

}
