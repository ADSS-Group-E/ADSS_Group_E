package PresentationLayer;

import BussinessLayer.DriverPackage.Driver;
import BussinessLayer.DriverPackage.DriverController;
import BussinessLayer.Facade;
import BussinessLayer.Response;
import BussinessLayer.ResponseT;
import DataAccessLayer.Workers.Workers;

import java.time.LocalDate;
import java.util.*;

import static PresentationLayer.Main.createDate;
import static PresentationLayer.Main.systemInitialize;

public class MenuWorkers {

    private static final Facade facade = new Facade();
    private static final Scanner reader = new Scanner(System.in);

    public static void createSystem() {
        System.out.println("Welcome to Super Lee's System");
        System.out.println("Do you want the system to be initialized? \n Enter Y/N");
        String ans = reader.next();
        while (!(ans.equals("n") || ans.equals("N") || ans.equals("Y") || ans.equals("y"))) {
            System.out.println("Please enter Y for Yes or N for No");
            ans = reader.next();
        }
        if (ans.equals("y") || ans.equals("Y")) {
            systemInitialize();
            int stop=1;
            int exit=0;

            while (true){
              stop = system(1);
              if(stop == 0)
              { System.out.println("Do you want to exit the system, for exit press 0 else press any number to continue");
                  exit = reader.nextInt();
              }
              if(exit == 0)
                  return;
              else {
                  stop =1;
                  continue;
              }
            }
        } else {
            int stop=1;
            int exit=0;

            while (true){
                stop = createSystemWithoutInitialize(1);
                if(stop == 0)
                { System.out.println("Do you want to exit the system, for exit press 0 else press any number to continue");
                    exit = reader.nextInt();
                }
                if(exit == 0)
                    return;
                else {
                    stop =1;
                    continue;
                }
            }
        }

    }

    private static int createSystemWithoutInitialize(int stop) {
        return MenuWorkers.menuForCreate(stop);
    }


    private static void createWeeklyAssignment(WorkerDTO branchManger, List<Driver> drivers) {
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();

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

            //LocalDate date_ = createDate();
            ShiftDemandsDTO[][] sd;
            response= facade.createWeeklyAssignment(branchID, sunday, branchManger, drivers);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
        }

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
        Response response= facade.isLegalBranch(branchID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            System.out.println("Please enter the Date of the first shift");
            LocalDate date1 = createDate();
            System.out.println("Please enter the type of the first shift for morning press M and for evening press E");
            String type1 = reader.next();
            ShiftTypeDTO shiftTypeDTO1 = createShiftType(type1);

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
            response= facade.printWorkersAtShift(branchID, date1, shiftTypeDTO1);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
            else{
                int worker1SerialNumber, worker2SerialNumber;
                System.out.println("Enter the worker's serial number you want to replace");
                worker1SerialNumber = reader.nextInt();

                System.out.println("Workers at second shift:");
                response= facade.printWorkersAtShift(branchID, date2, shiftTypeDTO2);
                if(response.isErrorOccurred())
                    System.out.println(response.getErrorMessage());
                else{
                    System.out.println("Enter the worker's serial number you want to replace");
                    worker2SerialNumber = reader.nextInt();

                    ResponseT<WorkerDTO> response1= facade.findWorkerBySerialNumber(branchID,worker1SerialNumber - 1);
                    ResponseT<WorkerDTO>response2= facade.findWorkerBySerialNumber(branchID,worker2SerialNumber - 1);
                    if(!response1.isErrorOccurred()&&!response2.isErrorOccurred()){
                        WorkerDTO workerDTO1 = response1.getValue();
                        WorkerDTO workerDTO2 =  response2.getValue();
                        response= facade.workerReplacement(branchID, date1, shiftTypeDTO1, date2, shiftTypeDTO2, workerDTO1, workerDTO2, workerDTO);
                        if(response.isErrorOccurred())
                            System.out.println(response.getErrorMessage());
                    }else{
                        System.out.println(response1.getErrorMessage());
                        System.out.println(response2.getErrorMessage());
                    }
                }

            }

        }


    }

    private static void displayCurrentDayShift() {
        ShiftTypeDTO morning = ShiftTypeDTO.Morning;
        ShiftTypeDTO evening = ShiftTypeDTO.Evening;
        System.out.println("Please enter the branch ID");
        int bID = reader.nextInt();
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
        Response response= facade.isLegalBranch(b);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            response= facade.printWorkersAtShift(b, shiftDate, shiftTypeDTO);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
        }

    }

    private static void searchWorker() {
        System.out.println("Please enter the ID of the worker");
        String workerID = reader.next();
        ResponseT<WorkerDTO> workerDTOResponseT= facade.findDTOWorkerByID(workerID);
        if(workerDTOResponseT.isErrorOccurred()){
            System.out.println(workerDTOResponseT.getErrorMessage());
            return ;
        }
        WorkerDTO workerDTO=workerDTOResponseT.getValue();
        Response response=facade.printWorker(workerDTO);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());

