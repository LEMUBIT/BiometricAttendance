package lemuel.lemubit.com.biometricattendance.model;

import io.realm.Realm;
import io.realm.RealmResults;
import lemuel.lemubit.com.biometricattendance.util.DBOperation;

public class DBHelper {
    private static DBOperation dbOperationSuccess = DBOperation.SUCCESSFUL;
    private static DBOperation dbOperationFailure = DBOperation.FAILED;

    public static DBOperation newAdminPassword(String password) {
        try {
            AdminLoginDb adminLoginDb = new AdminLoginDb();
            adminLoginDb.setId(1);
            adminLoginDb.setPassword(password);

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> realm.copyToRealmOrUpdate(adminLoginDb));

            return dbOperationSuccess;
        } catch (Exception e) {
            return dbOperationFailure;
        }
    }

    public static DBOperation loginAdmin(String password) {
        Realm realm = Realm.getDefaultInstance();
        AdminLoginDb realDBPassword = realm.where(AdminLoginDb.class).equalTo("id", 1).findFirst();
        if (realDBPassword.getPassword().equals(password)) {
            return dbOperationSuccess;
        } else {
            return dbOperationFailure;
        }
    }
}
