package PresentationLayer.Options;

import PresentationLayer.CommandLineInterface;
import PresentationLayer.ProductDTO;

import java.util.Scanner;

public class ProductsOptionsMenu extends OptionsMenu{
    public ProductsOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);

        options.put(1, new Option( "Get product",() -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the product id for the product you wish to display:");
            int pid = in.nextInt();
            ProductDTO product = parentCLI.getFacade().getProduct(pid);
            System.out.println(product);
        }));
        options.put(2, new Option( "Test product two",() -> System.out.println("Test product two success!")));
        options.put(3, new Option( "Back",() -> System.out.println("Going back.")));
    }
}
