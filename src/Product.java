import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Product {
    String name;
    private static List<Product> productsList;

    public String getName() {
        return name;
    }

    public abstract double getPrice(int year, int month) throws IndexOutOfBoundsException;

    public static void ClearProducts(){
        productsList = new ArrayList<Product>();
    }

    public static void addProducts(Function<Path, Product> fromCsv, Path dataDirectory){
        File directory = new File(dataDirectory.toString());

        File[] filesInDirectory = directory.listFiles();

        for(File file : filesInDirectory){
            productsList.add(fromCsv.apply(Paths.get(file.toString())));
        }
    }
}
