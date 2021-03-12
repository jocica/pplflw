package com.pplflw.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplflw.demo.dto.ApiResponse;
import com.pplflw.demo.dto.EventDto;
import com.pplflw.demo.events.Event;
import com.pplflw.demo.facade.EmployeeFacade;
import com.pplflw.demo.facade.EventFacade;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventFacade eventFacade;

    @Test
    @SneakyThrows
    void createNewEvent() {
        EventDto eventDto = new EventDto();
        eventDto.setEmployeeId(1L);
        eventDto.setEvent(Event.EMPLOYEE_CHECKING);

        when(eventFacade.createNewEvent(any(EventDto.class))).thenReturn(new ApiResponse("MSG"));

        mockMvc.perform(post("/events")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(status().isOk());
    }
}