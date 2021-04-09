package PresentationLayer;

import BuisnessLayer.*;
import com.sun.xml.internal.bind.v2.model.core.ID;


import java.time.LocalDate;
import java.util.*;

import static PresentationLayer.Main.createDate;
import static PresentationLayer.Main.systemInitialize;

public class Menu {

    private static final Facade facade = Facade.getInstance();
    private static final Scanner reader = new Scanner(System.in);

    private static void createWeeklyAssignment(WorkerDTO branchManger) {
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();
        facade.isLegalBranch(branchID);
        System.out.println("Please enter the date of sunday in order to create shift assignment for this week");
        LocalDate date_ = createDate();
        ShiftDemandsDTO[][] sd;
       /* if((facade.getShiftDemandsDTO(branchID, date_))==null)
            sd = null;
        else{
            sd = new ShiftDemandsDTO[7][2];
        for(int i=0 ; i< 7 ; i++){
            for(int j=0 ; j< 2 ; j++){
                sd[i][j] = facade.getShiftDemandsDTO(branchID,date_)[i][j];
            }
          }*/
        facade.createWeeklyAssignment(branchID, date_, branchManger);
    }


    private static ShiftTypeDTO createShiftType(String type) {
        if (type.equals("M") || type.equals("m"))
            return ShiftTypeDTO.Morning;
        else if (type.equals("E") || type.equals("e"))
            return ShiftTypeDTO.Evening;
        else return null;
    }

    private static void replaceAShiftBetweenTwoWorkers(WorkerDTO workerDTO) {
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();
        facade.isLegalBranch(branchID);
        System.out.println("Please enter the Date of the first shift");
        LocalDate date1 = createDate();
        System.out.println("Please enter the type of the first shift for morning press M and for evening press E");
        String type1 = reader.next();
        ShiftTypeDTO shiftTypeDTO1 = createShiftType(type1);
        ;
        if (shiftTypeDTO1 == null) {
            do {
                System.out.println("The type was incorrect Please enter again the type of the first shift for morning press M and for evening press E");
                type1 = reader.next();
                shiftTypeDTO1 = createShiftType(type1);

            } while (shiftTypeDTO1 == null);
        }

        System.out.println("Please enter the Date of the second shift");
        LocalDate date2 = createDate();
        System.out.println("Please enter the type of the second shift for morning press M and for evening press E");
        String type2 = reader.next();
        ShiftTypeDTO shiftTypeDTO2 = createShiftType(type2);

        if (shiftTypeDTO2 == null) {
            do {
                System.out.println("The type was incorrect Please enter again the type of the second shift for morning press M and for evening press E");
                type2 = reader.next();
                shiftTypeDTO2 = createShiftType(type2);

            } while (shiftTypeDTO2 == null);
        }

        System.out.println("The workers in the first shift are: ");
        facade.printWorkersAtShift(branchID, date1, shiftTypeDTO1);
        int worker1SerialNumber, worker2SerialNumber;
        System.out.println("Enter the worker's serial number you want to replace");
        worker1SerialNumber = reader.nextInt();

        System.out.println("Workers at second shift:");
        facade.printWorkersAtShift(branchID, date2, shiftTypeDTO2);
        System.out.println("Enter the worker's serial number you want to replace");
        worker2SerialNumber = reader.nextInt();

        WorkerDTO workerDTO1 = facade.findWorkerBySerialNumber(branchID, worker1SerialNumber - 1);
        WorkerDTO workerDTO2 = facade.findWorkerBySerialNumber(branchID, worker2SerialNumber - 1);
        facade.workerReplacement(branchID, date1, shiftTypeDTO1, date2, shiftTypeDTO2, workerDTO1, workerDTO2, workerDTO);

    }

    private static void displayCurrentDayShift() {
        ShiftTypeDTO morning = ShiftTypeDTO.Morning;
        ShiftTypeDTO evening = ShiftTypeDTO.Evening;
        System.out.println("Please enter the branch ID");
        int bID = reader.nextInt();
        facade.isLegalBranch(bID);
        facade.printWorkersAtShift(bID, LocalDate.now(), morning);
        facade.printWorkersAtShift(bID, LocalDate.now(), evening);
    }

