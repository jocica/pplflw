package com.pplflw.demo.facade;

import com.pplflw.demo.dto.ApiResponse;
import com.pplflw.demo.dto.EventDto;
import com.pplflw.demo.services.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFacade {

    private final EventsService eventsService;

    private final String EVENt_SUBMITTED = "Event submitted";

    public ApiResponse createNewEvent(EventDto eventDto) {

        eventsService.sendEvent(eventDto.getEvent(), eventDto.getEmployeeId());

        return new ApiResponse(EVENt_SUBMITTED);
    }
}
