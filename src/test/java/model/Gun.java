package model;

public class Gun {
    private int power;
    private String gunName;

    public Gun(){}
    public Gun(int power, String gunName){
        this.power = power;
        this.gunName = gunName;
    }

    public int getPower() {
        return power;
    }

    public String getGunName() {
        return gunName;
    }
}