    private static void displayShiftAssignment() {
        System.out.println("Please enter the date of the shift");
        LocalDate shiftDate = createDate();
        System.out.println("Please enter the type of the first shift for morning press M and for evening press E");
        String type = reader.next();
        ShiftTypeDTO shiftTypeDTO = createShiftType(type);
        ;
        if (shiftTypeDTO == null) {
            do {
                System.out.println("The type was incorrect Please enter again the type of the first shift for morning press M and for evening press E");
                type = reader.next();
                shiftTypeDTO = createShiftType(type);

            } while (shiftTypeDTO == null);
        }
        System.out.println("Please enter the branch ID");
        int b = reader.nextInt();
        facade.isLegalBranch(b);
        facade.printWorkersAtShift(b, shiftDate, shiftTypeDTO);
    }

    private static void searchWorker() {
        System.out.println("Please enter the ID of the worker");
        String IDForPrint = reader.next();
        facade.isLegalWorker(IDForPrint);
        facade.printWorker(facade.findDTOWorkerByID(IDForPrint));
    }

    private static void workersByQualification() {
        System.out.println("Please enter the branch ID");
        int brID = reader.nextInt();
        facade.isLegalBranch(brID);
        System.out.println("Please enter the date of the shift");
        LocalDate ld = createDate();
        System.out.println("Please enter the type of the shift for morning press M and for evening press E");
        String s = reader.next();
        ShiftTypeDTO st = createShiftType(s);
        ShiftDTO sh = facade.getShift(brID, ld, st);
        System.out.println("Please choose qualification number");
        System.out.println("1) Cashier ");
        System.out.println("2) Storekeeper");
        System.out.println("3) Arranger");
        System.out.println("4) Guard");
        System.out.println("5) Assistant");
        System.out.println("6) ShiftManager");
        int q = reader.nextInt();
        QualificationsDTO qua = null;
        switch (q) {
            case 1:
                qua = QualificationsDTO.Cashier;
                break;
            case 2:
                qua = QualificationsDTO.Storekeeper;
                break;
            case 3:
                qua = QualificationsDTO.Arranger;
                break;
            case 4:
                qua = QualificationsDTO.Guard;
                break;
            case 5:
                qua = QualificationsDTO.Assistant;
                break;
            case 6:
                System.out.println("The ShiftManager in the shift is:" + facade.getShiftManager(sh).getFirstName() + " " + facade.getShiftManager(sh).getFirstName());
                break;
        }
        if (q != 6 && sh != null)
            facade.printWorkersByQualification(qua, sh);
    }

    private static void displayWeeklyAssignment() {
        System.out.println("Please enter the date of sunday in order to see the weekly shift assignment");
        LocalDate date = createDate();
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();
        facade.isLegalBranch(branchID);
        facade.printWeeklyAssignment(branchID, date);
    }


    private static void personalDetailsMenu(String ID) {
        while (true) {
            System.out.println("\n Please enter a number between 1-4 in order to choose which information you want to see");
            System.out.println("1) Display your hiring conditions");
            System.out.println("2) Display your qualifications");
            System.out.println("3) Display your start working date");
            System.out.println("4) Display your bank account");

            System.out.println("For exit the Personal Details menu press 0");
            int menu = reader.nextInt();
            if (menu == 0) break;

            WorkerDTO workerDTO = facade.findDTOWorkerByID(ID);

            while (menu < 1 || menu > 4) {
                System.out.println("please enter a number between 1-4");
                System.out.println("For exit the Personal Details menu press 0");
                menu = reader.nextInt();
                if (menu == 0) break;
            }

            switch (menu) {
                case 1:
                    System.out.println("your salary per hour is: " + workerDTO.getHiringConditions().getSalaryPerHour());
                    System.out.println("your reminding sick days are : " + workerDTO.getHiringConditions().getSickLeavePerMonth());
                    System.out.println("your reminding vacations days are: " + workerDTO.getHiringConditions().getVacationDays());
                    System.out.println("your fund is: " + workerDTO.getHiringConditions().getFund());
                    break;

                case 2:
                    System.out.println("your qualifications are : " + workerDTO.getQualifications().toString());
                    break;
                case 3:
                    System.out.println("your start working day is : " + workerDTO.getStartWorkingDay());
                    break;
                case 4:
                    System.out.println("your bank account is : " + workerDTO.getBankAccount().toString());
                    break;
            }
        }
    }


