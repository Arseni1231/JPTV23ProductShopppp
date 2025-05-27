package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.tools.FormService;
import ee.ivkhkdev.productshop.services.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {

    @FXML private TextField tfProductName;
    @FXML private TextField tfProductPrice;
    @FXML private TextField tfProductAmount;
    @FXML private Label lbInfo;

    private final ProductService productService;
    private final FormService formService;

    @Autowired
    public ProductController(ProductService productService, FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }

    @FXML
    private void addProduct() {
        if (JPTV23ProductShopApplication.currentUser.getRole() != AppUser.Role.ADMIN) {
            lbInfo.setText("У вас нет прав для добавления товара.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        String name = tfProductName.getText().trim();
        String priceText = tfProductPrice.getText().trim();
        String amountText = tfProductAmount.getText().trim();
        String brand = "DefaultBrand"; // Укажите значение для brand

        if (name.isEmpty() || priceText.isEmpty() || amountText.isEmpty()) {
            lbInfo.setText("Все поля должны быть заполнены!");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            float price = Float.parseFloat(priceText);
            int amount = Integer.parseInt(amountText);

            if (price <= 0 || amount <= 0) {
                lbInfo.setText("Цена и количество должны быть положительными!");
                lbInfo.setStyle("-fx-text-fill: red;");
                return;
            }

            Product product = new Product(name, price, amount);
            productService.addProduct(product);

            lbInfo.setText("Товар успешно добавлен!");
            lbInfo.setStyle("-fx-text-fill: green;");
            formService.loadMainForm();

        } catch (NumberFormatException e) {
            lbInfo.setText("Некорректный формат цены или количества!");
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
