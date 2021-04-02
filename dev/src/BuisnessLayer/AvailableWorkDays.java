package BuisnessLayer;

import java.util.Arrays;

public class AvailableWorkDays {
    private Boolean favoriteShifts[][];
    private Boolean cantWork[][];

    public AvailableWorkDays() {
        favoriteShifts=new Boolean[7][2];
        cantWork=new Boolean[7][2];
        for(int i=0;i<7;i++) {
            for (int j = 0; j < 2; j++) {
                favoriteShifts[i][j] = false;
                cantWork[i][j] = false;
            }
        }
    }

    @Override
    public String toString() {
        return "AvailableWorkDays{" +
                "favoriteShifts=" + Arrays.toString(favoriteShifts) +
                ", cantWork=" + Arrays.toString(cantWork) +
                '}';
    }
}
