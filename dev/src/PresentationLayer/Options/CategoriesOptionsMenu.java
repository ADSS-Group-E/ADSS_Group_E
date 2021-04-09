package PresentationLayer.Options;

import BusinessLayer.Category;
import BusinessLayer.CategoryController;
import PresentationLayer.CategoryDTO;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represent the options menu of the categories.
 * The menu contains all the function that required:
 * Get category, List of all categories, Add category and remove category.
 * The instruction of this class can be found in the attached document.
 */

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

    /**
     * This function uses to get details of category by the ID of the category
     */
    public void getCategory(){
        System.out.println("Please enter the category id for the category you wish to display:");
        int cid = in.nextInt();
        CategoryDTO category = parentCLI.getFacade().getCategory(cid);
        System.out.println(category);
    }

    /**
     * This function uses to get list of all the categories that exist
     */
    public void getCategoryList(){
        ArrayList<CategoryDTO> DTOlist = parentCLI.getFacade().getCategoryList();
        System.out.printf("%-10s %s%n", "CID","Name");
        DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getCid(),DTO.getName()));
    }

    /**
     * This function uses to add a new category
     * It's require to insert ID, name, and super-category if exist.
     */
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

    /**
     * This function uses to remove category by ID
     */
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
