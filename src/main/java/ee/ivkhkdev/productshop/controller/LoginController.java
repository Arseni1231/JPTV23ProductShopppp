package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.services.AppUserService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private Label lbInfo;

    private final AppUserService appUserService;
    private final FormService formService;

    @Autowired
    public LoginController(AppUserService appUserService, FormService formService) {
        this.appUserService = appUserService;
        this.formService = formService;
    }

    @FXML
    private void handleLogin() {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        AppUser user = appUserService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            JPTV23ProductShopApplication.currentUser = user;
            formService.loadMainForm();
        } else {
            lbInfo.setText("Неверный логин или пароль.");
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToRegistration() {
        formService.loadRegistrationForm();
    }
}
