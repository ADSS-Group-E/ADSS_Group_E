package SupplierModule.ServiceLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class PresentationController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceController service = ServiceController.getInstance();
        service.initialize();
        final int numOfOptions = 5;
        //show user his options
        //showOptions();
        int input = scanner.nextInt();
        while (input >= 0 && input < numOfOptions) {
            //decrypts input
            switch (input){
                case (0) :{//register supplier
                    String info = scanner.next();
                    ArrayList<String> items = getInputIntoList();
                    ArrayList<String> contacts = getInputIntoList();
                    register(info, items, contacts);
                    break;
                }
                case (1): {//order
                    showSupplierInfo();
                    input = scanner.nextInt();
                    String deliOptions = scanner.next();
                    ArrayList<String> items = getInputIntoList();
                    createOrder(input, deliOptions, items);
                    break;
                }
                case (2): {//
                    break;
                }
            }
            input = scanner.nextInt();
        }
    }

    static ArrayList<String> getInputIntoList(){
        ArrayList<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        while(!next.equals("-1")){
            list.add(next);
            next = scanner.next();
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
            System.out.println(i + ": " + Arrays.toString(sups.get(i)));
        }
    }

    static void createOrder(int supplierNum, String deliOptions, List<String> items){
        ServiceController.getInstance().createOrder(supplierNum, deliOptions, items);
    }
}