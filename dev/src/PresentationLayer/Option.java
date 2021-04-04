package PresentationLayer;

public class Option {
    int choiceNum;
    String description;
    Runnable runOption;

    public Option(int choiceNum, String description, Runnable runOption) {
        this.choiceNum = choiceNum;
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
