package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.tools.FormService;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.services.PurchaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class PurchaseController {

    @FXML private TableView<Product> tvListProducts;
    @FXML private TableColumn<Product, Long> tcId;
    @FXML private TableColumn<Product, String> tcName;
    @FXML private TableColumn<Product, Float> tcPrice;
    @FXML private TableColumn<Product, Integer> tcAmount;
    @FXML private Label lbInfo;

    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final FormService formService;

    @Autowired
    public PurchaseController(ProductService productService, PurchaseService purchaseService, FormService formService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tcAmount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        loadProducts();
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
    private void handleProductDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Product selectedProduct = tvListProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct != null && selectedProduct.getAmount() > 0) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Покупка товара");
                dialog.setHeaderText("Покупка: " + selectedProduct.getName());
                dialog.setContentText("Введите количество:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(quantityStr -> {
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        if (quantity > 0 && quantity <= selectedProduct.getAmount()) {
                            purchaseService.processPurchase(selectedProduct, quantity);
                            lbInfo.setText("Покупка успешно завершена!");
                            lbInfo.setStyle("-fx-text-fill: green;");
                            loadProducts();
                        } else {
                            lbInfo.setText("Некорректное количество!");
                            lbInfo.setStyle("-fx-text-fill: red;");
                        }
                    } catch (NumberFormatException e) {
                        lbInfo.setText("Введите корректное число!");
                        lbInfo.setStyle("-fx-text-fill: red;");
                    }
                });
            } else {
                lbInfo.setText("Товар отсутствует на складе.");
                lbInfo.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
