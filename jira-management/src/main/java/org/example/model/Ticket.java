package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.config.TicketConfig;
import org.example.config.TicketType;
import org.example.config.WorkFlow;

import java.util.List;

@Data
@AllArgsConstructor
public class Ticket {
    private final String title;
    private final TicketConfig ticketConfig;
    private final TicketType type;
    private int workflowStatusIndex;

    public Ticket(String title, TicketConfig ticketConfig, TicketType type) {
        this.title = title;
        this.ticketConfig = ticketConfig;
        this.type = type;
        this.workflowStatusIndex = 0;
    }

    public synchronized WorkFlow getCurrentWorkflow() {
        return ticketConfig.getWorkFlowAt(workflowStatusIndex);
    }

    public synchronized void setWorkFlowToNext() {
        if (workflowStatusIndex == ticketConfig.gteWorkflowSize()) {
            return;
        }
        workflowStatusIndex++;
    }

    public synchronized WorkFlow getNextWorkFlow() {
        if (workflowStatusIndex == ticketConfig.gteWorkflowSize()) {
            return null;
        }
        return ticketConfig.getWorkFlowAt(workflowStatusIndex + 1);
    }

    public List<WorkFlow> getWorkFlows() {
        return ticketConfig.getWorkFlows();
    }
}
