package com.pplflw.demo.controllers.impl;

import com.pplflw.demo.dto.ApiResponse;
import com.pplflw.demo.controllers.EventController;
import com.pplflw.demo.dto.EventDto;
import com.pplflw.demo.facade.EventFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private final EventFacade eventFacade;

    @Override
    public ApiResponse createNewEvent(EventDto eventDto) {
        return eventFacade.createNewEvent(eventDto);
    }
}
