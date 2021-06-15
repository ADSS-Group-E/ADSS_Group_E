package PresentationLayer.Workers.OptionMenus;

import BusinessLayer.Workers_Transport.Facade;
import BusinessLayer.Workers_Transport.Response;
import BusinessLayer.Workers_Transport.ResponseT;
import DataAccessLayer.Workers_Transport.Transports.DTO;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Workers.DataTransferObjects.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


public class MainWorkersOptionsMenu extends OptionsMenu {

    private final Facade facade;

    public MainWorkersOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        facade = parentCLI.getWorkersTransportFacade();

        int i=1;
        // Branch Manager
        options.put(i++, new Option( "Create new weekly assignment by the date of sunday", this::createWeeklyAssignment, QualificationsDTO.BranchManager));
        options.put(i++, new Option( "Replace a shift between two workers", this::replaceAShiftBetweenTwoWorkers, QualificationsDTO.BranchManager));
        options.put(i++, new Option( "Display shift assignment by date and by branch ID", this::displayShiftAssignment, QualificationsDTO.BranchManager));


        //HRD
        options.put(i++, new Option( "Create new weekly shifts demands by branch ID,the date of sunday", this::createWeeklyShiftDemands, QualificationsDTO.Human_Resources_Director));
        options.put(i++, new Option( "Add new worker", this::addWorker, QualificationsDTO.Human_Resources_Director));
        options.put(i++, new Option( "Remove worker", this::removeWorker, QualificationsDTO.Human_Resources_Director));
        options.put(i++, new Option( "Change worker branch", this::changeWorkerBranch, QualificationsDTO.Human_Resources_Director));
        options.put(i++, new Option( "Update worker's details", this::updateWorkersDetails, QualificationsDTO.Human_Resources_Director));
        options.put(i++, new Option( "Add new branch", this::addNewBranch, QualificationsDTO.Human_Resources_Director));
        options.put(i++, new Option( "Add new qualifications by worker ID", this::addQualification, QualificationsDTO.Human_Resources_Director));


        HashSet<QualificationsDTO> hrdAndBranchManager = new HashSet<>();
        hrdAndBranchManager.add(QualificationsDTO.BranchManager);
        hrdAndBranchManager.add(QualificationsDTO.Human_Resources_Director);
        // Branch Manager + HRD
        options.put(i++, new Option( "Display current day shift assignment by branch ID", this::displayCurrentDayShift, hrdAndBranchManager));
        options.put(i++, new Option( "Display workers by Qualification, shift and by branch ID", this::workersByQualification, hrdAndBranchManager));
        options.put(i++, new Option( "Display weekly assignment by branchID and by date", this::displayWeeklyAssignment, hrdAndBranchManager));
        options.put(i++, new Option( "Search worker by worker ID", this::searchWorker, hrdAndBranchManager));
        options.put(i++, new Option( "Display personal details of a worker by his ID", this::displayWorkerPersonalDetails, hrdAndBranchManager));


        //Everyone
        options.put(i++, new Option( "Display your personal details", this::displayPersonalDetails));
        options.put(i++, new Option( "Display workers by branch ID", this::displayWorkersByBranchID));

