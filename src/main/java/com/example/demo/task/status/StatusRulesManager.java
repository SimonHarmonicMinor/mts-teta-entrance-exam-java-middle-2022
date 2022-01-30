package com.example.demo.task.status;

/**
 * Analise changing status of a task.
 * Create by fkshistero on 30.01.2022.
 */
public class StatusRulesManager {

    /**
     * Can it change a status to others.
     */
    public static boolean canChange(StatusTask status, StatusTask newStatus) {

        switch (status) {
            case CREATED: {
                if(StatusTask.CLOSED.equals(newStatus))
                    return true;
                break;
            }
            case DELETED: {
                //can't change
                break;
            }
            case CLOSED: {
                if(StatusTask.CREATED.equals(newStatus))
                    return true;
                if(StatusTask.DELETED.equals(newStatus))
                    return true;
                break;
            }
        }
        return false;
    }
}
