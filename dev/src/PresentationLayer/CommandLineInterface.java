package PresentationLayer;


import BusinessLayer.Facade;

import java.util.Scanner;

public class CommandLineInterface {
    private Facade facade;
    private MainOptionsMenu mainOptionsMenu;

    public CommandLineInterface() {
        facade = new Facade();
        mainOptionsMenu= new MainOptionsMenu();
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("SUPER-LI Inventory Module");
        while (true){
            mainOptionsMenu.displayOptions();
            int choice = in.nextInt();
            mainOptionsMenu.chooseOption(choice);
        }
    }
}


