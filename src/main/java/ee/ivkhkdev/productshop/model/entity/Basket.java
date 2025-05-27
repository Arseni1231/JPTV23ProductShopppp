package ee.ivkhkdev.productshop.model.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Basket {

    private final ObservableList<Product> products = FXCollections.observableArrayList();

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void clearBasket() {
        products.clear();
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(product -> product.getPrice() * product.getAmount()).sum();
    }
}