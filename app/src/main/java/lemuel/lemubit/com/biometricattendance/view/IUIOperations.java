package lemuel.lemubit.com.biometricattendance.view;

public interface IUIOperations {
    void showProgressDialog(String title, String message);

    void dismissProgressDialog();

    void setProgressDialog();

    void showInfoToast(String info);
}
