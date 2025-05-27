package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UpdateProductFormController {

    @FXML private TableView<Product> tvPhones;
    @FXML private TableColumn<Product, Long> tcId;
    @FXML private TableColumn<Product, String> tcName;
    @FXML private TableColumn<Product, Integer> tcQuantity;
    @FXML private TableColumn<Product, Float> tcPrice;
    @FXML private TextField tfId;
    @FXML private TextField tfPhoneName;
    @FXML private TextField tfQuantity;
    @FXML private TextField tfPrice;
    @FXML private Label lbInfo;

    private final ProductService productService;
    private final FormService formService;

    @Autowired
    public UpdateProductFormController(ProductService productService, FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcQuantity.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        tcPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        loadProducts();
    }

    private void loadProducts() {
        List<Product> products = productService.getAllProducts();
        tvPhones.getItems().setAll(products);
    }

    @FXML
    private void handlePhoneDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Product selectedProduct = tvPhones.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                tfId.setText(selectedProduct.getId().toString());
                tfPhoneName.setText(selectedProduct.getName());
                tfQuantity.setText(String.valueOf(selectedProduct.getAmount()));
                tfPrice.setText(String.valueOf(selectedProduct.getPrice()));
            }
        }
    }

    @FXML
    private void updatePhone() {
        try {
            Long id = Long.parseLong(tfId.getText());
            String name = tfPhoneName.getText();
            int quantity = Integer.parseInt(tfQuantity.getText());
            float price = Float.parseFloat(tfPrice.getText());

            Product product = new Product(name, price, quantity);
            product.setId(id);
            productService.updateProduct(product);

            lbInfo.setText("Продукт обновлен.");
            lbInfo.setStyle("-fx-text-fill: green;");
            loadProducts();
        } catch (NumberFormatException e) {
            lbInfo.setText("Некорректный формат данных.");
            lbInfo.setStyle("-fx-text-fill: red;");
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