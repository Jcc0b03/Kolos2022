import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class FoodProduct extends Product{
    Double[] prices;
    String[] provinces;

    private FoodProduct(String name, Double[] prices, String[] provinces){
        this.name = name;
        this.prices = prices;
        this.provinces = provinces;
    }

    public static FoodProduct fromCsv(Path path){
        String name;
        Double[] prices;

        String[] provincesInFile = new String[16];

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli

            int currentLine = 0;
            List<Double> pricesForAllProvinces = new ArrayList<Double>();
            while(scanner.hasNextLine()){
                String[] rawData = scanner.nextLine().split(";");
                String provinceName = rawData[0];
                provincesInFile[currentLine] = provinceName;

                for(int i=1; i<rawData.length; i++){
                    pricesForAllProvinces.add(Double.parseDouble(rawData[i].replace(',', '.')));
                }

                currentLine += 1;
            }

            scanner.close();

            Double[] pricesForAllProvincesArray = new Double[pricesForAllProvinces.size()];
            pricesForAllProvinces.toArray(pricesForAllProvincesArray);
            return new FoodProduct(name, pricesForAllProvincesArray, provincesInFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    int findProvinceIndex(String provinceName){
        for(int i = 0; i< this.provinces.length; i++){
            if(Objects.equals(this.provinces[i], provinceName)){
                return i;
            }
        }

        return -1;
    }

    void validateDate(int year, int month) throws IndexOutOfBoundsException{
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
    }

    public double getPrice(int year, int month, String province){
        validateDate(year, month);

        int priceDateOffset = (year-2010)*12+month;

        int proviceOffset = findProvinceIndex(province) * 147;
        if(proviceOffset == -1){
            throw new IndexOutOfBoundsException("Province with given name not found");
        }

        return this.prices[priceDateOffset+proviceOffset-1];
    }

    public double getPrice(int year, int month){
        validateDate(year, month);

        double sum = 0.0;
        int provinceCounter = 0;
        for(String province : provinces){
            int provinceOffset = findProvinceIndex(province)*147;
            int dateOffset = (Math.abs(2010-year)*12+month)-1;

            sum += prices[provinceOffset+dateOffset];
            provinceCounter += 1;
        }

        return sum/provinceCounter;
    }
}
