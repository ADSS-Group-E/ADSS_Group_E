package SupplierModule.ServiceLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class PresentationController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceController service = new ServiceController();
        service.initialize();
        final int numOfOptions = 5;
        //show user his options
        int input = scanner.nextInt();
        while (input >= 0 && input < numOfOptions) {
            //decrypts input
            switch (input){
                case (0) :{

                }
                case (1): {
                    ArrayList<String[]> sups = service.getSuppliersInfo();
                    for (int i = 0; i < sups.size(); i++) {
                        System.out.println(i + ": " + Arrays.toString(sups.get(i)));
                    }
                }
            }
            input = scanner.nextInt();
        }
    }
}