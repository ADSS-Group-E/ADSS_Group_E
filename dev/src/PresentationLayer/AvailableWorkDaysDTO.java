package PresentationLayer;

public class AvailableWorkDaysDTO {
    private Boolean favoriteShifts[][];

    private Boolean cantWork[][];

    public Boolean[][] getFavoriteShifts() {
        return favoriteShifts;
    }

    public AvailableWorkDaysDTO(Boolean[][] favoriteShifts, Boolean[][] cantWork) {
        this.favoriteShifts = favoriteShifts;
        this.cantWork = cantWork;
    }

    public Boolean[][] getCantWork() {
        return cantWork;
    }

    public void setFavoriteShifts(Boolean[][] favoriteShifts) {
        this.favoriteShifts = favoriteShifts;
    }

    public void setCantWork(Boolean[][] cantWork) {
        this.cantWork = cantWork;
    }

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
