import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class NonFoodProduct extends Product{
    Double[] prices;

    private NonFoodProduct(String name, Double[] prices) {
        this.name = name;
        this.prices = prices;
    }

    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                    .map(value -> value.replace(",",".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                    .map(Double::valueOf) // konwertuję string na double
                    .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy

            scanner.close();

            return new NonFoodProduct(name, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(int year, int month){
        if(month < 1 || month > 12){
            throw new IndexOutOfBoundsException("Month number is not between 1 and 12");
        }

        if(year < 2010 || year > 2022){
            throw new IndexOutOfBoundsException("Dataset only contains data for years 2010-2022");
        }else if(year == 2022){
            if(month > 3){
                throw new IndexOutOfBoundsException("In 2022 dataset contains data for only 3 months");
            }
        }

        int dateOffset = (Math.abs(2010-year)*12)+month-1;
        return prices[dateOffset];
    }
}
