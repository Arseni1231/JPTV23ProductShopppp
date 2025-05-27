package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainFormController {

    @FXML private TableView<Product> tvListProducts;
    @FXML private TableColumn<Product, Long> tcId;
    @FXML private TableColumn<Product, String> tcName;
    @FXML private TableColumn<Product, Float> tcPrice;
    @FXML private TableColumn<Product, Integer> tcAmount;
    @FXML private TextField tfQuantity;
    @FXML private Label lbInfo;  // Добавим метку для вывода сообщений

    private final ProductService productService;
    private final FormService formService;

    @Autowired
    public MainFormController(ProductService productService, FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        // Настроим отображение свойств для таблицы
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tcAmount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        loadProducts();
    }

    // Загрузить список продуктов
    private void loadProducts() {
        tvListProducts.getItems().setAll(productService.getAllProducts());
    }

    // Добавление товара в корзину
    @FXML
    private void addToBasket() {
        Product selectedProduct = tvListProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                int quantity = Integer.parseInt(tfQuantity.getText());
                if (quantity > 0) {
                    // Добавление продукта в корзину
                    JPTV23ProductShopApplication.currentUser.getBasket().addProduct(new Product(selectedProduct.getName(), selectedProduct.getPrice(), quantity));
                    lbInfo.setText("Товар добавлен в корзину.");
                    lbInfo.setStyle("-fx-text-fill: green;");
                } else {
                    lbInfo.setText("Количество должно быть положительным.");
                    lbInfo.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException e) {
                lbInfo.setText("Некорректный формат количества.");
                lbInfo.setStyle("-fx-text-fill: red;");
            }
        } else {
            lbInfo.setText("Выберите товар для добавления в корзину.");
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    // Обработка двойного щелчка по продукту
    @FXML
    private void handleProductDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Product selectedProduct = tvListProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                // В данном примере просто выводим информацию о выбранном товаре в консоль
                System.out.println("Продукт выбран: " + selectedProduct.getName());
                // Вы можете добавить здесь дополнительную логику, например, открыть форму с деталями товара
            }
        }
    }
}
