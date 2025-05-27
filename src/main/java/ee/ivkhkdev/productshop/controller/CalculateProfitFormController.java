package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.services.PurchaseService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class CalculateProfitFormController {

    @FXML private TextField tfStartDate;
    @FXML private TextField tfEndDate;
    @FXML private Label lbInfo;

    private final PurchaseService purchaseService;
    private final FormService formService;

    @Autowired
    public CalculateProfitFormController(PurchaseService purchaseService, FormService formService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
    }

    @FXML
    private void calculateProfit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse(tfStartDate.getText(), formatter);
            endDate = LocalDate.parse(tfEndDate.getText(), formatter);
        } catch (DateTimeParseException e) {
            lbInfo.setText("Неверный формат даты. Используйте yyyy-MM-dd.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        double profit = purchaseService.calculateProfit(startDate, endDate);
        lbInfo.setText("Прибыль: " + profit);
        lbInfo.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}