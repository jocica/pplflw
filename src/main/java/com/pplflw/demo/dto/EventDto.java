package com.pplflw.demo.dto;

import com.pplflw.demo.events.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDto {

    private Event event;
    private Long employeeId;
}
