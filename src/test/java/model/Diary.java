package model;

import java.util.ArrayList;

public class Diary {
    private ArrayList<Integer> countOfDailyKilled;
    private int totalCount;

    public Diary(){}
    public Diary(ArrayList<Integer> countOfDailyKilled, int totalCount){
        this.countOfDailyKilled = countOfDailyKilled;
        this.totalCount = totalCount;
    }


    public ArrayList<Integer> getCountOfDailyKilled() {
        return countOfDailyKilled;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
