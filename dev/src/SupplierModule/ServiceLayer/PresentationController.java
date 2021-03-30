package SupplierModule.ServiceLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class PresentationController {
    public static void main(String[] args) {
        InputService inputService = InputService.getInstance();
        ServiceController service = ServiceController.getInstance();
        service.initialize();
        final int numOfOptions = 5;
        //show user his options
        showOptions();
        int input = inputService.nextInt();
        while (input >= 0 && input < numOfOptions) {
            //decrypts input
            switch (input){
                case (0) :{//register supplier
                    String info = inputService.next();
                    ArrayList<String> items = getInputIntoList();
                    ArrayList<String> contacts = getInputIntoList();
                    manageQuantityWriter();
                    break;
                }
                case (1): {//order
                    showSupplierInfo();
                    input = inputService.nextInt();
                    String deliOptions = inputService.next();
                    ArrayList<String> items = getInputIntoList();
                    createOrder(input, deliOptions, items);
                    break;
                }
                case (2): {//
                    break;
                }
            }
            input = inputService.nextInt();
        }
    }

    static ArrayList<String> getInputIntoList(){
        InputService input = InputService.getInstance();
        ArrayList<String> list = new ArrayList<>();
        String next = input.next();
        while(!next.equals("-1")){
            list.add(next);
            next = input.next();
        }
        return list;
    }

    static void register(String info, List<String> items, List<String> contacts){
        String name = info.split(",")[0];
        int companyNumber = Integer.parseInt(info.split(",")[1]);
        String paymentMethod = info.split(",")[2];
        String bankAccount = info.split(",")[3];
        ServiceController.getInstance().register(name, companyNumber, paymentMethod, bankAccount, items, contacts);
    }

    static void showSupplierInfo(){
        ArrayList<String[]> sups = ServiceController.getInstance().getSuppliersInfo();
        for (int i = 0; i < sups.size(); i++) {
            OutputService.getInstance().println(i + ": " + Arrays.toString(sups.get(i)));
        }
    }

    static void createOrder(int supplierNum, String deliOptions, List<String> items){
        ServiceController.getInstance().createOrder(supplierNum, deliOptions, items);
    }

    static void manageQuantityWriter(){
        OutputService.getInstance().println("Do you need a Quantity Writer?");

    }

    static void showOptions(){
        String[] options = new String[]{
          "Add Supplier", "Create Order"
        };
        for(int i = 0; i < options.length; i++){
            OutputService.getInstance().println(i + ") " + options[i]);
        }
    }
}