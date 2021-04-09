package Tests;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class tests {

    public static void main(String[] args)
    {
        try {
            JUnitCore junit = new JUnitCore();
            junit.addListener(new TextListener(System.out));
            junit.run(DeliveryTest.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
