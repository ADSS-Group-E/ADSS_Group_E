package PresentationLayer;

import BuisnessLayer.*;

import java.time.LocalDate;
<<<<<<< HEAD
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
=======
import java.util.*;
>>>>>>> 38d4f499d7c84c95261052b2e8140587cb9670a3

public class Main {

    private static Scanner reader=new Scanner(System.in);
    private static Facade facade=Facade.getInstance();

    public static void main(String[] args) {
        LocalDate date=LocalDate.now();
        int dayatWeek=date.getDayOfWeek().getValue();
        System.out.println(dayatWeek);
        Facade facade=Facade.getInstance();
    }

    public static HiringConditions createHiringConditions(){
        double salary;
        String fund;
        int vacationDays,sickLeavePerMonth;
        System.out.println("Enter salary per hour:");
        salary=reader.nextDouble();
        System.out.println("Enter fund:");
        fund=reader.next();
        System.out.println("Enter the amount of vacation days per year");
        vacationDays=reader.nextInt();
        System.out.println("Enter the number of sick days per month");
        sickLeavePerMonth=reader.nextInt();
        return new HiringConditions(salary,fund,vacationDays,sickLeavePerMonth);
    }

    public static LocalDate createDate(){
        int day,month,year;
        System.out.println("Enter the day:");
        day= reader.nextInt();
        System.out.println("Enter the month:");
        month= reader.nextInt();
        System.out.println("Enter the year:");
        year= reader.nextInt();
        return LocalDate.of(day,month,year);
    }

    public static BankAccount createBankAccount(){
        String bankName,branch,bankAccount;
        System.out.println("Enter bank name");
        bankName=reader.next();
        System.out.println("Enter bank branch number");
        branch= reader.next();
        System.out.println("Enter bank account number");
        bankAccount= reader.next();
        return new BankAccount(bankName,branch,bankAccount);
    }

    public static  List<Qualifications> createQualifications(){
        char c;
        List<Qualifications>qualifications=new LinkedList<>();
        for(Qualifications q : Qualifications.values()){
            do {
                System.out.println("Do the worker is talented to work as " + q.name() + " Enter Y/N");
                c = reader.next().charAt(0);
            }while(c!='Y'&&c!='y'&&c!='n'&&c!='N');
            if(c=='Y'||c=='y')
                qualifications.add(q);
        }
        return qualifications;
    }

    public  static  Worker createWorker(){
        String firstname,lastname,ID;
        System.out.println("Enter worker first name:");
        firstname=reader.next();
        System.out.println("Enter worker last name:");
        lastname=reader.next();
        System.out.println("Enter worker ID");
        ID=reader.next();
        BankAccount bankAccount=createBankAccount();
        LocalDate startWorkingDay=createDate();
        HiringConditions hiringConditions=createHiringConditions();
        AvailableWorkDays availableWorkDays= createAvailableWorkDays();
        List<Qualifications>qualifications=createQualifications();
        return new Worker(firstname,lastname,ID,bankAccount,startWorkingDay,hiringConditions,availableWorkDays,qualifications);
    }

    private static AvailableWorkDays createAvailableWorkDays() {
        return null;
    }

    public static void initialSystem(){
        Worker branchManager=createWorker();
        Worker activeHRD=createWorker();
        Worker worker1=createWorker();
        Worker worker2=createWorker();
        Worker worker3=createWorker();
        Worker worker4=createWorker();
        facade.addBranch(1,branchManager,activeHRD);
        facade.addWorker(worker1,1);
        facade.addWorker(worker2,1);
        facade.addWorker(worker3,1);
        facade.addWorker(worker4,1);


    }

    public static  Facade createBasicFacade(){
        return Facade.getInstance();
    }

    public  static AvailableWorkDays createAvailableWorkDay(){
        Boolean [][] available = new Boolean[7][2];
        HashMap<Integer , String> week = new HashMap<Integer, String>();
        week.put(0,"sunday");
        week.put(1,"monday");
        week.put(2,"tuesday");
        week.put(3,"wednesday");
        week.put(4,"thursday");
        week.put(5,"friday");
        week.put(6,"saturday");
        String can;
        String shift;
        for (int i =0; i<7; i++)
            for (int j=0;j<2;j++) {
                if(j==0)
                    shift = "Morning";
                else shift = "Evening";
                System.out.println("Can you work at day " + week.get(i) + "in shift "+ shift + "? Enter Y/N");
                can = reader.next();
                while (!(can.equals("Y")|| can.equals("y")|| can.equals("N") || can.equals("n")))
                        can =reader.next();
                if(can.equals("Y") || can.equals("y"))
                    available[i][j]=true;
                else available[i][j]=false;

            }

        Boolean [][] favorite = new Boolean[7][2];
        String prefer;
        for (int i =0; i<7; i++)
            for (int j=0;j<2;j++) {
                if(j==0)
                    shift = "Morning";
                else shift = "Evening";
                System.out.println("Do you prefer to work at day " + week.get(i) + "in shift "+ shift + "? Enter Y/N");
                prefer = reader.next();
                while (!(prefer.equals("Y")|| prefer.equals("y")|| prefer.equals("N") || prefer.equals("n")))
                    prefer =reader.next();
                if(prefer.equals("Y") || prefer.equals("y"))
                    favorite[i][j]=true;
                else favorite[i][j]=false;

            }
        AvailableWorkDays availableWorkDays = new AvailableWorkDays(favorite,available);
            return availableWorkDays;



    }

}
