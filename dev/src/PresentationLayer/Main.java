package PresentationLayer;

import BuisnessLayer.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner reader=new Scanner(System.in);
    private static Facade facade=Facade.getInstance();

    public static void main(String[] args) {
        LocalDate date=LocalDate.now();
        int dayatWeek=date.getDayOfWeek().getValue();
        System.out.println(dayatWeek);
        Facade facade=Facade.getInstance();
    }

    //public static List<Qualifications>
    


    public  static  Worker initialWorker(){
        String firstname,lastname,ID,bankName,branch,bankAccount;
        int day,month,year;
        System.out.println("Enter worker first name:");
        firstname=reader.next();
        System.out.println("Enter worker last name:");
        lastname=reader.next();
        System.out.println("Enter worker ID");
        ID=reader.next();
        System.out.println("Enter bank name");
        bankName=reader.next();
        System.out.println("Enter bank branch number");
        branch= reader.next();
        System.out.println("Enter bank account number");
        bankAccount= reader.next();
        System.out.println("enter worker start day (enter day then month then year separate with enters)");
        day= reader.nextInt();
        month=reader.nextInt();
        year=reader.nextInt();






        return null;
    }

    public static void initialSystem(Facade facade){
        BankAccount bankAccount=new BankAccount("Mizrahi-Tefahot","234","0123456");
        LocalDate date=LocalDate.of(2001,5,23);
        HiringConditions hiringConditions=new HiringConditions(10000,"Meitav-Dash",10,20);
        List<Qualifications>qualifications=new LinkedList<>();
        qualifications.add(Qualifications.BranchManager);
        Worker branchManager=new Worker("Yoad","Ohayon","323079103",bankAccount,date,hiringConditions,new AvailableWorkDays(),qualifications);
        Worker activeHRD=new Worker("Omer","Shitrit","208060210",bankAccount,date,hiringConditions,new AvailableWorkDays(),qualifications);
        Worker worker1=new Worker("Guy","Lerer","2083678543",bankAccount,date,hiringConditions,new AvailableWorkDays(),qualifications);
        facade.addBranch(1,branchManager,activeHRD);
        facade.addWorker(worker1,1);



    }

    public static  Facade createBasicFacade(){
        return Facade.getInstance();
    }

}
