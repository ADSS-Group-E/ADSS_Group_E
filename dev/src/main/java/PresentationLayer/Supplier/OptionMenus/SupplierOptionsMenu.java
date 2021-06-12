package PresentationLayer.Supplier.OptionMenus;

import BusinessLayer.Inventory.DomainObjects.Discount;
import BusinessLayer.Supplier.Controllers.SupplierFacade;
import BusinessLayer.Supplier.SupplierController;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;
import PresentationLayer.Supplier.DataTransferObjects.ContactDTO;
import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierDTO;
import PresentationLayer.Supplier.InputService;
import PresentationLayer.Supplier.OutputService;
import PresentationLayer.Supplier.PresentationHandler;
import PresentationLayer.Supplier.ServiceController;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierOptionsMenu extends OptionsMenu {
    private SupplierFacade supplierFacade;
    private SupplierProductOptionsMenu supplierProductOptionsMenu;

    public SupplierOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;

        options.put(i++, new Option("Add supplier", this::addSupplier));
        options.put(i++, new Option("List all suppliers", this::listSuppliers));
        options.put(i++, new Option("Set supplier's quantity writer", this::setQuantityWriter));
        options.put(i++, new Option("Set supplier's contacts", this::setContacts));
        options.put(i++, new Option("Supplier Product Options", supplierProductOptionsMenu::enter));
        options.put(i, new Option("Back", this::back));
    }

    private InputService in = InputService.getInstance();
    private OutputService out = OutputService.getInstance();
    private ServiceController service = new ServiceController(new SupplierController()); //initializes empty objects


    private void addSupplier(){
        //get supplier information
        String name = in.next("Enter supplier name: ");
        int companyNumber = in.nextInt("Enter company number: ");
        String bankAccount = in.next("Enter bank account: ");
        String paymentMethod = in.next("Enter payment method: ");
        SupplierDTO supplierDTO = new SupplierDTO(companyNumber, name, bankAccount, paymentMethod);

        supplierFacade.addSupplier(supplierDTO);
    }

    private void listSuppliers(){
        ArrayList<SupplierDTO> supplierDTOS = supplierFacade.getAllSuppliers();
        supplierDTOS.forEach(System.out::println);
    }

    private void setQuantityWriter(){
        int companyNumber = in.nextInt("Enter company number: ");
        int regCostumer = in.nextInt("Regular costumer discount: %"); //asks for discount given to a regular costumer
        while (regCostumer >= service.getMaxDiscount()) { //while the max discount is illegal
            out.println("Please try again.");
            regCostumer = in.nextInt("Regular costumer discount: %"); //try again
        }
        int minPrice = in.nextInt("Minimum buy price for discount: "); //minimum order price for discount to happen
        ArrayList<DiscountStepDTO> discountSteps = createDiscountList(); //creates a discount list
        QuantityWriterDTO quantityWriterDTO = new QuantityWriterDTO(companyNumber, regCostumer, minPrice);
        supplierFacade.setSupplierQuantityWriter(quantityWriterDTO);
        discountSteps.forEach((discountStep)-> supplierFacade.addSupplierQuantityWriterDiscountStep(companyNumber, discountStep));
    }

    private ArrayList<DiscountStepDTO> createDiscountList() {
        HashMap<Integer, DiscountStepDTO>  discountSteps = new HashMap<>();
        // creates a discount step list for the quantity writer
        int maxDiscount = service.getMaxDiscount(); //gets a constant number that is in the system, usually 100 that suggests 100% discount is max
        String ans = in.next("Add discount steps? N/Y "); //asks user if to add another discount
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                out.println("Existing discounts: ");
                for (Integer i : discountSteps.keySet()) { //print existing discount
                    out.println("[Price: " + i + ", Discount: %" + discountSteps.get(i) + "]");
                }
                int cash = in.nextInt("Enter amount of money: ");
                while (discountSteps.containsKey(cash)) { //if there is a step containing the amount of money
                    out.println("Please try again.");
                    cash = in.nextInt("Enter amount of money: %");
                }
                int discount = in.nextInt("Enter amount of discount: %");
                while (discount >= service.getMaxDiscount()) {
                    out.println("amount must be between 0-" + (service.getMaxDiscount() - 1)); //prints what the currect range the discount should be
                    out.println("Please try again.");
                    discount = in.nextInt("Enter amount of discount: %");
                }
                discountSteps.put(cash, new DiscountStepDTO(cash, discount)); //if there is no step for the amount of money we add it
                out.print("Add another step? N/Y "); //asks the user for another step to add
            }
            else {
                out.println("Please try again.");
            }
            ans = in.next("");
        }
        return new ArrayList<>(discountSteps.values());
    }

    private void setContacts(){
        int companyNumber = in.nextInt("Enter company number: ");
        //creates a list of contacts for the supplier
        ArrayList<ContactDTO> list = new ArrayList<>();
        String ans = "Y";
        while (!ans.equalsIgnoreCase("N")) {
            if (ans.equalsIgnoreCase("Y")) {
                out.println("Existing contacts: ");
                for (ContactDTO contact : list) { //prints existing contacts
                    out.println("[Name: " + contact.getName()+ ", Email: " + contact.getEmail() + "]");
                }
                //get new contact information
                String name = in.next("Enter Contact Name: ");
                String email = in.next("Enter Contact Email: ");
                ContactDTO newContact = new ContactDTO(companyNumber, name,email); //create a contact
                list.add(newContact);
                out.print("Add another Contact? N/Y "); //we ask user to add another contact
            }
            else {
                out.println("Please try again.\n");
            }
            ans = in.next("");
        }

        list.forEach((contactDTO) -> supplierFacade.addSupplierContact(contactDTO));
    }
}