    public static void branchManagerMenu(WorkerDTO branchManager, int branchID) {
        while (true) {
            System.out.println("\n Welcome " + branchManager.getFirstName() + " " + branchManager.getLastName() + ", the branch manager of branch " + branchID + " please choose an action");
            System.out.println("1) Create new weekly assignment by the date of sunday");
            System.out.println("2) Replace a shift between two workers");
            System.out.println("3) Display current day shift assignment by branch ID");
            System.out.println("4) Display shift assignment by date and by branch ID");
            System.out.println("5) Search worker by worker ID");
            System.out.println("6) Display workers by Qualification and by branch ID");
            System.out.println("7) Display weekly assignment by branchID and by date");
            System.out.println("8) Display personal details of a worker by his ID");
            System.out.println("9) Display your personal details \n");


            System.out.println("For exit press 0");
            int menu = reader.nextInt();

            while (menu < 1 || menu > 9) {
                System.out.println("please enter a number between 1-9");
                System.out.println("For exit press 0");
                menu = reader.nextInt();
            }

            switch (menu) {
                case 1:
                    createWeeklyAssignment(branchManager);
                    break;

                case 2:
                    replaceAShiftBetweenTwoWorkers(branchManager);
                    break;
                case 3:
                    displayCurrentDayShift();
                    break;
                case 4:
                    displayShiftAssignment();
                    break;
                case 5:
                    searchWorker();
                    break;
                case 6:
                    workersByQualification();
                    break;
                case 7:
                    displayWeeklyAssignment();
                    break;
                case 8:
                    System.out.println("Please enter worker ID");
                    String ID = reader.next();
                    facade.isLegalWorker(ID);
                    displayWorkerPersonalDetails();
                    break;
                case 9:
                    personalDetailsMenu(branchManager.getID());
                    break;

            }
        }
    }

    private static void displayWorkerPersonalDetails() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        facade.isLegalWorker(ID);
        WorkerDTO workerDTO = facade.findDTOWorkerByID(ID);
        System.out.println("The worker chosen is " + workerDTO.getFirstName() + " " + workerDTO.getLastName() + " ");

        System.out.println("Please enter a number between 1-4 in order to choose which information you want to see");
        System.out.println("1) Display the hiring conditions of chosen worker");
        System.out.println("2) Display the qualifications of chosen worker");
        System.out.println("3) Display start working date of chosen worker");
        System.out.println("4) Display the bank account of chosen worker");


        int menu = reader.nextInt();
        while (menu < 1 || menu > 4) {
            System.out.println("please enter a number between 1-4");
            menu = reader.nextInt();
        }


