package BuisnessLayer;

public class Response<T> {

    private T value;
    private String errorMessage;

    public  Response(T value, String errorMessage){
        this.value=value;
        this.errorMessage=errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Response{" +
                "value=" + value +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
