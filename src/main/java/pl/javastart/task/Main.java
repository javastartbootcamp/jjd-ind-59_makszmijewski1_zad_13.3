package pl.javastart.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Currency> currencies = createListOfCurrencies();
        List<Product> products = createListOfProducts();
        savePriceInEuro(products, currencies);
        showSumInEuro(products);
        showAveragePrice(products);
        showMostExpensiveProductInEuro(products);
        showCheapestProduct(products);
    }

    private static void showCheapestProduct(List<Product> products) {
        List<BigDecimal> prices = new ArrayList<>();
        for (Product product : products) {
            prices.add(product.getPriceInEuro());
        }
        Collections.sort(prices);
        System.out.println(prices.get(prices.size() - 1));
    }

    private static void showMostExpensiveProductInEuro(List<Product> products) {
        List<BigDecimal> prices = new ArrayList<>();
        for (Product product : products) {
            prices.add(product.getPriceInEuro());
        }
        Collections.sort(prices);
        System.out.println(prices.get(0));
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

    private static void savePriceInEuro(List<Product> products, List<Currency> currencies) {
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

    private static List<Currency> createListOfCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/currencies.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currencies.add(createNewCurrency(line));
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

    private static BigDecimal calculatePriceInEuro(Product product, List<Currency> currencies) {
        BigDecimal priceInEuro = new BigDecimal(0);
        for (Currency currency : currencies) {
            if (product.getCurrency().equals(currency.getDesignation())) {
                priceInEuro = product.getPriceInCurrency().multiply(currency.getRateToEur());
            }
        }
        return priceInEuro;
    }
}
