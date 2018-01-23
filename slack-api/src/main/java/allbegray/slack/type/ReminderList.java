package allbegray.slack.type;

import java.util.List;
public class ReminderList {

    protected List<Reminder> reminders;


    public List<Reminder> getReminders() {
        return reminders;
    }


    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }


    @Override
    public String toString() {
        return "ReminderList{" +
                "reminders=" + reminders +
                '}';
    }
}