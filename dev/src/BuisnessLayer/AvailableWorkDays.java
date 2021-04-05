package BuisnessLayer;

import java.util.Arrays;

public class AvailableWorkDays {
    private Boolean favoriteShifts[][];
    private Boolean cantWork[][];

    public Boolean[][] getFavoriteShifts() {
        return favoriteShifts;
    }

    public void setFavoriteShifts(Boolean[][] favoriteShifts) {
        this.favoriteShifts = favoriteShifts;
    }

    public Boolean[][] getCantWork() {
        return cantWork;
    }

    public void setCantWork(Boolean[][] cantWork) {
        this.cantWork = cantWork;
    }

    public AvailableWorkDays(Boolean[][] favoriteShifts, Boolean[][] cantWork) {
        this.cantWork=new Boolean[7][2];
        this.favoriteShifts=new Boolean[7][2];
        this.favoriteShifts = favoriteShifts;
        this.cantWork = cantWork;
    }

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
    public AvailableWorkDays(Boolean[][] favoriteShifts, Boolean[][] cantWork) {
     /*   this.cantWork=new Boolean[7][2];
        this.favoriteShifts=new Boolean[7][2];*/
        this.favoriteShifts = favoriteShifts;
        this.cantWork = cantWork;
    }


    @Override
    public String toString() {
        return "AvailableWorkDays{" +
                "favoriteShifts=" + Arrays.toString(favoriteShifts) +
                ", cantWork=" + Arrays.toString(cantWork) +
                '}';
    }
}
