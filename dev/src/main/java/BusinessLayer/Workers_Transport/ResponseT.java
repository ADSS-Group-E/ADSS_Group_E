package BusinessLayer.Workers_Transport;

public class ResponseT<T> extends Response{
    private T Value;

    public ResponseT (T Value,String ErrorMessage){
        super(ErrorMessage);
        this.Value=Value;
    }

    public ResponseT(T value){
        super("");
        this.Value=value;
    }

    public T getValue() {
        return Value;
    }

    public void setValue(T value) {
        Value = value;
    }

}
