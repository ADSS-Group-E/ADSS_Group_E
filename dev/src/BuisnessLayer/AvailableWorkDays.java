package BuisnessLayer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

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
        this.cantWork=new Boolean[7][2];
        this.favoriteShifts=new Boolean[7][2];
        for(int i=0;i<7;i++){
            for(int j=0;j<2;j++){
                this.cantWork[i][j]=cantWork[i][j];
                this.favoriteShifts[i][j]=favoriteShifts[i][j];
            }
        }
    }
    @Override
    public String toString() {
        String string = "AvailableWorkDays: ";
        String days[] = new String[7];
        days[0] = "sunday";
        days[1] = "monday";
        days[2] = "tuesday";
        days[3] = "wednesday";
        days[4] = "thursday";
        days[5] = "friday";
        days[6] = "saturday";
        String type[] = new String[2];
        type[0] = "morning";
        type[1] = "evening";
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                if (favoriteShifts[i][j])
                    string = string + "Prefer to work at " + days[i] + " at " + type[j] + " shift \n";
            }
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                if (cantWork[i][j])
                    string = string + "cant to work at " + days[i] + " at " + type[j] + " shift \n";
            }
        }
        return string;
    }
}
