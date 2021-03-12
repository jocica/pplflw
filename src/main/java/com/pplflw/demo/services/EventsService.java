package com.pplflw.demo.services;

import com.pplflw.demo.events.Event;
import com.pplflw.demo.states.EmployeeState;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventsService {

    private final EmployeeService employeeService;
    private final StateMachineFactory<EmployeeState, Event> stateMachineFactory;

    private static final String MSG_HEADER_NAME = "employeeId";


    public boolean sendEvent(Event event, Long employeeId){
        var stateMachine = initStateMachine(employeeId);

        Message<Event> message = MessageBuilder.withPayload(event)
                .setHeader(MSG_HEADER_NAME, employeeId)
                .build();

        return stateMachine.sendEvent(message);
    }


    private StateMachine<EmployeeState, Event>  initStateMachine(Long employeeId){
        var employee = employeeService.findEmployee(employeeId);
        var employeeIdStr = Long.toString(employee.getId());

        var stateMachine = stateMachineFactory.getStateMachine(employeeIdStr);
        stateMachine.stop();

        var stateMachineAccessor = stateMachine.getStateMachineAccessor();

        stateMachineAccessor.doWithAllRegions(stateMachineAccess -> {
            stateMachineAccess.addStateMachineInterceptor(new StateMachineInterceptor());
            stateMachineAccess.resetStateMachine(new DefaultStateMachineContext<>(employee.getState(),
                    null, null, null));
        });
        stateMachine.start();
        return stateMachine;
    }

    private class StateMachineInterceptor extends StateMachineInterceptorAdapter<EmployeeState, Event>{

        @Override
        public void preStateChange(State<EmployeeState, Event> state, Message<Event> message,
                                   Transition<EmployeeState, Event> transition,
                                   StateMachine<EmployeeState, Event> stateMachine,
                                   StateMachine<EmployeeState, Event> rootStateMachine) {

            Object employeeHeader = message.getHeaders().getOrDefault(MSG_HEADER_NAME, "-1");
            Long employeeId = Long.parseLong(String.valueOf(employeeHeader));
            employeeService.changeStateOfEmployee(employeeId, state.getId());
        }
    }
}
