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
    private void goToMainForm() {
        formService.loadMainForm();
    }
}