package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MenuFormController {

    @FXML
    private Button btnProductList;
    @FXML
    private Button btnCart;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnUserManagement;
    @FXML
    private Button btnManageProducts;
    @FXML
    private Button btnManageOrders;

    private final FormService formService;

    @Autowired
    public MenuFormController(FormService formService) {
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        // Проверяем роль текущего пользователя и настраиваем видимость кнопок
        AppUser currentUser = JPTV23ProductShopApplication.currentUser;
        if (currentUser != null && currentUser.getRole() == AppUser.Role.ADMIN) {

            btnUserManagement.setVisible(true);
            btnManageProducts.setVisible(true);  // Показываем кнопку для управления товарами
            btnManageOrders.setVisible(true);    // Показываем кнопку для управления заказами
        } else {
            // Если не администратор, скрываем кнопки для администрирования
            btnUserManagement.setVisible(false);
            btnManageProducts.setVisible(false);  // Скрываем кнопку для управления товарами
            btnManageOrders.setVisible(false);    // Скрываем кнопку для управления заказами
        }
    }

    @FXML
    private void showProductList() {
        formService.loadForm("/ee.ivkhkdev.PhoneShopJavaFX/product/productListForm.fxml", "Список товаров");
    }

    @FXML
    private void showBasket() {
        formService.loadForm("/ee.ivkhkdev.PhoneShopJavaFX/cart/basketForm.fxml", "Корзина");
    }

    @FXML
    private void logout() {
        JPTV23ProductShopApplication.currentUser = null;  // Сбрасываем текущего пользователя
        formService.loadLoginForm();  // Перенаправляем на страницу логина
    }


    @FXML
    private void showUserManagement() {
        formService.loadForm("/ee.ivkhkdev.PhoneShopJavaFX/customer/updateCustomerForm.fxml", "Управление пользователями");
    }

    // Новый метод для отображения формы управления товарами
    @FXML
    private void showManageProducts() {
        formService.loadForm("/ee.ivkhkdev.PhoneShopJavaFX/phone/updateProductForm.fxml", "Управление товарами");
    }

    @FXML
    private void showOrderHistory() {
        formService.loadForm("/ee.ivkhkdev.PhoneShopJavaFX/order/orderHistoryForm.fxml", "История заказов");
    }
}
