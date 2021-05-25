package PresentationLayer.Workers_Transport;

import DataAccessLayer.Workers_Transport.Repo;

import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int choice;
        try {

            System.out.println("Welcome to the Workers and Delivery System Manager");
            do {
            System.out.println("Type 1 for Workers or 2 for Deliveries");
            choice = in.nextInt();
            Repo.openDatabase();
            if (choice == 1) {
                MenuWorkers.createSystem();
            }
            if (choice == 2)
                Menu_Transport.createSystem();
            } while (choice<1 || choice>2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
