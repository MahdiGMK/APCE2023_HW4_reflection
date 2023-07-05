package model;

public class John {
    public static class Wick {
        String name;

        public String getName() {
            return name;
        }

        public Wick(){};
        public Wick(String name){
            this.name = name;
        }
    }

    public Wick john;

    public John(){}
    public John(Wick john){
        this.john = john;
    }

}

