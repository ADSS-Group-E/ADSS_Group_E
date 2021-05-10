package Tests;


import DataAccessLayer.Workers.Shifts;

import java.sql.Time;
import java.time.LocalDate;

public class tests {

    public static void main(String[] args)
    {
        LocalDate day = LocalDate.of(2021, 5, 22);
        //System.out.println(isDayNextWeek(day));
        //Shifts.getShiftDemands();
        Time newTime1 = Time.valueOf("08:00:00");
        System.out.println(shiftType(newTime1));
    }

    public static String shiftType(Time time){
        if((time.after(Time.valueOf("08:00:00"))||time.toString().equals("08:00:00"))&&time.before(Time.valueOf("16:00:00")))
            return "Morning";
        if((time.after(Time.valueOf("16:00:00"))||time.toString().equals("16:00:00"))&&(time.before(Time.valueOf("23:00:00"))||time.toString().equals("23:00:00")))
            return "Evening";
        return null;
    }
    public static boolean isDayNextWeek(LocalDate day){
        LocalDate upcomingSunday=LocalDate.now();
        switch (upcomingSunday.getDayOfWeek().getValue()){
            case 1:
                upcomingSunday=upcomingSunday.plusDays(6);
                break;
            case 2:
                upcomingSunday=upcomingSunday.plusDays(5);
                break;
            case 3:
                upcomingSunday=upcomingSunday.plusDays(4);
                break;
            case 4:
                upcomingSunday=upcomingSunday.plusDays(3);
                break;
            case 5:
                upcomingSunday=upcomingSunday.plusDays(2);
                break;
            case 6:
                upcomingSunday=upcomingSunday.plusDays(1);
                break;
            case 7:
                break;
        }
        LocalDate Nextsunday=upcomingSunday.plusDays(7);
        return  day.isBefore(Nextsunday)&&(day.isAfter(upcomingSunday)||day.isEqual(upcomingSunday)) ? true : false;
    }
}
