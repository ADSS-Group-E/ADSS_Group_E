package PresentationLayer.Inventory.OptionMenus;

import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Workers.DataTransferObjects.QualificationsDTO;

import java.util.ArrayList;

/**
 * This class represents the options menu for Categories.
 *
 * It displays a list of the various functions that a user can perform with Categories
 * and prompts the user for their selection of which function to activate.
 *
 * The listed functions include - Get a category, List all categories, Add a category and Remove a category.
 *
 * Once the user has selected a function, the class then executes the required activity accordingly.
 */

// Display the Category's list of functions and prompt the user for their selection.
public class CategoriesOptionsMenu extends OptionsMenu {
    public CategoriesOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i=1;
        options.put(i++, new Option( "Get category",this::getCategory));

        options.put(i++, new Option( "List all categories",this::getCategoryList));

        options.put(i++, new Option( "Add category",this::addCategory, QualificationsDTO.Storekeeper));

        options.put(i++, new Option( "Remove category",this::removeCategory, QualificationsDTO.Storekeeper));

        options.put(i, new Option( "Back", this::back));
    }

    /**
     * Prompt the user for the Category ID and then proceed to retrieve and display the Category's details accordingly
     */
    public void getCategory(){
        System.out.println("Please enter the Category ID for the category you wish to display:");
        int cid = in.nextInt();
        CategoryDTO category = parentCLI.getInventoryFacade().getCategory(cid);
        System.out.println(category);
    }

    /**
     * Display a list of all the Categories that exist
     */
    public void getCategoryList(){
        ArrayList<CategoryDTO> DTOlist = parentCLI.getInventoryFacade().getCategoryList();
        System.out.printf("%-10s %s%n", "CID","Name");
        DTOlist.forEach((DTO)->System.out.printf("%-10s %s%n", DTO.getCid(),DTO.getName()));
    }

    /**
     * This function adds a new Category by prompting the user to enter the required information one field at a time.
     *
     * The required data includes Category ID, name and Super-Category ID if relevant.
     *
     * Once entered, the InventoryFacade function is called to save the newly input category information.
     */
    public void addCategory(){
        System.out.println("Please enter the ID of the category you wish to add");
        int cid = in.nextInt();
        in.nextLine();
        System.out.println("Please enter the name of the category you wish to add");
        String name = in.nextLine();
        //Prompt for the optional super-category
        System.out.println("Please enter the ID of this category's super-category, or press enter to skip.");
        try{
            int superCategoryId;
            superCategoryId = Integer.parseInt(in.nextLine());
            // Add the new category with super-category by calling the InventoryFacade function with the data the user just entered.
            parentCLI.getInventoryFacade().addCategory(new CategoryDTO(cid, name,superCategoryId));
        }
        catch(NumberFormatException e){
            // Add the new category by calling the InventoryFacade function with the data the user just entered.
            parentCLI.getInventoryFacade().addCategory(cid,name);
        }
        System.out.println("The new category was added successfully.");
    }

    /**
     * This function prompts the user to end the ID of a category they wish to remove,
     * and then proceeds to remove the category by calling the InventoryFacade function
     */
    public void removeCategory(){
        System.out.println("Please enter the category id you wish to remove:");
        int cid = in.nextInt();
        System.out.println("Are you sure you want to remove the category? Enter \"y\" to remove.");
        String verify = in.next().trim();
        if (verify.equals("y")) {
            // Remove the category by calling the InventoryFacade function with the ID the user just entered.
            parentCLI.getInventoryFacade().removeCategory(cid);
            System.out.println("The category was removed successfully.");
        }
        else {
            System.out.println("Product removal was cancelled.");
        }
    }
}
