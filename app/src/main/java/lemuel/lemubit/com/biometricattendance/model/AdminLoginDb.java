package lemuel.lemubit.com.biometricattendance.model;

import io.realm.RealmObject;

public class AdminLoginDb extends RealmObject {
    private int id;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
