package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MenuController {

    private final FormService formService;

    @Autowired
    public MenuController(FormService formService) {
        this.formService = formService;
    }

    @FXML
    private void showNewProductForm() {
        formService.loadNewProductForm();
    }

    @FXML
    private void showListProducts() {
        formService.loadMainForm();
    }

    @FXML
    private void showUpdateProductForm() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/products/updateProductForm.fxml", "Редактирование товара");
    }

    @FXML
    private void showNewCustomerForm() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/customer/newCustomerForm.fxml", "Добавление клиента");
    }

    @FXML
    private void showListCustomers() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/customer/listCustomersForm.fxml", "Список клиентов");
    }

    @FXML
    private void showUpdateCustomerForm() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/customer/updateCustomerForm.fxml", "Редактирование клиента");
    }

    @FXML
    private void processPurchase() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/purchase/processPurchaseForm.fxml", "Покупка товара");
    }

    @FXML
    private void showListPurchases() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX/purchase/listPurchasesForm.fxml", "Список покупок");
    }

    @FXML
    private void calculateProfit() {
        formService.loadForm("/ee/ivkhkdev/ProductShopJavaFX//profit/calculateProfitForm.fxml", "Расчет прибыли");
    }



}