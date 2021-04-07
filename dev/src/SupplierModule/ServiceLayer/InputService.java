package SupplierModule.ServiceLayer;

import java.util.InputMismatchException;
import java.util.Scanner;

final class InputService {//The class controls the input received from the user
    private Scanner scanner = new Scanner(System.in);

    private static InputService instance = null;
    private InputService(){

    }

    static InputService getInstance(){
        if(instance == null)
            instance = new InputService();
        return instance;
    }

    String next(String message){
        boolean retry = true;
        String nextString = "";
        while (retry) {
            try {
                OutputService.getInstance().print(message);
                nextString = scanner.nextLine();
                retry = false;
            } catch (Exception e) {
                if (!scanner.hasNext()) {
                    scanner.nextLine();
                }
                OutputService.getInstance().println("Please try again.");
            }
        }
        return nextString;
    }

    int nextInt(String message){
        boolean retry = true;
        int nextInt = 0;
        while (retry) {
            try {
                OutputService.getInstance().print(message);
                nextInt = Integer.parseInt(scanner.nextLine());
                if (nextInt < 0)
                    throw new InputMismatchException("Number was negative");
                retry = false;
            } catch (Exception e) {
                OutputService.getInstance().println("Please try again.");
            }
        }
        return nextInt;
    }

    boolean nextBoolean(String message){
        boolean retry = true;
        boolean nextBool = false;
        while (retry) {
            try {
                OutputService.getInstance().print(message);
                String ans = scanner.nextLine();
                if (!ans.equalsIgnoreCase("true") && !ans.equalsIgnoreCase("false")) {
                    throw new InputMismatchException("String is not true or false");
                }
                nextBool = Boolean.parseBoolean(ans.toLowerCase());
                retry = false;
            } catch (Exception e) {
                OutputService.getInstance().println("Please try again.");
            }
        }
        return nextBool;
    }
}
