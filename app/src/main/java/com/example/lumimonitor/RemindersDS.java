package com.example.lumimonitor;

public class RemindersDS {

    private String timeReminderSet;
    private String reminderTime;
    private String reminderDate;
    private String reminderName;
    private String reminderDesc;

    public RemindersDS() {
    }

    public RemindersDS(String timeReminderSet, String reminderTime, String reminderDate, String reminderName, String reminderDesc) {
        this.timeReminderSet = timeReminderSet;
        this.reminderTime = reminderTime;
        this.reminderDate = reminderDate;
        this.reminderName = reminderName;
        this.reminderDesc = reminderDesc;
    }

    public String getTimeReminderSet() {
        return timeReminderSet;
    }

    public void setTimeReminderSet(String timeReminderSet) {
        this.timeReminderSet = timeReminderSet;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getReminderDesc() {
        return reminderDesc;
    }

    public void setReminderDesc(String reminderDesc) {
        this.reminderDesc = reminderDesc;
    }

 }
