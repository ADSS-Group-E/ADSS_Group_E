package PresentationLayer;

import BuisnessLayer.*;

import java.time.LocalDate;

import java.util.*;


public class Main {

    private static Scanner reader=new Scanner(System.in);
    private static Facade facade=Facade.getInstance();

    public static void main(String[] args) {
        systemInitialize();
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
        return LocalDate.of(year,month,day);
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

    public static void systemInitialize(){
        BankAccount bankAccount1=new BankAccount("Bank Mizrahi-Tefahot","216","123456");
        BankAccount bankAccount2=new BankAccount("Bank Mizrahi-Tefahot","216","222222");
        BankAccount bankAccount3=new BankAccount("Bank Otsar Ha-Hayal","318","060000");
        BankAccount bankAccount4=new BankAccount("Bank Otsar Ha-Hayal","318","111222");
        BankAccount bankAccount5=new BankAccount("Bank Hapoalim","123","455446");
        BankAccount bankAccount6=new BankAccount("Bank Hapoalim","123","764309");
        BankAccount bankAccount7=new BankAccount("Bank Yahav","190","135262");
        BankAccount bankAccount8=new BankAccount("Bank Yahav","190","333444");
        BankAccount bankAccount9=new BankAccount("Bank Yahav","190","600200");
        BankAccount bankAccount10=new BankAccount("Bank Yahav","190","100200");
        BankAccount bankAccount11=new BankAccount("Bank Yahav","190","300111");
        BankAccount bankAccount12=new BankAccount("Bank Yahav","190","500432");
        BankAccount bankAccount13=new BankAccount("Bank Yahav","190","100243");
        BankAccount bankAccount14=new BankAccount("Bank Hapoalim","123","111897");
        BankAccount bankAccount15=new BankAccount("Bank Hapoalim","123","453672");
        BankAccount bankAccount16=new BankAccount("Bank Hapoalim","123","367829");
        BankAccount bankAccount17=new BankAccount("Bank Hapoalim","123","647389");
        BankAccount bankAccount18=new BankAccount("Bank Hapoalim","123","938476");
        BankAccount bankAccount19=new BankAccount("Bank Hapoalim","123","467378");
        BankAccount bankAccount20=new BankAccount("Bank Otsar Ha-Hayal","318","245673");
        BankAccount bankAccount21=new BankAccount("Bank Otsar Ha-Hayal","318","463782");
        BankAccount bankAccount22=new BankAccount("Bank Mizrahi-Tefahot","216","394873");
        BankAccount bankAccount23=new BankAccount("Bank Mizrahi-Tefahot","216","647383");
        BankAccount bankAccount24=new BankAccount("Bank Mizrahi-Tefahot","216","293847");
        BankAccount bankAccount25=new BankAccount("Bank Mizrahi-Tefahot","216","647380");

        LocalDate startDate1=LocalDate.of(2020,5,23);
        LocalDate startDate2=LocalDate.of(2021,1,15);
        LocalDate startDate3=LocalDate.of(2021,2,22);
        LocalDate startDate4=LocalDate.of(2019,3,17);
        LocalDate startDate5=LocalDate.of(2021,2,13);
        LocalDate startDate6=LocalDate.of(2018,4,2);
        LocalDate startDate7=LocalDate.of(2019,5,5);
        LocalDate startDate8=LocalDate.of(2015,9,28);
        LocalDate startDate9=LocalDate.of(2017,3,13);
        LocalDate startDate10=LocalDate.of(2020,4,2);
        LocalDate startDate11=LocalDate.of(2021,1,1);
        LocalDate startDate12=LocalDate.of(2018,4,2);

        HiringConditions hiringConditions1=new HiringConditions(10000,"Baillie Gifford American",10,5);
        HiringConditions hiringConditions2=new HiringConditions(12000,"Baillie Gifford American",7,3);
        HiringConditions hiringConditions3=new HiringConditions(8000,"Baillie Gifford American",12,7);
        HiringConditions hiringConditions4=new HiringConditions(10000,"Baillie Gifford American",6,6);
        HiringConditions hiringConditions5=new HiringConditions(15000,"Baillie Gifford American",15,7);
        HiringConditions hiringConditions6=new HiringConditions(5500,"Baillie Gifford American",8,6);
        HiringConditions hiringConditions7=new HiringConditions(7000,"Baillie Gifford American",9,4);
        HiringConditions hiringConditions8=new HiringConditions(18000,"Fundsmith Equity",6,5);
        HiringConditions hiringConditions9=new HiringConditions(9720,"Fundsmith Equity",7,3);
        HiringConditions hiringConditions10=new HiringConditions(13250,"Fundsmith Equity",12,4);
        HiringConditions hiringConditions11=new HiringConditions(2500,"Fundsmith Equity",6,6);
        HiringConditions hiringConditions12=new HiringConditions(3750,"Fundsmith Equity",9,7);
        HiringConditions hiringConditions13=new HiringConditions(7000,"Fundsmith Equity",8,6);
        HiringConditions hiringConditions14=new HiringConditions(10000,"Fundsmith Equity",9,4);
        HiringConditions hiringConditions15=new HiringConditions(8500,"Baillie Gifford Positive Change",6,6);
        HiringConditions hiringConditions16=new HiringConditions(20000,"Baillie Gifford Positive Change",14,4);
        HiringConditions hiringConditions17=new HiringConditions(25000,"Baillie Gifford Positive Change",5,6);
        HiringConditions hiringConditions18=new HiringConditions(30000,"Baillie Gifford Positive Change",25,7);
        HiringConditions hiringConditions19=new HiringConditions(2500,"Baillie Gifford China",9,7);
        HiringConditions hiringConditions20=new HiringConditions(9000,"Baillie Gifford China",8,6);
        HiringConditions hiringConditions21=new HiringConditions(15000,"Baillie Gifford China",9,4);
        HiringConditions hiringConditions22=new HiringConditions(7500,"Baillie Gifford China",6,6);
        HiringConditions hiringConditions23=new HiringConditions(25000,"Baillie Gifford China",14,4);
        HiringConditions hiringConditions24=new HiringConditions(12500,"Baillie Gifford China",5,6);
        HiringConditions hiringConditions25=new HiringConditions(15000,"Baillie Gifford China",25,7);

        Boolean favoriteShifts1[][]=new Boolean[][]{{true,true,false,true,false,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts2[][]=new Boolean[][]{{true,true,true,true,false,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts3[][]=new Boolean[][]{{true,false,false,true,false,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts4[][]=new Boolean[][]{{true,true,false,true,false,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts5[][]=new Boolean[][]{{true,true,false,true,true,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts6[][]=new Boolean[][]{{true,true,true,true,false,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts7[][]=new Boolean[][]{{true,true,false,true,false,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts8[][]=new Boolean[][]{{true,true,false,false,false,true,true},{true,true,false,true,false,true,true}};
        Boolean favoriteShifts9[][]=new Boolean[][]{{true,true,false,true,false,true,true},{true,true,true,true,false,true,true}};
        Boolean favoriteShifts10[][]=new Boolean[][]{{true,true,false,true,false,true,true},{true,false,false,true,false,true,true}};

        Boolean cantWork1[][]={{false,false,true,false,true,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork2[][]=new Boolean[][]{{false,false,false,false,true,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork3[][]=new Boolean[][]{{false,true,true,false,true,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork4[][]=new Boolean[][]{{false,false,true,false,true,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork5[][]=new Boolean[][]{{false,false,true,false,false,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork6[][]=new Boolean[][]{{false,false,false,false,true,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork7[][]=new Boolean[][]{{false,false,true,false,true,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork8[][]=new Boolean[][]{{false,false,true,true,true,false,false},{false,false,true,false,true,false,false}};
        Boolean cantWork9[][]=new Boolean[][]{{false,false,true,false,true,false,false},{false,false,false,false,true,false,false}};
        Boolean cantWork10[][]=new Boolean[][]{{false,false,true,false,true,false,false},{false,true,true,false,true,false,false}};

        AvailableWorkDays availableWorkDays1=new AvailableWorkDays(favoriteShifts1,cantWork1);
        AvailableWorkDays availableWorkDays2=new AvailableWorkDays(favoriteShifts2,cantWork2);
        AvailableWorkDays availableWorkDays3=new AvailableWorkDays(favoriteShifts3,cantWork3);
        AvailableWorkDays availableWorkDays4=new AvailableWorkDays(favoriteShifts4,cantWork4);
        AvailableWorkDays availableWorkDays5=new AvailableWorkDays(favoriteShifts5,cantWork5);
        AvailableWorkDays availableWorkDays6=new AvailableWorkDays(favoriteShifts6,cantWork6);
        AvailableWorkDays availableWorkDays7=new AvailableWorkDays(favoriteShifts7,cantWork7);
        AvailableWorkDays availableWorkDays8=new AvailableWorkDays(favoriteShifts8,cantWork8);
        AvailableWorkDays availableWorkDays9=new AvailableWorkDays(favoriteShifts9,cantWork9);
        AvailableWorkDays availableWorkDays10=new AvailableWorkDays(favoriteShifts10,cantWork10);

        List<Qualifications> qualifications1=new LinkedList<>();
        qualifications1.add(Qualifications.BranchManager);
        qualifications1.add(Qualifications.Assistant);
        qualifications1.add(Qualifications.Storekeeper);

        List<Qualifications> qualifications2=new LinkedList<>();
        qualifications2.add(Qualifications.BranchManager);
        qualifications2.add(Qualifications.Guard);
        qualifications2.add(Qualifications.Assistant);
        qualifications2.add(Qualifications.Storekeeper);

        List<Qualifications> qualifications3=new LinkedList<>();
        qualifications3.add(Qualifications.ShiftManager);
        qualifications3.add(Qualifications.Arranger);

        List<Qualifications> qualifications4=new LinkedList<>();
        qualifications4.add(Qualifications.Human_Resources_Director);
        qualifications4.add(Qualifications.Storekeeper);
        qualifications4.add(Qualifications.Guard);

        List<Qualifications> qualifications5=new LinkedList<>();
        qualifications5.add(Qualifications.Human_Resources_Director);
        qualifications5.add(Qualifications.Storekeeper);
        qualifications5.add(Qualifications.Guard);
        qualifications5.add(Qualifications.Assistant);

        List<Qualifications> qualifications6=new LinkedList<>();
        qualifications6.add(Qualifications.Human_Resources_Director);
        qualifications6.add(Qualifications.Storekeeper);
        qualifications6.add(Qualifications.Guard);
        qualifications6.add(Qualifications.Assistant);
        qualifications6.add(Qualifications.ShiftManager);

        Worker branchManager=new Worker("Yoad","Ohayon","323079103",bankAccount1,startDate1,hiringConditions1,availableWorkDays1,qualifications1);
        Worker HRD=new Worker("Omer","Shitrit","208060210",bankAccount2,startDate2,hiringConditions2,availableWorkDays2,qualifications5);
        Worker worker1=new Worker("Gal","Brown","207896321",bankAccount3,startDate3,hiringConditions3,availableWorkDays3,qualifications3);
        Worker worker2=new Worker("Daniel","Levi","209456234",bankAccount4,startDate4,hiringConditions4,availableWorkDays4,qualifications4);
        Worker worker3=new Worker("Shaked","Ohayon","209542302",bankAccount5,startDate4,hiringConditions5,availableWorkDays5,qualifications5);
        Worker worker4=new Worker("Yoav","Yehuda","323456123",bankAccount6,startDate5,hiringConditions6,availableWorkDays6,qualifications6);
        Worker worker5=new Worker("Ehud","Shamgar","254987413",bankAccount7,startDate6,hiringConditions7,availableWorkDays7,qualifications4);
        Worker worker6=new Worker("Yossi","Bookobza","209456534",bankAccount8,startDate7,hiringConditions8,availableWorkDays8,qualifications3);
        Worker worker7=new Worker("Matan","Sabag","206342302",bankAccount9,startDate8,hiringConditions9,availableWorkDays9,qualifications1);
        Worker worker8=new Worker("Avishy","Yehuda","320842543",bankAccount10,startDate9,hiringConditions10,availableWorkDays10,qualifications2);
        Worker worker9=new Worker("Dani","Lerner","206894441",bankAccount11,startDate10,hiringConditions11,availableWorkDays6,qualifications4);
        Worker worker10=new Worker("Raz","Cohen","229656234",bankAccount12,startDate11,hiringConditions12,availableWorkDays8,qualifications1);
        Worker worker11=new Worker("Eren","Ben David","209509970",bankAccount13,startDate12,hiringConditions13,availableWorkDays4,qualifications5);
        Worker worker12=new Worker("Or","Efraim","326556129",bankAccount14,startDate1,hiringConditions14,availableWorkDays1,qualifications6);
        Worker worker13=new Worker("Bar","Zomer","332545111",bankAccount15,startDate2,hiringConditions15,availableWorkDays2,qualifications2);
        Worker worker14=new Worker("Ido","Barak","289774534",bankAccount16,startDate3,hiringConditions16,availableWorkDays5,qualifications3);
        Worker worker15=new Worker("Daniel","maymon","339342302",bankAccount17,startDate8,hiringConditions17,availableWorkDays9,qualifications5);
        Worker worker16=new Worker("Kobi","Swissa","290842543",bankAccount18,startDate5,hiringConditions18,availableWorkDays6,qualifications6);
        Worker worker17=new Worker("Ben","Cohen","333232987",bankAccount19,startDate6,hiringConditions19,availableWorkDays3,qualifications1);
        Worker worker18=new Worker("Baruch","Tzion","279587570",bankAccount20,startDate12,hiringConditions20,availableWorkDays4,qualifications4);
        Worker worker19=new Worker("Moses","Efraim","321642111",bankAccount21,startDate7,hiringConditions21,availableWorkDays1,qualifications2);
        Worker worker20=new Worker("Shalom","Brefman","205123654",bankAccount22,startDate2,hiringConditions22,availableWorkDays2,qualifications5);
        Worker worker21=new Worker("Barak","Bar","226134562",bankAccount23,startDate3,hiringConditions23,availableWorkDays5,qualifications3);
        Worker worker22=new Worker("Avi","Ohayon","325431754",bankAccount24,startDate8,hiringConditions24,availableWorkDays9,qualifications5);
        Worker worker23=new Worker("Dganit","Refeli","298764234",bankAccount25,startDate5,hiringConditions25,availableWorkDays6,qualifications6);

        facade.addBranch(1,branchManager,HRD);
        facade.addWorker(worker1,1);
        facade.addWorker(worker2,1);
        facade.addWorker(worker3,1);
        facade.addWorker(worker4,1);
        facade.addWorker(worker5,1);
        facade.addWorker(worker6,1);
        facade.addWorker(worker7,1);
        facade.addWorker(worker8,1);
        facade.addWorker(worker9,1);
        facade.addWorker(worker10,1);
        facade.addWorker(worker11,1);
        facade.addWorker(worker12,1);
        facade.addWorker(worker13,1);
        facade.addWorker(worker14,1);
        facade.addWorker(worker15,1);
        facade.addWorker(worker16,1);
        facade.addWorker(worker17,1);
        facade.addWorker(worker18,1);
        facade.addWorker(worker19,1);
        facade.addWorker(worker20,1);
        facade.addWorker(worker21,1);
        facade.addWorker(worker22,1);
        facade.addWorker(worker23,1);

        facade.showWorkers(1);


    }

    public static  Facade createBasicFacade(){
        return Facade.getInstance();
    }

    public  static AvailableWorkDays createAvailableWorkDays(){
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
