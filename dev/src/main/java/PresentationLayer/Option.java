package PresentationLayer;

public class Option {
    String description;
    Runnable runOption;

    public Option(String description, Runnable runOption) {
        this.description = description;
        this.runOption = runOption;
    }

    public void run(){
        runOption.run();
    }

    public String getDescription() {
        return description;
    }
}