        switch (menu) {
            case 1:
                System.out.println("Hiring conditions: " + workerDTO.getHiringConditions().getSalaryPerHour());
                System.out.println("Remaining sick days : " + workerDTO.getHiringConditions().getSickLeavePerMonth());
                System.out.println("Remaining vacations days : " + workerDTO.getHiringConditions().getVacationDays());
                System.out.println("The fund : " + workerDTO.getHiringConditions().getFund());
                break;

            case 2:
                System.out.println("Qualifications : " + workerDTO.getQualifications().toString());
                break;
            case 3:
                System.out.println("Start working day : " + workerDTO.getStartWorkingDay());
                break;
            case 4:
                System.out.println("Bank account : " + workerDTO.getBankAccount().toString());
                break;
        }
    }


    public static void HRDMenu(WorkerDTO HRD, int branchID) {
        while (true) {
            System.out.println("\n Welcome " + HRD.getFirstName() + " " + HRD.getLastName() + ", the HRD of branch " + branchID + " please choose an action:");
            System.out.println("1) Create new weekly shifts demands by branch ID,the date of sunday");
            System.out.println("2) Add new worker");
            System.out.println("3) Remove worker");
            System.out.println("4) Change worker branch");
            System.out.println("5) Display current day shift assignment by branch ID");
            System.out.println("6) Display shift assignment by date and by branch ID");
            System.out.println("7) Search worker by Branch ID and by  worker ID");
            System.out.println("8) Display workers by Qualification and by branch ID");
            System.out.println("9) Display weekly assignment by branchID and by date");
            System.out.println("10) Update worker's details");
            System.out.println("11) Display your personal details ");
            System.out.println("12) Add new branch");
            System.out.println("13) Add new qualifications by worker ID (deleting the former qualifications)");
            System.out.println("14) Display personal details of a worker by his ID\n");

            System.out.println("For exit press 0");
            int menu = reader.nextInt();
            if (menu == 0) break;
            while (menu < 1 || menu > 14) {
                System.out.println("please enter a number between 1-14");
                System.out.println("For exit press 0");
                if (menu == 0) break;
                menu = reader.nextInt();
            }

            switch (menu) {
                case 1:
                    createWeeklyShiftDemands();
                    break;

                case 2:
                    addWorker();
                    break;
                case 3:
                    removeWorker();
                    break;
                case 4:
                    changeWorkerBranch();
                    break;
                case 5:
                    displayCurrentDayShift();
                    break;
                case 6:
                    displayShiftAssignment();
                    break;
                case 7:
                    searchWorker();
                    break;
                case 8:
                    workersByQualification();
                    break;
                case 9:
                    displayWeeklyAssignment();
                    break;
                case 10:
                    updateWorkersDetails();
                    break;
                case 11:
                    personalDetailsMenu(HRD.getID());
                    break;
                case 12:
                    addNewBranch();
                    break;
                case 13:
                    addQualification();
                    break;
                case 14:
                    displayWorkerPersonalDetails();
                    break;
            }
        }
    }

    private static void addQualification() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        facade.isLegalWorker(ID);
        System.out.println("The former qualifications of this worker are: ");
        System.out.println(facade.findDTOWorkerByID(ID).getQualifications().toString());
        List<QualificationsDTO> q = createQualifications();
        for (QualificationsDTO qu : q)
            facade.addQualification(ID, qu);

    }


    public static void otherWorkerMenu(WorkerDTO workerDTO, int branchID) {
        while (true) {
            System.out.println("\nWelcome " + workerDTO.getFirstName() + " " + workerDTO.getLastName() + " please choose an action");
            System.out.println("1) Create new weekly Available Work days by the date of sunday");
            System.out.println("2) Display current day shift assignment of your branch");
            System.out.println("3) Display the daily shift assignment of your branch by date");
            System.out.println("4) Display weekly assignment by branchID and by date");
            System.out.println("5) Display your personal details \n");

            System.out.println("For exit press 0");
            int menu = reader.nextInt();
            if (menu == 0) break;

            while (menu < 1 || menu > 5) {
                System.out.println("please enter a number between 1-5");
                System.out.println("For exit press 0");
                if (menu == 0) break;
                menu = reader.nextInt();
            }

            switch (menu) {
                case 1:
                    AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays();
                    facade.setAvailableWorkDays(branchID, workerDTO, availableWorkDaysDTO);
                    break;
                case 2:
                    displayCurrentDayShift();
                    break;
                case 3:
                    displayShiftAssignment();
                    break;
                case 4:
                    displayWeeklyAssignment();
                    break;
                case 5:
                    personalDetailsMenu(workerDTO.getID());
                    break;
            }
        }
    }

    private static void createWeeklyShiftDemands() {
        System.out.println("Please enter the branch ID");
        int brID = reader.nextInt();
        facade.isLegalBranch(brID);
        facade.resetShiftDemands(brID);
        System.out.println("Please enter the date of the first shift on sunday");
        LocalDate date = createDate();
        ShiftTypeDTO shiftTypeDTO;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    shiftTypeDTO = ShiftTypeDTO.Morning;
                    System.out.println("enter morning shift of " + date);
                } else {
                    System.out.println("enter evening shift of " + date);
                    shiftTypeDTO = ShiftTypeDTO.Evening;
                }
                createShiftDemands(date, brID, shiftTypeDTO);
            }
            date = date.plusDays(1);
        }
    }

    private static void createShiftDemands(LocalDate date, int brID, ShiftTypeDTO shiftTypeDTO) {

        int cashiers, storeKeepers, arrangers, guards, assistants;
        System.out.println("Please enter cashiers amount");
        cashiers = reader.nextInt();
        System.out.println("Please enter storeKeepers amount");
        storeKeepers = reader.nextInt();
        System.out.println("Please enter arrangers amount");
        arrangers = reader.nextInt();
        System.out.println("Please enter guards amount");
        guards = reader.nextInt();
        System.out.println("Please enter assistants amount");
        assistants = reader.nextInt();
        ShiftDemandsDTO shiftDemandsDTO = new ShiftDemandsDTO(date, cashiers, storeKeepers, arrangers, guards, assistants);
        facade.addShiftDemands(brID, date, shiftTypeDTO, shiftDemandsDTO);
    }

    private static void addWorker() {
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();
        facade.isLegalBranch(branchID);
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        facade.isLegalWorker(ID);
        WorkerDTO workerDTO = facade.findDTOWorkerByID(ID);
        if (workerDTO != null) {
            if (facade.findBranchByWorker(workerDTO).getBranchID() != branchID) {
                facade.removeWorker(workerDTO, facade.findBranchByWorker(workerDTO).getBranchID());
                facade.addWorker(workerDTO, branchID);
            } else System.out.println("The worker is already exist in this branch");
        } else {
            createWorker(ID, branchID);
        }
    }

    private static void addNewBranch() {
        WorkerDTO branchManager = null, branchHRD = null;
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();
        if (facade.getBranch(branchID) != null)
            throw new IllegalArgumentException("The branch " + branchID + "is already exist in the system");
        System.out.println("Do you want to promote an existing worker in another branch to be this new branch manager?");
        System.out.println("Enter Y/N");
        String ans = reader.next();
        while (ans != "n" || ans != "N" || ans != "y" || ans != "Y") {
            System.out.println("Please enter Y for Yes or N for No");
            ans = reader.next();
        }
        if (ans.equals("n") || ans.equals("N")) {
            System.out.println("Please enter new branch manager");
            branchManager = createWorkerWithoutAdd();
        } else {
            System.out.println("Please enter ID of existing worker");
            String ID = reader.next();
            facade.isLegalWorker(ID);
            if (facade.findDTOWorkerByID(ID) == null)
                System.out.println("There is no such worker in the System");
            else {
                if (facade.isAManager(ID))
                    System.out.println("The worker: " + ID + " is already a manager");
                else {
                    facade.addQualification(ID, QualificationsDTO.BranchManager);
                }
            }

        }

        System.out.println("Do you want to chose an existing worker that already qualified to be HRD to be this new branch HRD?");
        System.out.println("Enter Y/N");
        ans = reader.next();
        while (ans != "n" || ans != "N" || ans != "y" || ans != "Y") {
            System.out.println("Please enter Y for Yes or N for No");
            ans = reader.next();
        }
        if (ans.equals("n") || ans.equals("N")) {
            System.out.println("Please enter new branch HRD");
            branchHRD = createWorkerWithoutAdd();
        } else {
            System.out.println("Please enter ID of existing worker");
            String ID = reader.next();
            facade.isLegalWorker(ID);
            if (facade.findDTOWorkerByID(ID) == null)
                System.out.println("There is no such worker in the System");
            else {
                if (!facade.isHRD(ID))
                    System.out.println("The worker: " + ID + " is not qualified of being HRD");

                else branchHRD = facade.findDTOWorkerByID(ID);
            }
        }
        if (branchHRD != null && branchManager != null)
            facade.addBranch(branchID, branchManager, branchHRD);

        else System.out.println("The branch was not created due to illegal branch manager or HRD");
    }


    public static void createWorker(String ID, int branchID) {
        String firstname, lastname;
        System.out.println("Enter worker first name:");
        firstname = reader.next();
        System.out.println("Enter worker last name:");
        lastname = reader.next();
        BankAccountDTO bankAccountDTO = createBankAccount();
        LocalDate startWorkingDay = createDate();
        HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
        AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays();
        List<QualificationsDTO> qualifications = createQualifications();
        WorkerDTO newWorkerDTO = new WorkerDTO(firstname, lastname, ID, bankAccountDTO, startWorkingDay, hiringConditionsDTO, availableWorkDaysDTO, qualifications);
        facade.addWorker(newWorkerDTO, branchID);
    }

    public static WorkerDTO createWorkerWithoutAdd() {
        String firstname, lastname;
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        facade.isExistingWorker(ID);
        System.out.println("Enter worker first name:");
        firstname = reader.next();
        System.out.println("Enter worker last name:");
        lastname = reader.next();
        BankAccountDTO bankAccountDTO = createBankAccount();
        LocalDate startWorkingDay = createDate();
        HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
        AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays();
        List<QualificationsDTO> qualifications = createQualifications();
        WorkerDTO newWorkerDTO = new WorkerDTO(firstname, lastname, ID, bankAccountDTO, startWorkingDay, hiringConditionsDTO, availableWorkDaysDTO, qualifications);
        return newWorkerDTO;
    }

    public static BankAccountDTO createBankAccount() {
        String bankName, branch, bankAccount;
        System.out.println("Enter bank name");
        bankName = reader.next();
        System.out.println("Enter bank branch number");
        branch = reader.next();
        System.out.println("Enter bank account number");
        bankAccount = reader.next();
        return new BankAccountDTO(bankName, branch, bankAccount);
    }

    public static AvailableWorkDaysDTO createAvailableWorkDays() {
        Boolean[][] available = new Boolean[7][2];
        HashMap<Integer, String> week = new HashMap<Integer, String>();
        week.put(0, "sunday");
        week.put(1, "monday");
        week.put(2, "tuesday");
        week.put(3, "wednesday");
        week.put(4, "thursday");
        week.put(5, "friday");
        week.put(6, "saturday");
        String can;
        String shift;
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++) {
                if (j == 0)
                    shift = "Morning";
                else shift = "Evening";
                System.out.println("Can you work at day " + week.get(i) + "in shift " + shift + "? Enter Y/N");
                can = reader.next();
                while (!(can.equals("Y") || can.equals("y") || can.equals("N") || can.equals("n")))
                    can = reader.next();
                if (can.equals("Y") || can.equals("y"))
                    available[i][j] = true;
                else available[i][j] = false;

            }

        Boolean[][] favorite = new Boolean[7][2];
        String prefer;
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 2; j++) {
                if (j == 0)
                    shift = "Morning";
                else shift = "Evening";
                System.out.println("Do you prefer to work at day " + week.get(i) + "in shift " + shift + "? Enter Y/N");
                prefer = reader.next();
                while (!(prefer.equals("Y") || prefer.equals("y") || prefer.equals("N") || prefer.equals("n")))
                    prefer = reader.next();
                if (prefer.equals("Y") || prefer.equals("y"))
                    favorite[i][j] = true;
                else favorite[i][j] = false;

            }
        AvailableWorkDaysDTO availableWorkDaysDTO = new AvailableWorkDaysDTO(favorite, available);
        return availableWorkDaysDTO;


    }

    public static HiringConditionsDTO createHiringConditions() {
        double salary;
        String fund;
        int vacationDays, sickLeavePerMonth;
        System.out.println("Enter salary per hour:");
        salary = reader.nextDouble();
        System.out.println("Enter fund:");
        fund = reader.next();
        System.out.println("Enter the amount of vacation days per year");
        vacationDays = reader.nextInt();
        System.out.println("Enter the number of sick days per month");
        sickLeavePerMonth = reader.nextInt();
        return new HiringConditionsDTO(salary, fund, vacationDays, sickLeavePerMonth);
    }

    public static List<QualificationsDTO> createQualifications() {
        char c;
        List<QualificationsDTO> qualifications = new LinkedList<>();
        for (QualificationsDTO q : QualificationsDTO.values()) {
            do {
                System.out.println("Do the worker is talented to work as " + q.name() + " Enter Y/N");
                c = reader.next().charAt(0);
            } while (c != 'Y' && c != 'y' && c != 'n' && c != 'N');
            if (c == 'Y' || c == 'y')
                qualifications.add(q);
        }
        return qualifications;
    }

    private static void removeWorker() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        facade.isLegalWorker(ID);
        WorkerDTO workerDTOToRemove = facade.findDTOWorkerByID(ID);
        facade.removeWorker(workerDTOToRemove, facade.findBranchByWorker(workerDTOToRemove).getBranchID());
        System.out.println("Removal succeeded");
    }

    private static void changeWorkerBranch() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        facade.isLegalWorker(ID);
        System.out.println("Please enter new branch ID");
        int newID = reader.nextInt();
        facade.isLegalBranch(newID);
        WorkerDTO workerDTO = facade.findDTOWorkerByID(ID);
        if (newID == facade.findBranchByWorker(workerDTO).getBranchID())
            System.out.println("The worker is already work in this branch");
        else {
            facade.removeWorker(workerDTO, facade.findBranchByWorker(workerDTO).getBranchID());
            facade.addWorker(workerDTO, newID);
        }
    }

    private static void updateWorkersDetails() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        facade.isLegalWorker(ID);
        System.out.println("Please choose which detail you want to change, if you finish changing press 0");
        int option = reader.nextInt();
        while (option != 0) {
            System.out.println("1) Reset worker's first name");
            System.out.println("2) Reset worker's last name");
            System.out.println("3) Reset worker's bankAccount");
            System.out.println("4) Reset worker's hiringConditions");
            System.out.println("5) Reset worker's qualifications");

            WorkerDTO workerDTO = facade.findDTOWorkerByID(ID);

            switch (option) {
                case 1:
                    System.out.println("The first name of the worker was: " + facade.getWorkerFirstName(ID));
                    System.out.println("Enter the new first name of the worker");
                    String firstName = reader.next();
                    facade.setWorkerFirstName(firstName, ID);
                case 2:
                    System.out.println("The Last name of the worker was: " + facade.getWorkerLastName(ID));
                    System.out.println("Enter the new last name of the worker");
                    String lastName = reader.next();
                    facade.setWorkerLastName(lastName, ID);
                case 3:
                    System.out.println("The Last bank account of the worker was: " + facade.getWorkerBankAccount(ID));
                    BankAccountDTO bankAccountDTO = createBankAccount();
                    facade.setBankAccount(bankAccountDTO, ID);
                case 4:
                    System.out.println("The Last hiring conditions of the worker was: " + facade.getWorkerHiringConditions(ID));
                    HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
                    facade.setHiringConditions(hiringConditionsDTO, ID);
                case 5:
                    System.out.println("The Last qualifications of the worker were: " + facade.getWorkerQualifications(workerDTO));
                    List<QualificationsDTO> qualifications = createQualifications();
                    facade.setWorkerQualifications(qualifications, ID);
            }
        }
    }

    public static void menuForCreate() {
        while (true) {
            System.out.println("\n Please choose an action, remember the system is not initialized yet so you might want to add new branches and workers");
            System.out.println("1) Create new weekly shifts demands by branch ID,the date of sunday");
            System.out.println("2) Add new worker");
            System.out.println("3) Remove worker");
            System.out.println("4) Change worker branch");
            System.out.println("5) Display current day shift assignment by branch ID");
            System.out.println("6) Display shift assignment by date and by branch ID");
            System.out.println("7) Search worker by Branch ID and by  worker ID");
            System.out.println("8) Display workers by Qualification and by branch ID");
            System.out.println("9) Display weekly assignment by branchID and by date");
            System.out.println("10) Update worker's details");
            System.out.println("11) Display personal details of a worker by his ID ");
            System.out.println("12) Add new branch");
            System.out.println("13) Add new qualifications by worker ID (deleting the former qualifications)\n");

            System.out.println("For exit press 0 and for logging the system as a worker press -1");
            int menu = reader.nextInt();
            if (menu == 0) break;
            else if(menu == -1)
                 system();
            while (menu < 1 || menu > 13) {
                System.out.println("please enter a number between 1-13");
                System.out.println("For exit press 0");
                if (menu == 0) break;
                menu = reader.nextInt();
            }

            switch (menu) {
                case 1:
                    createWeeklyShiftDemands();
                    break;

                case 2:
                    addWorker();
                    break;
                case 3:
                    removeWorker();
                    break;
                case 4:
                    changeWorkerBranch();
                    break;
                case 5:
                    displayCurrentDayShift();
                    break;
                case 6:
                    displayShiftAssignment();
                    break;
                case 7:
                    searchWorker();
                    break;
                case 8:
                    workersByQualification();
                    break;
                case 9:
                    displayWeeklyAssignment();
                    break;
                case 10:
                    updateWorkersDetails();
                    break;
                case 11:
                    displayWorkerPersonalDetails();
                    break;
                case 12:
                    addNewBranch();
                    break;
                case 13:
                    addQualification();
                    break;
            }
        }
    }

    private static void system(){
        System.out.println(LocalDate.now());
        System.out.println("Welcome to Super Lee's System, please enter your ID in order to log in");
        String ID = reader.next();
        WorkerDTO workerDTO = facade.findDTOWorkerByID(ID);
        while (workerDTO == null) {
            System.out.println("There is no worker with such ID please enter new ID");
            ID = reader.next();
            workerDTO = facade.findDTOWorkerByID(ID);
        }
        int branchID = facade.findBranchByWorker(workerDTO).getBranchID();
        List<QualificationsDTO> qualifications = facade.getWorkerQualifications(workerDTO);
        if (qualifications.contains(QualificationsDTO.BranchManager)) {
            Menu.branchManagerMenu(workerDTO,branchID);

        } else if (workerDTO.getQualifications().contains(QualificationsDTO.Human_Resources_Director)) {
            Menu.HRDMenu(workerDTO,branchID);

        }
        else {
            Menu.otherWorkerMenu(workerDTO,branchID);
        }

    }
}




