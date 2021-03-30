package SupplierModule.ServiceLayer;

public final class OutputService {
    private static OutputService instance = null;
    private OutputService(){

    }

    public static OutputService getInstance(){
        if(instance == null)
            instance = new OutputService();
        return instance;
    }

    void println(String arg){
        System.out.println(arg);
    }
}
