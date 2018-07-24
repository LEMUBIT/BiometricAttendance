package lemuel.lemubit.com.biometricattendance.view;
import com.wepoy.fp.FingerprintImage;
public interface IFingerPrintOperation {
    void showProgressDialog(String title, String message);

    void dismissProgressDialog();

    void setProgressDialog();

    void showInfoToast(String info);

void updateFingerPrintImage(FingerprintImage fi);
}