//        if(workerExist.isErrorOccurred())
//            System.out.println(workerExist.getErrorMessage());
//        Response workerExist= facade.isWorkerExist(IDForPrint);
//        if(workerExist.isErrorOccurred())
//            System.out.println(workerExist.getErrorMessage());
//        else{
//            ResponseT<WorkerDTO>workerDTOResponseT= facade.findDTOWorkerByID(IDForPrint);
//            if(workerDTOResponseT.isErrorOccurred())
//                System.out.println(workerDTOResponseT.getErrorMessage());
//            else {
//                if(workerDTOResponseT.getValue()!=null){
//                    Response printWorker= facade.printWorker(workerDTOResponseT.getValue());
//                    if(printWorker.isErrorOccurred())
//                        System.out.println(printWorker.getErrorMessage());
//                }
//            }
//        }
    }

    private static void workersByQualification() {
        System.out.println("Please enter the branch ID");
        int brID = reader.nextInt();
        Response response= facade.isLegalBranch(brID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else{
            System.out.println("Please enter the date of the shift");
            LocalDate ld = createDate();
            System.out.println("Please enter the type of the shift for morning press M and for evening press E");
            String s = reader.next();
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

    private static void displayWeeklyAssignment() {
        System.out.println("Please enter the date of sunday in order to see the weekly shift assignment");
        LocalDate date = createDate();
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();
        Response response= facade.isLegalBranch(branchID);
        if(response.isErrorOccurred()){
            System.out.println(response.getErrorMessage());
        }else{
            response= facade.printWeeklyAssignment(branchID, date);
            if(response.isErrorOccurred())
                System.out.println(response.getErrorMessage());
        }
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
                    menu = reader.nextInt();
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


    public static int branchManagerMenu(WorkerDTO branchManager, int branchID, List<Driver> drivers) {
        while (true) {
            System.out.println("\n Welcome " + branchManager.getFirstName() + " " + branchManager.getLastName() + ", the branch manager of branch " + branchID + " please choose an action");
            System.out.println("1) Create new weekly assignment by the date of sunday");
            System.out.println("2) Replace a shift between two workers");
            System.out.println("3) Display current day shift assignment by branch ID");
            System.out.println("4) Display shift assignment by date and by branch ID");
            System.out.println("5) Search worker by worker ID");
            System.out.println("6) Display workers by Qualification, shift and by branch ID");
            System.out.println("7) Display weekly assignment by branchID and by date");
            System.out.println("8) Display personal details of a worker by his ID");
            System.out.println("9) Display your personal details ");
            System.out.println("10) Display workers by branch ID \n");


            System.out.println("For exit the branch manager menu press 0");
            int menu = reader.nextInt();
            if(menu==0)
                return menu;


            while (menu < 1 || menu > 10) {
                System.out.println("please enter a number between 1-10");
                System.out.println("For exit press 0");
                menu = reader.nextInt();
            }

            switch (menu) {
                case 1:
                    createWeeklyAssignment(branchManager,drivers);
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
                    Response response= facade.isWorkerExist(ID);
                    if(response.isErrorOccurred())
                        System.out.println(response.getErrorMessage());
                    displayWorkerPersonalDetails();
                    break;
                case 9:
                    personalDetailsMenu(branchManager.getID());
                    break;
                case 10:
                    displayWorkersByBranchID();
                    break;

            }
        }
    }

    private static void displayWorkersByBranchID() {
        System.out.println("Please enter the branch ID");
        int brID = reader.nextInt();
        Response response= facade.displayWorkersByBranchID(brID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
    }

    private static void displayWorkerPersonalDetails() {
            System.out.println("Please enter worker ID");
            String ID = reader.next();
            Response response= facade.isWorkerExist(ID);
            if(response.isErrorOccurred()){
                System.out.println(response.getErrorMessage());
            }else{
                ResponseT<WorkerDTO>workerDTOResponseT= facade.findDTOWorkerByID(ID);
                if(workerDTOResponseT.isErrorOccurred())
                    System.out.println(workerDTOResponseT.getValue());
                else {
                    WorkerDTO workerDTO = workerDTOResponseT.getValue();
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
            }

    }


    public static int HRDMenu(WorkerDTO HRD, int branchID) {
        while (true) {
            System.out.println("\n Welcome " + HRD.getFirstName() + " " + HRD.getLastName() + ", the HRD of branch " + branchID + " please choose an action:");
            System.out.println("1) Create new weekly shifts demands by branch ID,the date of sunday");
            System.out.println("2) Add new worker");
            System.out.println("3) Remove worker");
            System.out.println("4) Change worker branch");
            System.out.println("5) Display current day shift assignment by branch ID");
            System.out.println("6) Display shift assignment by date and by branch ID");
            System.out.println("7) Search worker by  worker ID");
            System.out.println("8) Display workers by Qualification, shift and by branch ID");
            System.out.println("9) Display weekly assignment by branchID and by date");
            System.out.println("10) Update worker's details");
            System.out.println("11) Display your personal details ");
            System.out.println("12) Add new branch");
            System.out.println("13) Add new qualifications by worker ID ");
            System.out.println("14) Display personal details of a worker by his ID");
            System.out.println("15) Display workers by branch ID \n");

            System.out.println("For exit the HRD menu press 0");
            int menu = reader.nextInt();
            if (menu == 0)
                return menu;
            while (menu < 1 || menu > 15) {
                System.out.println("please enter a number between 1-15");
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
                    removeWorker(HRD.getID());
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
                case 15:
                    displayWorkersByBranchID();
                    break;

            }
        }
    }

    private static void addQualification() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
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
                int choose=reader.nextInt();
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


    public static int otherWorkerMenu(WorkerDTO workerDTO, int branchID) {
        while (true) {
            System.out.println("\nWelcome " + workerDTO.getFirstName() + " " + workerDTO.getLastName() + " please choose an action");
            System.out.println("1) Create new weekly Available Work days by the date of sunday");
            System.out.println("2) Display current day shift assignment of your branch");
            System.out.println("3) Display the daily shift assignment of your branch by date");
            System.out.println("4) Display weekly assignment by branchID and by date");
            System.out.println("5) Display your personal details ");
            System.out.println("6) Display workers by branch ID \n");

            System.out.println("For exit the workers menu press 0");
            int menu = reader.nextInt();
            if (menu == 0)
                return menu;

            while (menu < 1 || menu > 6) {
                System.out.println("please enter a number between 1-6");
                System.out.println("For exit press 0");
                if (menu == 0) break;
                menu = reader.nextInt();
            }

            switch (menu) {
                case 1:
                    AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays("you");
                    Response response= facade.setAvailableWorkDays(branchID, workerDTO, availableWorkDaysDTO);
                    if(response.isErrorOccurred())
                        System.out.println(response.getErrorMessage());
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
                case 6:
                    displayWorkersByBranchID();
                    break;

            }
        }
    }

    private static void createWeeklyShiftDemands() {
        System.out.println("Please enter the branch ID");
        int brID = reader.nextInt();
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
                        cashierAmount = reader.nextInt();
                        System.out.println("Please enter storeKeepers amount");
                        storeKeeperAmount = reader.nextInt();
                        System.out.println("Please enter arrangers amount");
                        arrangerAmount = reader.nextInt();
                        System.out.println("Please enter guards amount");
                        guardAmount = reader.nextInt();
                        System.out.println("Please enter assistants amount");
                        assistantAmount = reader.nextInt();
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
        Response response= facade.addShiftDemands(brID, date, shiftTypeDTO, shiftDemandsDTO);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
        else System.out.println("Created shift demands");
    }

    private static void addWorker() {
        System.out.println("Please enter the branch ID");
        int branchID = reader.nextInt();
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
        String ID = reader.next();
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

    private static void addNewBranch() {
        WorkerDTO branchManager, branchHRD;
        System.out.println("Please enter new branch ID");
        int branchID = reader.nextInt();
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




    public static void createWorker(String ID, int branchID) {
        String firstname, lastname;
        System.out.println("Enter worker first name:");
        firstname = reader.next();
        System.out.println("Enter worker last name:");
        lastname = reader.next();
        BankAccountDTO bankAccountDTO = createBankAccount();
        HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
        AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays("the worker");
        List<QualificationsDTO> qualifications = createQualifications();
        WorkerDTO newWorkerDTO = new WorkerDTO(firstname, lastname, ID, bankAccountDTO, hiringConditionsDTO, availableWorkDaysDTO, qualifications);
        Response response= facade.addWorker(newWorkerDTO, branchID);
        if(response.isErrorOccurred())
            System.out.println(response.getErrorMessage());
    }

    public static WorkerDTO createWorkerWithoutAdd() {
        String firstname, lastname;
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        Response response= facade.isExistingWorker(ID);
        if(!response.isErrorOccurred())
            System.out.println("this worker is already exists");
        else{
            System.out.println("Enter worker first name:");
            firstname = reader.next();
            System.out.println("Enter worker last name:");
            lastname = reader.next();
            BankAccountDTO bankAccountDTO = createBankAccount();
            HiringConditionsDTO hiringConditionsDTO = createHiringConditions();
            AvailableWorkDaysDTO availableWorkDaysDTO = createAvailableWorkDays("the worker");
            List<QualificationsDTO> qualifications = createQualifications();
            WorkerDTO newWorkerDTO = new WorkerDTO(firstname, lastname, ID, bankAccountDTO, hiringConditionsDTO, availableWorkDaysDTO, qualifications);
            return newWorkerDTO;
        }

        return null;
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


    public static AvailableWorkDaysDTO createAvailableWorkDays(String person) {
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
                System.out.println("Do " +person + " prefer to work at " + week.get(i) + " in shift " + shift + "? Enter Y/N");
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
        String c;
        List<QualificationsDTO> qualifications = new LinkedList<>();
        for (QualificationsDTO q : QualificationsDTO.values()) {
            do {
                System.out.println("Do the worker is talented to work as " + q.name() + " Enter Y/N");
                c = reader.next();
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

    private static void removeWorker(String HRD_ID) {
        System.out.println("Please enter worker ID to remove");
        String ID = reader.next();
        if (HRD_ID.equals(ID)) {
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

    private static void changeWorkerBranch() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
        Response response = facade.isWorkerExist(ID);
        if (response.isErrorOccurred()) {
            System.out.println(response.getErrorMessage());
            return;
        }
        System.out.println("Please enter new branch ID");
        int newBranchID = reader.nextInt();
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

    private static void updateWorkersDetails() {
        System.out.println("Please enter worker ID");
        String ID = reader.next();
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
                option= reader.nextInt();


                ResponseT<WorkerDTO> workerDTOResponseT = facade.findDTOWorkerByID(ID);
                if(workerDTOResponseT.isErrorOccurred()) {
                    System.out.println(workerDTOResponseT.getErrorMessage());
                    return ;
                }

                switch (option) {
                        case 1:
                            System.out.println("The first name of the worker was: " + workerDTOResponseT.getValue().getFirstName());
                            System.out.println("Enter the new first name of the worker");
                            String firstName = reader.next();
                            Response response1= facade.setWorkerFirstName(ID,firstName);
                            if(response1.isErrorOccurred())
                                System.out.println(response1.getErrorMessage());
                            break;
                        case 2:
                            System.out.println("The Last name of the worker was: " + workerDTOResponseT.getValue().getLastName());
                            System.out.println("Enter the new last name of the worker");
                            String lastName = reader.next();
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



    public static int menuForCreate(int menu) {
        while (true) {
            System.out.println("\n Please choose an action, remember the system is not initialized yet so you might want to add new branches and workers");
            System.out.println("1) Create new weekly shifts demands by branch ID,the date of sunday");
            System.out.println("2) Add new worker");
            System.out.println("3) Remove worker");
            System.out.println("4) Change worker branch");
            System.out.println("5) Display current day shift assignment by branch ID");
            System.out.println("6) Display shift assignment by date and by branch ID");
            System.out.println("7) Search worker by  worker ID");
            System.out.println("8) Display workers by Qualification and by branch ID");
            System.out.println("9) Display weekly assignment by branchID and by date");
            System.out.println("10) Update worker's details");
            System.out.println("11) Display personal details of a worker by his ID ");
            System.out.println("12) Add new branch");
            System.out.println("13) Add new qualifications by worker ID\n");

            System.out.println("For exit press 0 and for logging the system as a worker press -1");
            menu = reader.nextInt();
            if (menu == 0)
                return menu;
            else if(menu == -1)
                 system(1);
            while (menu < 1 || menu > 13) {
                System.out.println("please enter a number between 1-13");
                System.out.println("For exit press 0");
                if (menu == 0)
                    return menu;
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
                    removeWorker("");
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

    private static int system(int s){
        int stop = 1;
        if(s == 0){
            System.out.println("For log out of Super Lee's System PRESS 0 else press any number to continue");
            int x = reader.nextInt();
             if(x == 0)
                 return 0;
        }

        System.out.println(LocalDate.now());
        System.out.println("Welcome to Super Lee's System, please enter your ID in order to log in");
        String ID = reader.next();
        ResponseT<WorkerDTO> workerDTOResponseT= facade.findDTOWorkerByID(ID);
        if(workerDTOResponseT.isErrorOccurred()) {
            System.out.println(workerDTOResponseT.getErrorMessage());
            stop=0;
        }else{
            WorkerDTO workerDTO = workerDTOResponseT.getValue();
            while (workerDTO == null) {
                //System.out.println("There is no worker with such ID please enter new ID");
                ID = reader.next();
                ResponseT<WorkerDTO> workerDTOResponseT1= facade.findDTOWorkerByID(ID);
                if(workerDTOResponseT1.isErrorOccurred())
                    System.out.println(workerDTOResponseT1.getErrorMessage());
                else{
                    workerDTO = workerDTOResponseT1.getValue();
                }

            }
            ResponseT<Integer> responseBranchID = facade.findBranchIDByWorker(ID);
            if(responseBranchID.isErrorOccurred())
                System.out.println(responseBranchID.getErrorMessage());
            else{
                int branchID = responseBranchID.getValue();
                ResponseT<List<QualificationsDTO>> listResponseT= facade.getWorkerQualifications(workerDTO);
                if(listResponseT.isErrorOccurred())
                    System.out.println(listResponseT.getErrorMessage());
                else{
                    List<QualificationsDTO> qualifications = listResponseT.getValue();
                    if (qualifications.contains(QualificationsDTO.BranchManager)) {
                        stop = MenuWorkers.branchManagerMenu(workerDTO,branchID, DriverController.getInstance().getDrivers());

                    } else if (workerDTO.getQualifications().contains(QualificationsDTO.Human_Resources_Director)) {
                        stop = MenuWorkers.HRDMenu(workerDTO,branchID);

                    }
                    else {
                        stop = MenuWorkers.otherWorkerMenu(workerDTO,branchID);
                    }
                }

            }

        }
        return stop;
    }
}




