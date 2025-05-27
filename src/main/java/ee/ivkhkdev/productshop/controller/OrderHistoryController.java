package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.model.entity.PurchasedProduct;
import ee.ivkhkdev.productshop.services.PurchaseService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class OrderHistoryController {

    @FXML private TableView<PurchasedProduct> tvOrderHistory;
    @FXML private TableColumn<PurchasedProduct, Long> tcId;
    @FXML private TableColumn<PurchasedProduct, String> tcCustomerName;
    @FXML private TableColumn<PurchasedProduct, String> tcProductName;
    @FXML private TableColumn<PurchasedProduct, Integer> tcQuantity;
    @FXML private TableColumn<PurchasedProduct, Double> tcPrice;
    @FXML private TableColumn<PurchasedProduct, LocalDate> tcDate;
    @FXML private Label lbDailyProfit;
    @FXML private Label lbMonthlyProfit;
    @FXML private Label lbYearlyProfit;

    private final PurchaseService purchaseService;
    private final FormService formService;

    @Autowired
    public OrderHistoryController(PurchaseService purchaseService, FormService formService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcCustomerName.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        tcProductName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        tcQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        tcPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        loadOrderHistory();
        updateProfits();
    }

    private void loadOrderHistory() {
        tvOrderHistory.getItems().setAll(purchaseService.getAllPurchases());
    }

    private void updateProfits() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate startOfYear = today.withDayOfYear(1);

        double dailyProfit = purchaseService.calculateProfit(today, today);
        double monthlyProfit = purchaseService.calculateProfit(startOfMonth, today);
        double yearlyProfit = purchaseService.calculateProfit(startOfYear, today);

        lbDailyProfit.setText("Доход за день: " + dailyProfit + " евро");
        lbMonthlyProfit.setText("Доход за месяц: " + monthlyProfit + " евро");
        lbYearlyProfit.setText("Доход за год: " + yearlyProfit + " евро");
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}