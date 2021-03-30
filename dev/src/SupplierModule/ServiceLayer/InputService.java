package SupplierModule.ServiceLayer;

import java.util.Scanner;

public final class InputService {
    Scanner scanner = new Scanner(System.in);

    private static InputService instance = null;
    private InputService(){

    }

    public static InputService getInstance(){
        if(instance == null)
            instance = new InputService();
        return instance;
    }

    String next(){
        return scanner.next();
    }

    int nextInt(){
        return scanner.nextInt();
    }
}
