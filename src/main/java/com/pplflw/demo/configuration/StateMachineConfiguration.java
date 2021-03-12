package com.pplflw.demo.configuration;

import com.pplflw.demo.events.Event;
import com.pplflw.demo.states.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<EmployeeState, Event> {

    @Override
    public void configure(StateMachineStateConfigurer<EmployeeState, Event> states)
            throws Exception {
        states
                .withStates()
                .initial(EmployeeState.ADDED)
                .states(EnumSet.allOf(EmployeeState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EmployeeState, Event> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(EmployeeState.ADDED).target(EmployeeState.IN_CHECK)
                .event(Event.EMPLOYEE_CHECKING)
                .and()
                .withExternal()
                .source(EmployeeState.IN_CHECK).target(EmployeeState.APPROVED)
                .event(Event.EMPLOYEE_APPROVING)
                .and()
                .withExternal()
                .source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE)
                .event(Event.EMPLOYEE_ACTIVATE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<EmployeeState, Event> configurer) throws Exception {

        configurer.withConfiguration()
                .listener(new StateMachineListener())
                .autoStartup(false);
    }

    private static final class StateMachineListener extends StateMachineListenerAdapter<EmployeeState, Event> {

        @Override
        public void stateChanged(State<EmployeeState, Event> from, State<EmployeeState, Event> to) {
            log.info("Employee state changed to " + to.getId());
        }
        public void stateMachineError(StateMachine<EmployeeState, Event> stateMachine, Exception exception) {
            log.error("Employee state failed due to exception", exception);
        }
    }
}
