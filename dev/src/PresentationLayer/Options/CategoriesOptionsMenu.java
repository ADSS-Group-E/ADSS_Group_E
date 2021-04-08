package PresentationLayer.Options;

import BusinessLayer.Category;
import BusinessLayer.CategoryController;
import PresentationLayer.CategoryDTO;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class CategoriesOptionsMenu extends OptionsMenu{
    public CategoriesOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i=1;
        options.put(i++, new Option( "Get category",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the category id for the category you wish to display:");
            int cid = in.nextInt();
            CategoryDTO category = parentCLI.getFacade().getCategory(cid);
            System.out.println(category);
        }));

        options.put(i++, new Option( "List all categories",() -> {

            ArrayList<CategoryDTO> DTOlist = parentCLI.getFacade().getCategoryList();
            System.out.printf("%-10s %s%n", "CID","Name");
            DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getCid(),DTO.getName()));
        }));

        options.put(i++, new Option( "Add category",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the cid of the category you wish to add");
            int cid = in.nextInt();
            System.out.println("Please enter the name of the category you wish to add");
            String name = in.next();
            parentCLI.getFacade().addCategory(cid, name);
        }));

        options.put(i++, new Option( "Remove category",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the category id you wish to remove:");
            int pid = in.nextInt();
            System.out.println("Are you sure you want to remove the category? type \"1\" to remove.");
            int verify = in.nextInt();
            if (verify == 1) {
                parentCLI.getFacade().removeCategory(pid);
            }
            else {
                System.out.println("Category did not removed");
            }
        }));
        options.put(i, new Option( "Back",() -> System.out.println("Going back.")));
    }
}
