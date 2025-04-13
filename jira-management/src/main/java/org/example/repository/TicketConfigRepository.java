package org.example.repository;

import org.example.config.TicketConfig;
import org.example.config.TicketType;
import org.example.config.WorkFlow;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TicketConfigRepository {
    private final ConcurrentHashMap<TicketType, TicketConfig>ticketConfigs;

    public TicketConfigRepository() {
        ticketConfigs=new ConcurrentHashMap<>();
        List<WorkFlow> workFlows=List.of(WorkFlow.OPEN,WorkFlow.IN_PROGRESS,WorkFlow.TESTING,WorkFlow.IN_REVIEW,WorkFlow.DEPLOYED);
        ticketConfigs.put(TicketType.STORY,new TicketConfig(workFlows,true));

        workFlows=List.of(WorkFlow.OPEN,WorkFlow.IN_PROGRESS,WorkFlow.COMPLETED);
        ticketConfigs.put(TicketType.EPIC,new TicketConfig(workFlows,false));

        workFlows=List.of(WorkFlow.OPEN,WorkFlow.IN_PROGRESS,WorkFlow.RESOLVED);
        ticketConfigs.put(TicketType.ON_CALL,new TicketConfig(workFlows,false));
    }

    public TicketConfig getTicketConfig(TicketType type){
        return ticketConfigs.getOrDefault(type,null);
    }
}
