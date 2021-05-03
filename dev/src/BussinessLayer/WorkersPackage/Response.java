package BussinessLayer.WorkersPackage;

public class Response {
    private String ErrorMessage;
    public Response(){
        ErrorMessage="";
    }

    public Response(String errorMessage){
        ErrorMessage=errorMessage;
    }

    public boolean isErrorOccurred(){
        return ErrorMessage!=null&&ErrorMessage!="";
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
