package com.alex_nechaev.jobagent;

public class Agent {
    private int id;
    private int progress;
    private String field;
    private int location;
    private String calendar;
    private boolean mobility;
    private int kilometers;
    private int jobType;
    private boolean isInitialized;

    public Agent(int id) {
        this.id = id;
        this.isInitialized = false;
    }

    public Agent(Agent copyAgent) {
        if(copyAgent != null) {
            this.id = copyAgent.id;
            this.progress = copyAgent.progress;
            this.field = copyAgent.field;
            this.location = copyAgent.location;
            this.calendar = copyAgent.calendar;
            this.mobility = copyAgent.mobility;
            this.kilometers = copyAgent.kilometers;
            this.jobType = copyAgent.jobType;
            this.isInitialized = copyAgent.isInitialized;
        }
    }

    public Agent(int id, int progress, String field, int location, String calendar, boolean mobility, int kilometers, int jobType, boolean isInitialized) {
        this.id = id;
        this.progress = progress;
        this.field = field;
        this.location = location;
        this.calendar = calendar;
        this.mobility = mobility;
        this.kilometers = kilometers;
        this.jobType = jobType;
        this.isInitialized = isInitialized;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public boolean isMobility() {
        return mobility;
    }

    public void setMobility(boolean mobility) {
        this.mobility = mobility;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }
}
