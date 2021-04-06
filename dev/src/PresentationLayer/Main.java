package PresentationLayer;

import BuisnessLayer.*;

import java.time.LocalDate;
import java.util.*;

public class Main {

    private static Scanner reader=new Scanner(System.in);
    private static Facade facade=Facade.getInstance();

    public static void main(String[] args) {
        LocalDate date=LocalDate.now();
        int dayatWeek=date.getDayOfWeek().getValue();
        System.out.println(dayatWeek);
        Facade facade=Facade.getInstance();

        System.out.println(LocalDate.now());
        System.out.println("Welcome to Super Lee's System, please enter your ID and your in order to log in");
        String ID = reader.next();
        Worker worker = facade.findWorkerByID(ID);
        while(worker == null) {
            System.out.println("There is no worker with such ID please enter new ID");
            ID = reader.next();
            worker = facade.findWorkerByID(ID);
        }
        int branchID = facade.getBranchController().findBranchByWorker(ID).getBranchID();
        List<Qualifications> qualifications = facade.getWorkerQualifications(worker);
        if(qualifications.contains(Qualifications.BranchManager)){
            System.out.println("Welcome "+ worker.getFirstName()+" "+ worker.getLastName()+", the branch manager of branch "+branchID+" please choose an action");
            System.out.println("1) Create new weekly assignment");
            System.out.println("2) Replace a shift between two workers");
            System.out.println("3) Display current day shift assignment by branch ID");
            System.out.println("4) Display shift assignment by date and by branch ID");
            System.out.println("5) Search worker by Branch ID and worker ID");
            System.out.println("6) Display workers by Qualification and by branch ID");

            int menu = reader.nextInt();
       /*     if(menu == 1) {
                System.out.println("Please enter the ID of the HRD");
                facade.getShiftController().createWeeklyAssignment();

            }*/
            if(menu == 2)
            {   System.out.println("Please enter the Date of the first shift");
                LocalDate date1 = createDate();
                System.out.println("Please enter the type of the first shift for morning press M and for evening press E");
                String type1 = reader.next();
                ShiftType shiftType1 = createShiftType(type1);;
                if(shiftType1==null) {
                    do {
                        System.out.println("The type was incorrect Please enter again the type of the first shift for morning press M and for evening press E");
                        type1 = reader.next();
                        shiftType1 = createShiftType(type1);

                    } while (shiftType1 == null);
                }

                System.out.println("Please enter the Date of the second shift");
                LocalDate date2 = createDate();
                System.out.println("Please enter the type of the second shift for morning press M and for evening press E");
                String type2 = reader.next();
                ShiftType shiftType2 = createShiftType(type2);;
                if(shiftType2==null) {
                    do {
                        System.out.println("The type was incorrect Please enter again the type of the second shift for morning press M and for evening press E");
                        type2 = reader.next();
                        shiftType2 = createShiftType(type2);

                    } while (shiftType2 == null);
                }


                System.out.println("The workers in the first shift are: ");
                facade.printWorkersAtShift(branchID,date1,shiftType1);
                int worker1SerialNumber,worker2SerialNumber;
                System.out.println("Enter the worker's serial number you want to replace");
                worker1SerialNumber=reader.nextInt();

                System.out.println("Workers at second shift:");
                facade.printWorkersAtShift(branchID,date2,shiftType2);
                System.out.println("Enter the worker's serial number you want to replace");
                worker2SerialNumber=reader.nextInt();

                Worker worker1=facade.getBranchController().getBranch(branchID).getCurrentWorkersList().get(worker1SerialNumber-1);
                Worker worker2= facade.getBranchController().getBranch(branchID).getCurrentWorkersList().get(worker2SerialNumber-1);
                facade.workerReplacement(branchID,date1,shiftType1,date2,shiftType2,worker1,worker2,worker);
            }
            if(menu == 3) {

                ShiftType morning = ShiftType.Morning;
                ShiftType evening = ShiftType.Evening;
                System.out.println("Please enter the branch ID");
                int bID = reader.nextInt();
                facade.printWorkersAtShift(bID, LocalDate.now(), morning);
                facade.printWorkersAtShift(bID, LocalDate.now(), evening);
            }

            if(menu == 4) {
                System.out.println("Please enter the date of the shift");
                LocalDate shiftDate = createDate();
                System.out.println("Please enter the type of the first shift for morning press M and for evening press E");
                String type = reader.next();
                ShiftType shiftType = createShiftType(type);;
                if(shiftType==null) {
                    do {
                        System.out.println("The type was incorrect Please enter again the type of the first shift for morning press M and for evening press E");
                        type = reader.next();
                        shiftType = createShiftType(type);

                    } while (shiftType == null);
                }
                System.out.println("Please enter the branch ID");
                int b = reader.nextInt();
                facade.printWorkersAtShift(b, date, shiftType);
            }

            if(menu == 5){
                System.out.println("Please enter the ID of the worker");
                String IDForPrint = reader.next();
                System.out.println("Please enter the ID of the branch");
                int bID = reader.nextInt();
                facade.printWorker(facade.findWorker(bID,IDForPrint));

            }

            if(menu == 6){
                System.out.println("Please choose qualification number");
                System.out.println("1) Cashier ");
                System.out.println("2) Storekeeper");
                System.out.println("3) Arranger");
                System.out.println("4) Human Resources Director");
                System.out.println("5) Guard");
                System.out.println("6) BranchManager");
                System.out.println("7) Assistant");
                System.out.println("8) ShiftManager");

                switch(menu){
                    case 1:
                        System.out.println("Cashier:");

                }



            }

        }








    }

    private static ShiftType createShiftType(String type) {
        if(type.equals("M")||type.equals("m"))
            return ShiftType.Morning;
        else if(type.equals("E")||type.equals("e"))
            return ShiftType.Evening;
        else return null;
    }

    //public static List<Qualifications>

    public static LocalDate createDate(){
        int day,month,year;
        System.out.println("Enter the day in month:");
        day= reader.nextInt();
        System.out.println("Enter the month:");
        month= reader.nextInt();
        System.out.println("Enter the year:");
        year= reader.nextInt();
        return LocalDate.of(year,month,day);
    }


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
