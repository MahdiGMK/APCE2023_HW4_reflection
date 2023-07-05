package model;

public class JohnWickChild extends JohnWick {
    public float apGrade;

    public JohnWickChild(){}
    public JohnWickChild(float apGrade, int strength, char activateKey){
        super(strength, activateKey);
        this.apGrade = apGrade;
    }
}
