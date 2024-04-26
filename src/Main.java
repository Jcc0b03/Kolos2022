import java.nio.file.Paths;

public class Main{
    public static void main(String[] args) {
        System.out.println("Hello world");

        FoodProduct foodProduct = FoodProduct.fromCsv(Paths.get("data/food/buraki.csv"));
        System.out.println(foodProduct.getPrice(2010, 1, "ZACHODNIOPOMORSKIE"));
        System.out.println(foodProduct.getPrice(2022, 3));

        NonFoodProduct nonFoodProduct = NonFoodProduct.fromCsv(Paths.get("data/nonfood/benzyna.csv"));
        System.out.println(nonFoodProduct.getPrice(2010, 1));

        Product.ClearProducts();
        Product.addProducts(NonFoodProduct::fromCsv, Paths.get("data/nonfood"));
    }
}