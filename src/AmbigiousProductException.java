import java.util.List;

public class AmbigiousProductException extends Exception{
    public AmbigiousProductException(List<String> values){
        super(values.toString());
    }
}
