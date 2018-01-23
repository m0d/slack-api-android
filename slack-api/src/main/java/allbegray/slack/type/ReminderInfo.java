package allbegray.slack.type;

public class ReminderInfo {

    protected Reminder reminder;

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "ReminderInfo{" +
                "reminder=" + reminder +
                '}';
    }
}