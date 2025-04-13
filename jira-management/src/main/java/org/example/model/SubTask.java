package org.example.model;

import lombok.Data;
import org.example.config.WorkFlow;

import java.util.List;

@Data
public class SubTask {
    private final String title;
    private int workflowStatusIndex;
    private final String parentTicket;

    private final List<WorkFlow> workFlows;

    public SubTask(String title, String parentTicket, List<WorkFlow> workFlows) {
        this.title = title;
        this.workflowStatusIndex = 0;
        this.parentTicket = parentTicket;
        this.workFlows = workFlows;
    }

    public void updateWorkFlowToNext(){
        if(workflowStatusIndex!=workFlows.size()){
            workflowStatusIndex++;
        }
        return;
    }

    public WorkFlow getNExtWorkFlow(){
        if(workflowStatusIndex!=workFlows.size()){
            return workFlows.get(workflowStatusIndex+1);
        }
        return null;
    }

    public void setWorkFlowToNext(){
        if(workflowStatusIndex!=workFlows.size()){
            workflowStatusIndex++;
            return;
        }
    }
}
