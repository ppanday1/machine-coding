package org.example.config;

import java.util.List;

public class TicketConfig {
    private final List<WorkFlow> workFlows;
    private final boolean canHaveSprint;

    public TicketConfig(List<WorkFlow> workFlows, boolean canHaveSprint) {
        this.workFlows = workFlows;
        this.canHaveSprint = canHaveSprint;
    }

    public synchronized WorkFlow getWorkFlowAt(int index) {
        return workFlows.get(index);
    }

    public synchronized int gteWorkflowSize() {
        return workFlows.size();
    }

    public List<WorkFlow> getWorkFlows() {
        return workFlows;
    }
}
