package SupplierModule.ServiceLayer;

import java.util.Scanner;

public final class InputService {//The class controls the input received from the user
    Scanner scanner = new Scanner(System.in);

    private static InputService instance = null;
    private InputService(){

    }

    public static InputService getInstance(){
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
                nextString = scanner.next();
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
                nextInt = scanner.nextInt();
                retry = false;
            } catch (Exception e) {
                if (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
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
                nextBool = scanner.nextBoolean();
                retry = false;
            } catch (Exception e) {
                if (!scanner.hasNextBoolean()) {
                    scanner.nextLine();
                }
                OutputService.getInstance().println("Please try again.");
            }
        }
        return nextBool;
    }
}
