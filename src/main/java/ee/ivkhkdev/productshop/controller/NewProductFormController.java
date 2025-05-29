package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NewProductFormController {

    @FXML private TextField tfName;
    @FXML private TextField tfPrice;
    @FXML private TextField tfAmount;
    @FXML private Label lbInfo;

    private final ProductService productService;
    private final FormService formService;

    @Autowired
    public NewProductFormController(ProductService productService, FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }


    @FXML
    private void addProduct() {
        String name = tfName.getText();
        String priceText = tfPrice.getText();
        String amountText = tfAmount.getText();

        if (name.isEmpty() || priceText.isEmpty() || amountText.isEmpty()) {
            lbInfo.setText("Все поля должны быть заполнены.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            float price = Float.parseFloat(priceText);
            int amount = Integer.parseInt(amountText);

            if (price <= 0 || amount <= 0) {
                lbInfo.setText("Цена и количество должны быть положительными.");
                lbInfo.setStyle("-fx-text-fill: red;");
                return;
            }

            // Создаем продукт и сохраняем в базу данных
            Product product = new Product(name, price, amount);
            Product savedProduct = productService.addProduct(product);

            // Добавляем в корзину продукт С ID
            JPTV23ProductShopApplication.currentUser.getBasket().getProducts().add(savedProduct);

            lbInfo.setText("Товар добавлен в корзину (ID: " + savedProduct.getId() + ")");
            lbInfo.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            lbInfo.setText("Ошибка: " + e.getMessage());
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }


}