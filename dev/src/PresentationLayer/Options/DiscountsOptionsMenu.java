package PresentationLayer.Options;

import PresentationLayer.CategoryDTO;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.DiscountDTO;
import PresentationLayer.ProductDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class DiscountsOptionsMenu extends OptionsMenu{

    public DiscountsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Get discount", () -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the discount id for the discount you wish to display:");
            int did = in.nextInt();
            DiscountDTO discount = parentCLI.getFacade().getDiscount(did);
            System.out.println(discount);
        }));

        options.put(i++, new Option("List all discounts", () -> {
            ArrayList<DiscountDTO> DTOlist = parentCLI.getFacade().getDiscountList();
            System.out.printf("%-10s %s%n", "DID","Name");
            DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getDid(),DTO.getName()));
        }));

        options.put(i++, new Option("Add discount", () -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Discount type?\n1 => Buying\n2 => Selling");
            int choice = in.nextInt();
            String type;
            switch (choice){
                case 1:
                    type = "Buying";
                    break;
                case 2:
                    type = "Selling";
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            // TODO validate entered input
            System.out.println("Please fill out the details for the new discount when prompted and press enter.");

            System.out.println("ID:");
            int did = in.nextInt();

            in.nextLine();
            System.out.println("Name:");
            String name = in.nextLine();

            System.out.println("Discount Percentage:");
            double discountPercentage = in.nextDouble();

            System.out.println("Start Year:");
            int startYear = in.nextInt();

            System.out.println("Start Month:");
            int startMonth = in.nextInt();

            System.out.println("Start Day:");
            int startDay = in.nextInt();

            LocalDateTime startDate = LocalDate.of(startYear,startMonth,startDay).atStartOfDay();

            System.out.println("End Year:");
            int endYear = in.nextInt();

            System.out.println("End Month:");
            int endMonth = in.nextInt();

            System.out.println("End Day:");
            int endDay = in.nextInt();

            LocalDateTime endDate = LocalDate.of(endYear,endMonth,endDay).atStartOfDay();

            ArrayList<Integer> cids = new ArrayList<>();
            ArrayList<Integer> pids = new ArrayList<>();
            in.nextLine();
            System.out.println("Enter CIDs of categories you wish to include (separated by spaces), or press enter to skip.");
            String[] lineVector = in.nextLine().split(" ");
            for (String cid : lineVector) {
                try{
                    if (!cid.equals(""))
                        cids.add(Integer.parseInt(cid));
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input - " + e.getMessage());
                }
            }

            System.out.println("Enter PIDs of specific products you wish to include (separated by spaces), or press enter to skip.");
            lineVector = in.nextLine().split(" ");
            for (String pid : lineVector) {
                try{
                    if (!pid.equals(""))
                        pids.add(Integer.parseInt(pid));
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid input - " + e.getMessage());
                }
            }

            if (cids.isEmpty() && pids.isEmpty()){
                System.out.println("Can't make empty discount.");
                return;
            }

            parentCLI.getFacade().addDiscount(did,name,discountPercentage,startDate,endDate,cids,pids,type);
        }));

        options.put(i++, new Option("Remove discount", () -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the discount id you wish to remove:");
            int did = in.nextInt();
            System.out.println("Are you sure you want to remove the discount? Enter \"y\" to remove.");
            String verify = in.next().trim();
            if (verify.equals("y")) {
                parentCLI.getFacade().removeDiscount(did);
            }
            else {
                System.out.println("Cancelled.");
            }
        }));

        options.put(i, new Option( "Back", this::back));
    }
}
