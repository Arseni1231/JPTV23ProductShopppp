package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductListController {

    @FXML private TableView<Product> tvListProducts;
    @FXML private TableColumn<Product, Long> tcId;
    @FXML private TableColumn<Product, String> tcName;
    @FXML private TableColumn<Product, Float> tcPrice;
    @FXML private TableColumn<Product, Integer> tcAmount;
    @FXML private Label lbInfo;

    private final ProductService productService;
    private final FormService formService;

    @Autowired
    public ProductListController(ProductService productService, FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tcAmount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        loadProducts();

        // Добавление обработчика двойного клика
        tvListProducts.setOnMouseClicked(this::handleProductDoubleClick);
    }

    @FXML
    private void handleProductDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Product selectedProduct = tvListProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                // Обработка двойного клика по продукту
                System.out.println("Двойной клик по продукту: " + selectedProduct.getName());
            }
        }
    }


    private void loadProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            lbInfo.setText("Список товаров пуст.");
            lbInfo.setStyle("-fx-text-fill: red;");
        } else {
            tvListProducts.getItems().setAll(products);
        }
    }

    @FXML
    private void addToCart() {
        Product selectedProduct = tvListProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            JPTV23ProductShopApplication.currentUser.getBasket().addProduct(selectedProduct);
            lbInfo.setText("Товар добавлен в корзину.");
            lbInfo.setStyle("-fx-text-fill: green;");
        } else {
            lbInfo.setText("Выберите товар для добавления в корзину.");
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}