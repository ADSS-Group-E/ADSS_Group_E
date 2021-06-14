package PresentationLayer.Workers.OptionMenus;

import BusinessLayer.Workers_Transport.Response;
import BusinessLayer.Workers_Transport.ResponseT;
import BusinessLayer.Workers_Transport.WorkersPackage.WorkersFacade;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Transport.OptionMenus.*;
import PresentationLayer.Workers_Transport.*;

import java.time.LocalDate;

import static PresentationLayer.Workers_Transport.Main.createDate;


public class MainWorkersOptionsMenu extends OptionsMenu {

    private final WorkersFacade facade;

    public MainWorkersOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        facade = new WorkersFacade();

        int i=1;
        options.put(i++, new Option( "Create new weekly assignment by the date of sunday", this::createWeeklyAssignment));
        options.put(i++, new Option( "Replace a shift between two workers", this::replaceAShiftBetweenTwoWorkers));
        options.put(i++, new Option( "Display current day shift assignment by branch ID", this::displayCurrentDayShift));
        options.put(i++, new Option( "Display shift assignment by date and by branch ID", this::displayShiftAssignment));
        options.put(i++, new Option( "Search worker by worker ID", this::searchWorker));
        options.put(i++, new Option( "Display workers by Qualification, shift and by branch ID", this::workersByQualification));
        options.put(i++, new Option( "Display weekly assignment by branchID and by date", this::displayWeeklyAssignment));
        options.put(i++, new Option( "Display personal details of a worker by his ID", this::displayWorkerPersonalDetails));
        options.put(i++, new Option( "Display your personal details", this::displayPersonalDetails));
        options.put(i++, new Option( "Display workers by branch ID", this::displayWorkersByBranchID));

        options.put(i++, new Option( "Back", this::back));
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

}