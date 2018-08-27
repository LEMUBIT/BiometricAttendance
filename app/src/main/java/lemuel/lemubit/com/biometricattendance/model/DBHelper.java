package lemuel.lemubit.com.biometricattendance.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import lemuel.lemubit.com.biometricattendance.util.ClockedState;
import lemuel.lemubit.com.biometricattendance.util.DBOperation;
import lemuel.lemubit.com.biometricattendance.util.TimeHelper;

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

    public static DBOperation registerNewUser(int id, byte[] userPhoto, String firstName, String lastName, String sex, String dateOfBirth, String email, String phoneNumber, int rightThumb, int rightIndex, int rightMiddle, int rightRing, int rightPinky, int leftThumb, int leftIndex, int leftMiddle, int leftRing, int leftPinky) {
        try {
            Realm realm = Realm.getDefaultInstance();
            UserInformationDb userInformationDb = new UserInformationDb();
            userInformationDb.setId(id);
            userInformationDb.setUserPhoto(userPhoto);
            userInformationDb.setClockedState(ClockedState.CLOCKED_OUT);

            userInformationDb.setFirstName(firstName);
            userInformationDb.setLastName(lastName);
            userInformationDb.setSex(sex);
            userInformationDb.setDateOfBirth(dateOfBirth);
            userInformationDb.setEmail(email);
            userInformationDb.setPhoneNumber(phoneNumber);

            userInformationDb.setRightThumb(rightThumb);
            userInformationDb.setRightIndex(rightIndex);
            userInformationDb.setRightMiddle(rightMiddle);
            userInformationDb.setRightRing(rightRing);
            userInformationDb.setRightPinky(rightPinky);

            userInformationDb.setLeftThumb(leftThumb);
            userInformationDb.setLeftIndex(leftIndex);
            userInformationDb.setLeftMiddle(leftMiddle);
            userInformationDb.setLeftRing(leftRing);
            userInformationDb.setLeftPinky(leftPinky);

            realm.executeTransaction(realm1 -> realm.copyToRealmOrUpdate(userInformationDb));
            return DBOperation.SUCCESSFUL;
        } catch (Exception e) {
            return DBOperation.FAILED;
        }
    }

    public static RealmResults<UserInformationDb> getUsers() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(UserInformationDb.class).findAll();
    }

    public static DBOperation clockUser(int id, int clockState) {
        try {
            AttendanceDb attendanceDb = new AttendanceDb();
            attendanceDb.setId(id);
            attendanceDb.setTime(TimeHelper.getCurrentTime());
            attendanceDb.setDate(TimeHelper.getCurrentDate());
            attendanceDb.setClockState(clockState);

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> realm.copyToRealm(attendanceDb));
            
            updateClockedState(id, clockState);
            return DBOperation.SUCCESSFUL;
        } catch (Exception e) {
            return DBOperation.FAILED;
        }
    }

    private static void updateClockedState(int id, int clockState) {
        try {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<UserInformationDb> user = realm.where(UserInformationDb.class).equalTo("id", id).findAll();
            realm.beginTransaction();
            assert user.get(0) != null;
            user.get(0).setClockedState(clockState);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.d("Realm Exception", e.getMessage());
        }
    }
}
