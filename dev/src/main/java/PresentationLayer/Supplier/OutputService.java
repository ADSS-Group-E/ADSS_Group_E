package PresentationLayer.Supplier;

public final class OutputService {//The class controls the output sent to the user
    private static OutputService instance = null;
    private OutputService(){

    }

    public static OutputService getInstance(){
        if(instance == null)
            instance = new OutputService();
        return instance;
    }

    public void println(String arg){
        System.out.println(arg);
    } //println

    public void print(String arg) {
        System.out.print(arg);
    } //print
}
