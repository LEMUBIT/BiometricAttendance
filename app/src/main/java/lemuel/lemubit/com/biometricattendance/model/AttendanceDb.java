package lemuel.lemubit.com.biometricattendance.model;

import io.realm.RealmObject;

public class AttendanceDb extends RealmObject  {
    private int id;
    private String time;
    private String date;
    private int clockState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getClockState() {
        return clockState;
    }

    public void setClockState(int clockState) {
        this.clockState = clockState;
    }
}
