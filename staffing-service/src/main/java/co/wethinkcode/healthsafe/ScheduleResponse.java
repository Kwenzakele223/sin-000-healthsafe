
package co.wethinkcode.healthsafe;

public class ScheduleResponse {

    private final String wardId;
    private final int alertLevel;
    private final String schedule;

    public ScheduleResponse(
            String wardId,
    int alertLevel,
    String schedule
    ) {
        this.wardId = wardId;
        this.alertLevel = alertLevel;
        this.schedule = schedule;
    }

    public String getWardId() {
        return wardId;
    }

    public int getAlertLevel() {
        return alertLevel;
    }

    public String getSchedule() {
        return schedule;
    }
}