        options.put(i++, new Option( "Back", this::back));
    }

    public LocalDate createDate() {
        int day, month, year;
        System.out.println("Enter the day in month:");
        day = in.nextInt();
        System.out.println("Enter the month:");
        month = in.nextInt();
        System.out.println("Enter the year:");
        year = in.nextInt();
        return LocalDate.of(year, month, day);
    }

    public void createWeeklyAssignment(){
        System.out.println("Please enter the branch ID");
        int branchID = in.nextInt();

        Response response= facade.isBranchExists(branchID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            System.out.println("Creating weekly assignment of the closest week starting from sunday to saturday");
            LocalDate sunday=LocalDate.now();
            switch (sunday.getDayOfWeek().getValue()){
                case 1:
                    sunday=sunday.plusDays(6);
                    break;
                case 2:
                    sunday=sunday.plusDays(5);
                    break;
                case 3:
                    sunday=sunday.plusDays(4);
                    break;
                case 4:
                    sunday=sunday.plusDays(3);
                    break;
                case 5:
                    sunday=sunday.plusDays(2);
                    break;
                case 6:
                    sunday=sunday.plusDays(1);
                    break;
                case 7:
                    break;
            }

            System.out.println("next sunday:"+sunday);

            LocalDate date_ = createDate();
            ShiftDemandsDTO[][] sd;
            response= facade.createWeeklyAssignment(branchID, sunday, parentCLI.getLoggedInWorker()/*, drivers*/);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
        }


    }

    private void replaceAShiftBetweenTwoWorkers() {
        System.out.println("Please enter the branch ID1");
        int branchID1 = in.nextInt();
        System.out.println("Please enter the date1");
        LocalDate date1 = createDate();
        System.out.println("Please enter the shift type1:");
        String shiftTypeDTO1=in.next();
        System.out.println("Please enter the worker id1:");
        String id1=in.next();

        System.out.println("Please enter the branch ID2");
        int branchID2 = in.nextInt();
        System.out.println("Please enter the date2");
        LocalDate date2 = createDate();
        System.out.println("Please enter the shift type2:");
        String shiftTypeDTO2=in.next();
        System.out.println("Please enter the worker id2:");
        String id2=in.next();

        Response response= facade.workerReplacement(branchID1, branchID2, date1, date2, shiftTypeDTO1, shiftTypeDTO2, id1, id2);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());

    }

    private void displayCurrentDayShift() {
        ShiftTypeDTO morning = ShiftTypeDTO.Morning;
        ShiftTypeDTO evening = ShiftTypeDTO.Evening;
        System.out.println("Please enter the branch ID");
        int bID = in.nextInt();
        Response response= facade.isLegalBranch(bID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            response= facade.printWorkersAtShift(bID, LocalDate.now(), morning);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
            else{
                response= facade.printWorkersAtShift(bID, LocalDate.now(), evening);
                if(response.isErrorOccurred())
                    System.out.println(response.getErrorMessage());
            }

        }

    }

    private void displayShiftAssignment() {
        System.out.println("Please enter the date of the shift");
        LocalDate shiftDate = createDate();
        System.out.println("Please enter the type of the first shift for morning press M and for evening press E");
        String type = in.next();
        ShiftTypeDTO shiftTypeDTO = createShiftType(type);

        if (shiftTypeDTO == null) {
            do {
                System.out.println("The type was incorrect Please enter again the type of the first shift for morning press M and for evening press E");
                type = in.next();
                shiftTypeDTO = createShiftType(type);

            } while (shiftTypeDTO == null);
        }
        System.out.println("Please enter the branch ID");
        int b = in.nextInt();
        Response response= facade.isLegalBranch(b);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            response= facade.printWorkersAtShift(b, shiftDate, shiftTypeDTO);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
        }
    }

    private ShiftTypeDTO createShiftType(String type) {
        if (type.equals("M") || type.equals("m"))
            return ShiftTypeDTO.Morning;
        else if (type.equals("E") || type.equals("e"))
            return ShiftTypeDTO.Evening;
        else return null;
    }

    private void searchWorker() {
        System.out.println("Please enter the ID of the worker");
        String workerID = in.next();
        ResponseT<WorkerDTO> workerDTOResponseT= facade.findDTOWorkerByID(workerID);
        if(workerDTOResponseT.isErrorOccurred()){
            System.out.println(workerDTOResponseT.getErrorMessage());
            return ;
        }
        WorkerDTO workerDTO=workerDTOResponseT.getValue();
        Response response=facade.printWorker(workerDTO);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());

    }

    private void workersByQualification() {
        System.out.println("Please enter the branch ID");
        int brID = in.nextInt();
        Response response= facade.isLegalBranch(brID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            System.out.println("Please enter the date of the shift");
            LocalDate ld = createDate();
            System.out.println("Please enter the type of the shift for morning press M and for evening press E");
            String s = in.next();
            ShiftTypeDTO st = createShiftType(s);

            ShiftDTO sh;
            ResponseT<ShiftDTO>shiftDTOResponseT= facade.getShift(brID, ld,st);
            if(shiftDTOResponseT.isErrorOccurred())
                System.out.println(shiftDTOResponseT.getErrorMessage());
            else {
                sh = shiftDTOResponseT.getValue();
                if(sh==null) {
                    System.out.println("This shift is not exist");
                    return;
                }
                System.out.println("Please choose qualification number");
                System.out.println("1) Cashier ");
                System.out.println("2) Storekeeper");
                System.out.println("3) Arranger");
                System.out.println("4) Guard");
                System.out.println("5) Assistant");
                System.out.println("6) ShiftManager");
                int q = in.nextInt();
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
                        ResponseT<WorkerDTO>shiftManagerResponse= facade.getShiftManager(sh);
                        if(shiftManagerResponse.isErrorOccurred()) {
                            System.out.println(shiftManagerResponse.getErrorMessage());
                            break;
                        }
                        System.out.println("The ShiftManager in the shift is:" + shiftManagerResponse.getValue().getFirstName() + " " + shiftManagerResponse.getValue().getLastName());
                        break;
                }
                if (q != 6 && sh != null) {
                    response= facade.printWorkersByQualification(qua, sh);
                    if(response.isErrorOccurred())
                        System.out.println(response.getErrorMessage());
                }

            }
        }

    }

    private void displayWeeklyAssignment() {
        System.out.println("Please enter the date of sunday in order to see the weekly shift assignment");
        LocalDate date = createDate();
        System.out.println("Please enter the branch ID");
        int branchID = in.nextInt();
        Response response= facade.isLegalBranch(branchID);
        if(response.isErrorOccurred()){
            System.out.println(response.getErrorMessage());
        }else{
            response= facade.printWeeklyAssignment(branchID, date);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
        }
    }

    private void displayPersonalDetails(){
        personalDetailsMenu(parentCLI.getLoggedInWorker().getID());
    }

    private void personalDetailsMenu(String ID) {
        while (true) {
            System.out.println("\n Please enter a number between 1-4 in order to choose which information you want to see");
            System.out.println("1) Display your hiring conditions");
            System.out.println("2) Display your qualifications");
            System.out.println("3) Display your start working date");
            System.out.println("4) Display your bank account");

            System.out.println("For exit the Personal Details menu press 0");
            int menu = in.nextInt();
            if (menu == 0) break;

            ResponseT<WorkerDTO> workerDTOResponseT= facade.findDTOWorkerByID(ID);
            if(workerDTOResponseT.isErrorOccurred())
                System.out.println(workerDTOResponseT.getErrorMessage());
            else{
                WorkerDTO workerDTO = workerDTOResponseT.getValue();
                if(workerDTO==null){
                    System.out.println("The worker is not exist");
                    return;
                }

                while (menu < 1 || menu > 4) {
                    System.out.println("please enter a number between 1-4");
                    System.out.println("For exit the Personal Details menu press 0");
                    menu = in.nextInt();
                    if(menu==0) break;
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
    }

    private void displayWorkerPersonalDetails() {
        System.out.println("Please enter worker ID");
        String ID = in.next();
        Response response = facade.isWorkerExist(ID);
        if (response.isErrorOccurred()) {
            System.out.println(response.getErrorMessage());
        } else {
            ResponseT<WorkerDTO> workerDTOResponseT = facade.findDTOWorkerByID(ID);
            if (workerDTOResponseT.isErrorOccurred())
                System.out.println(workerDTOResponseT.getValue());
            else {
                WorkerDTO workerDTO = workerDTOResponseT.getValue();
                System.out.println("The worker chosen is " + workerDTO.getFirstName() + " " + workerDTO.getLastName() + " ");
                System.out.println("Please enter a number between 1-4 in order to choose which information you want to see");
                System.out.println("1) Display the hiring conditions of chosen worker");
                System.out.println("2) Display the qualifications of chosen worker");
                System.out.println("3) Display start working date of chosen worker");
                System.out.println("4) Display the bank account of chosen worker");

                int menu = in.nextInt();
                while (menu < 1 || menu > 4) {
                    System.out.println("please enter a number between 1-4");
                    menu = in.nextInt();
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
        }
    }

    private void displayWorkersByBranchID() {
        System.out.println("Please enter the branch ID");
        int brID = in.nextInt();
        Response response= facade.displayWorkersByBranchID(brID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
    }


    private void addQualification() {
        System.out.println("Please enter worker ID");
        String ID = in.next();
        Response workerExist= facade.isWorkerExist(ID);
        if(workerExist.isErrorOccurred())
            System.out.println(workerExist.getErrorMessage());
        else{
            ResponseT<WorkerDTO>workerDTOResponseT= facade.findDTOWorkerByID(ID);
            if(workerDTOResponseT.isErrorOccurred())
                System.out.println(workerDTOResponseT.getErrorMessage());
            else{
                System.out.println("The former qualifications of this worker are: ");
                System.out.println(workerDTOResponseT.getValue().getQualifications().toString());
                System.out.println("select qualification to add from the qualifications by entering its number:");
                int index=1;
                for(QualificationsDTO q : QualificationsDTO.values()){
                    System.out.print(index+"."+q.name()+" ");
                    index++;
                }
                int choose=in.nextInt();
                QualificationsDTO toAdd=null;
                index=1;
                for(QualificationsDTO q : QualificationsDTO.values()){
                    if(index==choose)
                        toAdd=q;
                    index++;
                }

                Response response= facade.addQualification(ID,toAdd);
                if(response.isErrorOccurred())
                    System.out.println(response.getErrorMessage());

            }

        }
    }

    private void updateWorkersDetails() {
        System.out.println("Please enter worker ID");
        String ID = in.next();
        Response response= facade.isWorkerExist(ID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            int option=1;
            while (option != 0) {
                System.out.println("1) Reset worker's first name");
                System.out.println("2) Reset worker's last name");
                System.out.println("3) Reset worker's bankAccount");
                System.out.println("4) Reset worker's hiringConditions");
                System.out.println("5) Reset worker's qualifications");
                System.out.println("Please choose which detail you want to change, if you finish changing press 0");
                option= in.nextInt();


                ResponseT<WorkerDTO> workerDTOResponseT = facade.findDTOWorkerByID(ID);
                if(workerDTOResponseT.isErrorOccurred()) {
                    System.out.println(workerDTOResponseT.getErrorMessage());
                    return ;
                }

                switch (option) {
                    case 1:
                        System.out.println("The first name of the worker was: " + workerDTOResponseT.getValue().getFirstName());
                        System.out.println("Enter the new first name of the worker");
                        String firstName = in.next();
                        Response response1= facade.setWorkerFirstName(ID,firstName);
                        if(response1.isErrorOccurred())
                            System.out.println(response1.getErrorMessage());
                        break;
                    case 2:
                        System.out.println("The Last name of the worker was: " + workerDTOResponseT.getValue().getLastName());
                        System.out.println("Enter the new last name of the worker");
                        String lastName = in.next();
                        Response response2= facade.setWorkerLastName(ID,lastName);
                        if(response2.isErrorOccurred())
                            System.out.println(response2.getErrorMessage());
                        break;
                    case 3:
                        System.out.println("The Last bank account of the worker was: " + workerDTOResponseT.getValue().getBankAccount());
                        BankAccountDTO bankAccountDTO = createBankAccount();
                        Response response3= facade.setBankAccount(ID,bankAccountDTO);
                        if(response3.isErrorOccurred())
                            System.out.println(response3.getErrorMessage());
                        break;
                    case 4:
                        System.out.println("The Last hiring conditions of the worker was: " + workerDTOResponseT.getValue().getHiringConditions());
                        HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
                        Response response4= facade.setHiringConditions(ID,hiringConditionsDTO);
                        if(response4.isErrorOccurred())
                            System.out.println(response4.getErrorMessage());
                        break;
                    case 5:
                        System.out.println("The Last qualifications of the worker were: " + workerDTOResponseT.getValue().getQualifications());
                        List<QualificationsDTO> qualifications = createQualifications();
                        Response response5= facade.setWorkerQualifications(ID,qualifications);
                        if(response5.isErrorOccurred())
                            System.out.println(response5.getErrorMessage());
                        break;
                }
            }
        }

    }

    public BankAccountDTO createBankAccount() {
        String bankName, branch, bankAccount;
        System.out.println("Enter bank name");
        bankName = in.next();
        System.out.println("Enter bank branch number");
        branch = in.next();
        System.out.println("Enter bank account number");
        bankAccount = in.next();
        return new BankAccountDTO(bankName, branch, bankAccount);
    }

    public AvailableWorkDaysDTO createAvailableWorkDays(String person) {
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
                System.out.println("Can "+ person + " work at " + week.get(i) + " in shift " + shift + "? Enter Y/N");
                can = in.next();
                while (!(can.equals("Y") || can.equals("y") || can.equals("N") || can.equals("n")))
                    can = in.next();
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
                System.out.println("Do " +person + " prefer to work at " + week.get(i) + " in shift " + shift + "? Enter Y/N");
                prefer = in.next();
                while (!(prefer.equals("Y") || prefer.equals("y") || prefer.equals("N") || prefer.equals("n")))
                    prefer = in.next();
                if (prefer.equals("Y") || prefer.equals("y"))
                    favorite[i][j] = true;
                else favorite[i][j] = false;

            }
        AvailableWorkDaysDTO availableWorkDaysDTO = new AvailableWorkDaysDTO(favorite, available);
        return availableWorkDaysDTO;


    }

    public HiringConditionsDTO createHiringConditions() {
        double salary;
        String fund;
        int vacationDays, sickLeavePerMonth;
        System.out.println("Enter salary per hour:");
        salary = in.nextDouble();
        System.out.println("Enter fund:");
        fund = in.next();
        System.out.println("Enter the amount of vacation days per year");
        vacationDays = in.nextInt();
        System.out.println("Enter the number of sick days per month");
        sickLeavePerMonth = in.nextInt();
        return new HiringConditionsDTO(salary, fund, vacationDays, sickLeavePerMonth);
    }


    public List<QualificationsDTO> createQualifications() {
        String c;
        List<QualificationsDTO> qualifications = new LinkedList<>();
        for (QualificationsDTO q : QualificationsDTO.values()) {
            do {
                System.out.println("Do the worker is talented to work as " + q.name() + " Enter Y/N");
                c = in.next();
            } while (!c.equals("Y")  && !c.equals("y") && !c.equals("n") && !c.equals("N"));
            if (c.equals("Y") || c.equals("y")){
                if(qualifications.contains(QualificationsDTO.BranchManager)&&QualificationsDTO.Human_Resources_Director.name().equals(q.name()))
                    System.out.println("The worker is already qualified of being branch manager, he can't be HRD as well");
                else if (qualifications.contains(QualificationsDTO.Human_Resources_Director)&&QualificationsDTO.BranchManager.name().equals(q.name()))
                    System.out.println("The worker is already qualified of being HRD, he can't be branch manager as well");
                else qualifications.add(q);
            }
        }
        return qualifications;
    }


    private void createWeeklyShiftDemands() {
        System.out.println("Please enter the branch ID");
        int brID = in.nextInt();
        Response response= facade.isBranchExists(brID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else {
            System.out.println("You are about to create weekly shift demands to the closest week start on sunday");
            LocalDate date = LocalDate.now();
            switch (date.getDayOfWeek().getValue()){
                case 1:
                    date=date.plusDays(6);
                    break;
                case 2:
                    date=date.plusDays(5);
                    break;
                case 3:
                    date=date.plusDays(4);
                    break;
                case 4:
                    date=date.plusDays(3);
                    break;
                case 5:
                    date=date.plusDays(2);
                    break;
                case 6:
                    date=date.plusDays(1);
                    break;
                case 7:
                    break;
            }

            System.out.println("next sunday:"+date);

            ShiftTypeDTO shiftTypeDTO;
            int cashierAmount=0,storeKeeperAmount=0,arrangerAmount=0,guardAmount=0,assistantAmount=0;
            ShiftDemandsDTO shiftDemandsDTO=null;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        shiftTypeDTO = ShiftTypeDTO.Morning;
                        System.out.println("enter morning shift of " + date);
                    } else {
                        System.out.println("enter evening shift of " + date);
                        shiftTypeDTO = ShiftTypeDTO.Evening;
                    }

                    System.out.println("Please enter cashiers amount");
                    cashierAmount = in.nextInt();
                    System.out.println("Please enter storeKeepers amount");
                    storeKeeperAmount = in.nextInt();
                    System.out.println("Please enter arrangers amount");
                    arrangerAmount = in.nextInt();
                    System.out.println("Please enter guards amount");
                    guardAmount = in.nextInt();
                    System.out.println("Please enter assistants amount");
                    assistantAmount = in.nextInt();
                    shiftDemandsDTO=new ShiftDemandsDTO(date,cashierAmount,storeKeeperAmount,arrangerAmount,guardAmount,assistantAmount);
                    Response response1= facade.addShiftDemands(brID,date,shiftTypeDTO,shiftDemandsDTO);
                    if(response1.isErrorOccurred()) {
                        System.out.println(response1.getErrorMessage());
                        return ;
                    }
                }
                date = date.plusDays(1);
            }


        }

    }

    private void createShiftDemands(LocalDate date, int brID, ShiftTypeDTO shiftTypeDTO) {

        int cashiers, storeKeepers, arrangers, guards, assistants;
        System.out.println("Please enter cashiers amount");
        cashiers = in.nextInt();
        System.out.println("Please enter storeKeepers amount");
        storeKeepers = in.nextInt();
        System.out.println("Please enter arrangers amount");
        arrangers = in.nextInt();
        System.out.println("Please enter guards amount");
        guards = in.nextInt();
        System.out.println("Please enter assistants amount");
        assistants = in.nextInt();
        ShiftDemandsDTO shiftDemandsDTO = new ShiftDemandsDTO(date, cashiers, storeKeepers, arrangers, guards, assistants);
        Response response= facade.addShiftDemands(brID, date, shiftTypeDTO, shiftDemandsDTO);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else System.out.println("Created shift demands");
    }


    private void addWorker() {
        System.out.println("Please enter the branch ID");
        int branchID = in.nextInt();
        ResponseT<Boolean> booleanResponseT=facade.isBranchExists(branchID);
        if(booleanResponseT.isErrorOccurred()) {
            System.out.println(booleanResponseT.getErrorMessage());
            return ;
        }
        if(booleanResponseT.getValue()!=true) {
            System.out.println("cant add Worker to a branch that is not exists");
            return ;
        }

//        Response response = facade.isLegalBranch(branchID);
//        if (response.isErrorOccurred()) {
//            System.out.println(response.getErrorMessage());
//            return;
//        }

        System.out.println("Please enter worker ID");
        String ID = in.next();
        Response response = facade.isWorkerExist(ID);
        if (!response.isErrorOccurred()) {
            response=facade.getBackToWork(ID);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
            //System.out.println("The worker is already exist");
            return;
        }
        createWorker(ID,branchID);


    }


    private void addNewBranch() {
        WorkerDTO branchManager, branchHRD;
        System.out.println("Please enter new branch ID");
        int branchID = in.nextInt();
        ResponseT<Boolean> isBranchExists= facade.isBranchExists(branchID);
        if(isBranchExists.isErrorOccurred()) {

            System.out.println("add new branch manager and HRD");
            branchManager = createWorkerWithoutAdd();
            branchHRD = createWorkerWithoutAdd();
            Response response = facade.addBranch(branchID, branchManager, branchHRD);
            if (response.isErrorOccurred()) {
                System.out.println(response.getErrorMessage());
                return;
            }
        }

//        ResponseT<Boolean>branchDTOResponseT= facade.isBranchExists(branchID);
//        if(!branchDTOResponseT.isErrorOccurred())
//            System.out.println("cant create a branch that is already exist");
//        else{
//            Boolean isBranchExists=branchDTOResponseT.getValue();
//            if (isBranchExists != null && isBranchExists==true)
//                throw new IllegalArgumentException("The branch " + branchID + "is already exist in the system");
//            System.out.println("Do you want to promote an existing worker in another branch to be this new branch manager?");
//            System.out.println("Enter Y/N");
//            String ans = reader.next();
//            while (!ans.equals( "n") && !ans.equals("N") && !ans.equals("Y") && !ans.equals("y")) {
//                System.out.println("Please enter Y for Yes or N for No");
//                ans = reader.next();
//            }
//            if (ans.equals("n") || ans.equals("N")) {
//                System.out.println("Please enter new branch manager");
//                branchManager = createWorkerWithoutAdd();
//            } else {
//                Response response;
//                String ID;
//                do {
//                    System.out.println("Please enter ID of existing worker");
//                    ID = reader.next();
//                    response = facade.isWorkerExist(ID);
//                    if (response.isErrorOccurred())
//                        System.out.println(response.getErrorMessage());
//                } while (response.isErrorOccurred());
//
//                ResponseT<WorkerDTO> workerDTOResponseT = facade.findDTOWorkerByID(ID);
//                if (workerDTOResponseT.isErrorOccurred()) {
//                    System.out.println(response.getErrorMessage());
//                    return;
//                }
//                if (workerDTOResponseT.getValue() == null) {
//                    System.out.println("There is no such worker in the System");
//                    return;
//                }
//                ResponseT<Integer> integerResponseT = facade.isAManagerOfBranch(ID);
//                if (!integerResponseT.isErrorOccurred()) {
//                    System.out.println("The worker: " +ID + " is already a manager of branch" + integerResponseT.getValue());
//                    return;
//                }
//
//                if(workerDTOResponseT.getValue().getQualifications().contains(QualificationsDTO.Human_Resources_Director)){
//                    System.out.println("The worker is already HRD qualified so the worker can't be a branch manager");
//                    return ;
//                }
//                response = facade.addQualification(ID, QualificationsDTO.BranchManager);
//                if (response.isErrorOccurred()) {
//                    System.out.println(response.getErrorMessage());
//                    return ;
//                }
//
//                ResponseT<WorkerDTO> workerDTOResponseT1= facade.findDTOWorkerByID(ID);
//                if(workerDTOResponseT1.isErrorOccurred()){
//                    System.out.println(workerDTOResponseT1.getErrorMessage());
//                    return;
//                }
//                if(workerDTOResponseT1.getValue()==null){
//                    System.out.println("Worker didn't found");
//                    return ;
//                }
//                branchManager=workerDTOResponseT1.getValue();
//            }
//
//            System.out.println("Do you want to choose an existing worker that already qualified to be HRD to be this new branch HRD?");
//            System.out.println("Enter Y/N");
//            ans = reader.next();
//            while (!ans.equals( "n") && !ans.equals("N") && !ans.equals("Y") && !ans.equals("y")) {
//                System.out.println("Please enter Y for Yes or N for No");
//                ans = reader.next();
//            }
//            if (ans.equals("n") || ans.equals("N")) {
//                System.out.println("Please enter new branch HRD");
//                branchHRD = createWorkerWithoutAdd();
//            } else {
//                String ID;
//                Response response;
//                do {
//                    System.out.println("Please enter ID of existing worker");
//                    ID = reader.next();
//                    response = facade.isWorkerExist(ID);
//                } while (response.isErrorOccurred());
//
//                ResponseT<WorkerDTO> workerDTOResponseT = facade.findDTOWorkerByID(ID);
//                if (workerDTOResponseT.isErrorOccurred()) {
//                    System.out.println(workerDTOResponseT.getErrorMessage());
//                    return;
//                }
//                if (workerDTOResponseT.getValue() == null) {
//                    System.out.println("There is no such worker in the System");
//                    return;
//                }
//                Boolean isHRD=workerDTOResponseT.getValue().getQualifications().contains(QualificationsDTO.Human_Resources_Director);
//                if (!isHRD) {
//                    System.out.println("The worker: " + ID + " is not qualified of being HRD");
//                    return;
//                }
//
//                branchHRD = workerDTOResponseT.getValue();
//
//            }
//            if(branchHRD==null)
//                System.out.println("branchHRD bug");
//            if(branchManager==null)
//                System.out.println("branch manager bug");
//
//            if (branchHRD != null && branchManager != null) {
//                Response response= facade.addBranch(branchID, branchManager, branchHRD);
//                if(response.isErrorOccurred())
//                    System.out.println(response.getErrorMessage());
//                else System.out.println("Branch successfully created");
//            }
//
//            else System.out.println("The branch was not created due to illegal branch manager or HRD");
//        }

    }

    public void createWorker(String ID, int branchID) {
        String firstname, lastname;
        System.out.println("Enter worker first name:");
        firstname = in.next();
        System.out.println("Enter worker last name:");
        lastname = in.next();
        BankAccountDTO bankAccountDTO = createBankAccount();
        HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
        AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays("the worker");
        List<QualificationsDTO> qualifications = createQualifications();
        WorkerDTO newWorkerDTO = new WorkerDTO(firstname, lastname, ID, bankAccountDTO, hiringConditionsDTO, availableWorkDaysDTO, qualifications);
        Response response= facade.addWorker(newWorkerDTO, branchID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        System.out.println("If you're adding driver, please insert 1\nelse press any other key");
        try {
            Scanner in = new Scanner(System.in);
            String choice = in.next();
            if (choice.equals("1")){
                String licenseType, licenseExpDate;
                System.out.println("Please enter driver details: licence type");
                licenseType = in.next();
                System.out.println("Please enter driver details: expiration date");
                licenseExpDate = in.next();
                Date date;
                date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                DTO.Driver driver=new DTO.Driver(ID,licenseType,date);
                facade.createDriver(driver, branchID);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }




    public WorkerDTO createWorkerWithoutAdd() {
        String firstname, lastname;
        System.out.println("Please enter worker ID");
        String ID = in.next();
        Response response= facade.isExistingWorker(ID);
        if(!response.isErrorOccurred())
            System.out.println("this worker is already exists");
        else{
            System.out.println("Enter worker first name:");
            firstname = in.next();
            System.out.println("Enter worker last name:");
            lastname = in.next();
            BankAccountDTO bankAccountDTO = createBankAccount();
            HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
            AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays("the worker");
            List<QualificationsDTO> qualifications = createQualifications();
            WorkerDTO newWorkerDTO = new WorkerDTO(firstname, lastname, ID, bankAccountDTO, hiringConditionsDTO, availableWorkDaysDTO, qualifications);
            return newWorkerDTO;
        }

        return null;
    }


    private void removeWorker() {
        System.out.println("Please enter worker ID to remove");
        String ID = in.next();
        if (parentCLI.getLoggedInWorker().getID().equals(ID)) {
            System.out.println("You can't remove yourself");
            return;
        }

        ResponseT<Integer> branchIDByWorker=facade.findBranchIDByWorker(ID);
        if(branchIDByWorker.isErrorOccurred())
            System.out.println(branchIDByWorker.getErrorMessage());
        else{
            ResponseT<WorkerDTO> branchManager=facade.getBranchManager(branchIDByWorker.getValue());
            if(branchManager.isErrorOccurred()) {
                System.out.println(branchManager.getErrorMessage());
                return ;
            }

            if(branchManager.getValue().getID().equals(ID)){
                System.out.println("You cant remove the branch manager of this branch");
                return;
            }

            Response response=facade.removeWorker(ID);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
            else
                System.out.println("Removal succeeded");
        }
    }


    private void changeWorkerBranch() {
        System.out.println("Please enter worker ID");
        String ID = in.next();
        Response response = facade.isWorkerExist(ID);
        if (response.isErrorOccurred()) {
            System.out.println(response.getErrorMessage());
            return;
        }
        System.out.println("Please enter new branch ID");
        int newBranchID = in.nextInt();
        ResponseT<Boolean> isBranchExists = facade.isBranchExists(newBranchID);
        if (isBranchExists.isErrorOccurred()) {
            System.out.println(isBranchExists.getErrorMessage());
            return;
        }
        if (isBranchExists.getValue() != true) {
            System.out.println(isBranchExists.getErrorMessage());
            return;
        }

        if (facade.getBranchHRD(newBranchID).getValue().getID().equals(ID)) {
            System.out.println("cant change the HRD of the branch is branch");
            return;
        }

        if (facade.getBranchManager(newBranchID).getValue().getID().equals(ID)) {
            System.out.println("cant change the branch manager of the branch is branch");
            return;
        }

        response = facade.changeWorkerBranch(ID, newBranchID);
        if (response.isErrorOccurred())
            System.out.println(response.getErrorMessage());

    }

}