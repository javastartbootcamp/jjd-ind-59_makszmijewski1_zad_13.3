package pl.javastart.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<String, Currency> currencies = createListOfCurrencies();
        List<Product> products = createListOfProducts();
        savePriceInEuro(products, currencies);
        showSumInEuro(products);
        showAveragePrice(products);
        showMostExpensiveProductInEuro(products);
        showCheapestProduct(products);
    }

    private static void showCheapestProduct(List<Product> products) {
        BigDecimal lowestPrice = products.get(0).getPriceInEuro();
        for (Product product : products) {
            if (product.getPriceInEuro().compareTo(lowestPrice) < 0) {
                lowestPrice = product.getPriceInEuro();
            }
        }
        System.out.println(lowestPrice);
    }

    private static void showMostExpensiveProductInEuro(List<Product> products) {
        BigDecimal maxPrice = products.get(0).getPriceInEuro();
        for (Product product : products) {
            if (product.getPriceInEuro().compareTo(maxPrice) > 0) {
                maxPrice = product.getPriceInEuro();
            }
        }
        System.out.println(maxPrice);
    }

    private static void showAveragePrice(List<Product> products) {
        BigDecimal sum = new BigDecimal(0);
        for (Product product : products) {
            sum = sum.add(product.getPriceInEuro());
        }
        BigDecimal numberOfProducts = BigDecimal.valueOf(products.size());
        BigDecimal average = sum.divide(numberOfProducts, 2, RoundingMode.HALF_UP);
        System.out.println(average);
    }

    private static void savePriceInEuro(List<Product> products, Map<String, Currency> currencies) {
        for (Product product : products) {
            product.setPriceInEuro(calculatePriceInEuro(product, currencies));
        }
    }

    private static void showSumInEuro(List<Product> products) {
        BigDecimal sum = new BigDecimal(0);
        for (Product product : products) {
            sum = sum.add(product.getPriceInEuro());
        }
        System.out.println(sum);
    }

    private static Map<String, Currency> createListOfCurrencies() {
        Map<String, Currency> currencies = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/currencies.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Currency newCureency = createNewCurrency(line);
                currencies.put(newCureency.getDesignation(), newCureency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    private static Currency createNewCurrency(String line) {
        String[] values = line.split(";");
        return new Currency(values[0], new BigDecimal(values[1]));
    }

    private static List<Product> createListOfProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/products.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(createNewProduct(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static Product createNewProduct(String line) {
        String[] values = line.split(";");
        return new Product(values[0], values[2], new BigDecimal(values[1]));
    }

    private static BigDecimal calculatePriceInEuro(Product product, Map<String, Currency> currencies) {
        Currency currency = currencies.get(product.getCurrency());
        return product.getPriceInCurrency().multiply(currency.getRateToEur());
    }
}
