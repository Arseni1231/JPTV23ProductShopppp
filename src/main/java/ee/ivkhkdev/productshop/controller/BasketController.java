package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.Basket;
import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BasketController {

    @FXML
    private TableView<Product> tvBasket;
    @FXML
    private TableColumn<Product, String> tcName;
    @FXML
    private TableColumn<Product, Float> tcPrice;
    @FXML
    private TableColumn<Product, Integer> tcAmount;
    @FXML
    private Label lbInfo;
    @FXML
    private Label lbTotalPrice;

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
            Basket basket = JPTV23ProductShopApplication.currentUser.getBasket();
            List<Product> basketProducts = new ArrayList<>(basket.getProducts());

            // 1. Проверка корзины
            if (basketProducts.isEmpty()) {
                throw new IllegalArgumentException("Корзина пуста");
            }

            // 2. Проверка наличия ID у всех продуктов
            for (Product product : basketProducts) {
                if (product.getId() == null) {
                    throw new IllegalStateException(
                            "Найден продукт без ID: " + product.getName() + ". " +
                                    "Добавляйте в корзину только сохранённые в БД товары!"
                    );
                }
            }

            // 3. Выполнение покупки
            productService.purchaseProducts(basketProducts);

            // 4. Очистка корзины
            basket.clearBasket();
            showSuccess("Покупка успешно завершена!");
            loadBasket();

        } catch (IllegalArgumentException | IllegalStateException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Системная ошибка при оформлении заказа");
            e.printStackTrace();
        }
    }

    private void showSuccess(String message) {
        lbInfo.setText(message);
        lbInfo.setStyle("-fx-text-fill: green;");
    }

    private void showError(String message) {
        lbInfo.setText(message);
        lbInfo.setStyle("-fx-text-fill: red;");
    }




    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}