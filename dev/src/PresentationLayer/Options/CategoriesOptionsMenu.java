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
        options.put(i++, new Option( "Get category",this::getCategory));

        options.put(i++, new Option( "List all categories",this::getCategoryList));

        options.put(i++, new Option( "Add category",this::addCategory));

        options.put(i++, new Option( "Remove category",this::removeCategory));

        options.put(i, new Option( "Back", this::back));
    }

    public void getCategory(){
        System.out.println("Please enter the category id for the category you wish to display:");
        int cid = in.nextInt();
        CategoryDTO category = parentCLI.getFacade().getCategory(cid);
        System.out.println(category);
    }

    public void getCategoryList(){
        ArrayList<CategoryDTO> DTOlist = parentCLI.getFacade().getCategoryList();
        System.out.printf("%-10s %s%n", "CID","Name");
        DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getCid(),DTO.getName()));
    }

    public void addCategory(){
        System.out.println("Please enter the ID of the category you wish to add");
        int cid = in.nextInt();
        in.nextLine();
        System.out.println("Please enter the name of the category you wish to add");
        String name = in.nextLine();
        System.out.println("Please enter the ID of this category's super-category, or press enter to skip.");
        try{
            int superCategoryId;
            superCategoryId = Integer.parseInt(in.nextLine());
            parentCLI.getFacade().addCategory(new CategoryDTO(cid, name,superCategoryId));
        }
        catch(NumberFormatException e){
            parentCLI.getFacade().addCategory(cid,name);
        }
    }

    public void removeCategory(){
        System.out.println("Please enter the category id you wish to remove:");
        int cid = in.nextInt();
        System.out.println("Are you sure you want to remove the category? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            parentCLI.getFacade().removeCategory(cid);
        }
        else {
            System.out.println("Cancelled.");
        }
    }
}
