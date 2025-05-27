package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.model.entity.Product;
import ee.ivkhkdev.productshop.services.ProductService;
import ee.ivkhkdev.productshop.services.PurchaseService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ProcessPurchaseFormController {

    @FXML private TextField tfProductName;
    @FXML private TextField tfQuantity;
    @FXML private Label lbInfo;

    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final FormService formService;

    @Autowired
    public ProcessPurchaseFormController(ProductService productService, PurchaseService purchaseService, FormService formService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
        this.formService = formService;
    }

    @FXML
    private void processPurchase() {
        String productName = tfProductName.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(tfQuantity.getText());
        } catch (NumberFormatException e) {
            lbInfo.setText("Количество должно быть числом.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        Optional<Product> product = productService.getProductByName(productName);
        if (product.isEmpty()) {
            lbInfo.setText("Товар не найден.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            purchaseService.processPurchase(product.orElse(null), quantity);
            lbInfo.setText("Покупка успешно обработана.");
            lbInfo.setStyle("-fx-text-fill: green;");
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