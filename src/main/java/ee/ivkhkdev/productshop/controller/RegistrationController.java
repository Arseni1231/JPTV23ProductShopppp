package ee.ivkhkdev.productshop.controller;

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
public class RegistrationController {

    @FXML private TextField tfName;
    @FXML private TextField tfLogin;
    @FXML private PasswordField tfPassword;
    @FXML private Label lbInfo;

    private final AppUserService appUserService;
    private final FormService formService;

    @Autowired
    public RegistrationController(AppUserService appUserService, FormService formService) {
        this.appUserService = appUserService;
        this.formService = formService;
    }

    @FXML
    private void registrationUser() {
        String name = tfName.getText();
        String login = tfLogin.getText();
        String password = tfPassword.getText();

        if (name.isEmpty() || login.isEmpty() || password.isEmpty()) {
            lbInfo.setText("Все поля должны быть заполнены.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        AppUser newUser = new AppUser(login, password, AppUser.Role.USER);
        appUserService.addOrUpdateCustomer(newUser);
        lbInfo.setText("Регистрация успешна.");
        lbInfo.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void goToLoginForm() {
        formService.loadLoginForm();
    }
}