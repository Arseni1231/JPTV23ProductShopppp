package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminPanelController {

    private final FormService formService;

    @Autowired
    public AdminPanelController(FormService formService) {
        this.formService = formService;
    }



    @FXML
    private void showUserManagement() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/customer/updateCustomerForm.fxml", "Управление пользователями");
    }

    @FXML
    private void showManageProducts() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/products/updateProductForm.fxml", "Управление товарами");
    }

    @FXML
    private void showOrderHistory() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/order/orderHistoryForm.fxml", "История заказов");
    }

    @FXML
    private void goToMainForm() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/menu/menuForm.fxml", "Главное меню");
    }
}