package lemuel.lemubit.com.biometricattendance.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserInformationDb extends RealmObject {
    @PrimaryKey
    private int id;

    private byte [] userPhoto;
    private int clockedState;

    private String firstName;
    private String lastName;
    private String sex;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;

    private int rightThumb;
    private int rightIndex;
    private int rightMiddle;
    private int rightRing;
    private int rightPinky;

    private int leftThumb;
    private int leftIndex;
    private int leftMiddle;
    private int leftRing;
    private int leftPinky;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getClockedState() {
        return clockedState;
    }

    public void setClockedState(int clockedState) {
        this.clockedState = clockedState;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRightThumb() {
        return rightThumb;
    }

    public void setRightThumb(int rightThumb) {
        this.rightThumb = rightThumb;
    }

    public int getRightIndex() {
        return rightIndex;
    }

    public void setRightIndex(int rightIndex) {
        this.rightIndex = rightIndex;
    }

    public int getRightMiddle() {
        return rightMiddle;
    }

    public void setRightMiddle(int rightMiddle) {
        this.rightMiddle = rightMiddle;
    }

    public int getRightRing() {
        return rightRing;
    }

    public void setRightRing(int rightRing) {
        this.rightRing = rightRing;
    }

    public int getRightPinky() {
        return rightPinky;
    }

    public void setRightPinky(int rightPinky) {
        this.rightPinky = rightPinky;
    }

    public int getLeftThumb() {
        return leftThumb;
    }

    public void setLeftThumb(int leftThumb) {
        this.leftThumb = leftThumb;
    }

    public int getLeftIndex() {
        return leftIndex;
    }

    public void setLeftIndex(int leftIndex) {
        this.leftIndex = leftIndex;
    }

    public int getLeftMiddle() {
        return leftMiddle;
    }

    public void setLeftMiddle(int leftMiddle) {
        this.leftMiddle = leftMiddle;
    }

    public int getLeftRing() {
        return leftRing;
    }

    public void setLeftRing(int leftRing) {
        this.leftRing = leftRing;
    }

    public int getLeftPinky() {
        return leftPinky;
    }

    public void setLeftPinky(int leftPinky) {
        this.leftPinky = leftPinky;
    }
}
