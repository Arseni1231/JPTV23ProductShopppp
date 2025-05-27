package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BasketController {

    @FXML private TableView<Product> tvBasket;
    @FXML private TableColumn<Product, String> tcName;
    @FXML private TableColumn<Product, Float> tcPrice;
    @FXML private TableColumn<Product, Integer> tcAmount;
    @FXML private Label lbInfo;
    @FXML private Label lbTotalPrice;

    private final FormService formService;
    private final ProductService productService;

    @Autowired
    public BasketController(FormService formService, ProductService productService) {
        this.formService = formService;
        this.productService = productService;
    }

    @FXML
    private void initialize() {
        tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tcAmount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        loadBasket();
    }

    private void loadBasket() {
        tvBasket.getItems().setAll(JPTV23ProductShopApplication.currentUser.getBasket().getProducts());
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = JPTV23ProductShopApplication.currentUser.getBasket().getTotalPrice();
        lbTotalPrice.setText("Итоговая сумма: " + totalPrice + " евро.");
    }

    @FXML
    private void removeFromBasket() {
        Product selectedProduct = tvBasket.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            JPTV23ProductShopApplication.currentUser.getBasket().removeProduct(selectedProduct);
            lbInfo.setText("Товар удален из корзины.");
            lbInfo.setStyle("-fx-text-fill: green;");
            loadBasket();
        } else {
            lbInfo.setText("Выберите товар для удаления из корзины.");
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void purchase() {
        try {
            for (Product product : JPTV23ProductShopApplication.currentUser.getBasket().getProducts()) {
                if (product.getId() == null) {
                    // Продукт без ID, возможно это новый продукт или ошибка в базе данных
                    throw new IllegalArgumentException("Продукт не имеет идентификатора: " + product.getName());
                }

                // Ищем продукт по ID в базе данных
                Product storedProduct = productService.getProductById(product.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Продукт не найден с ID: " + product.getId()));

                // Проверяем наличие достаточного количества товара
                if (storedProduct.getAmount() < product.getAmount()) {
                    throw new IllegalArgumentException("Недостаточно товара на складе для продукта с ID " + product.getId());
                }

                // Обновляем количество товара на складе
                storedProduct.setAmount(storedProduct.getAmount() - product.getAmount());
                productService.updateProduct(storedProduct);
            }

            // Очищаем корзину после успешной покупки
            JPTV23ProductShopApplication.currentUser.getBasket().clearBasket();
            lbInfo.setText("Покупка успешно завершена.");
            lbInfo.setStyle("-fx-text-fill: green;");
            loadBasket();

        } catch (IllegalArgumentException e) {
            lbInfo.setText(e.getMessage());
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }



    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}