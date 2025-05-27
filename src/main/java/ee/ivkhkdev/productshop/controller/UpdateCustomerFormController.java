package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.services.AppUserService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UpdateCustomerFormController {

    @FXML private TextField tfCustomerName;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;
    @FXML private Label lbInfo;
    @FXML private TableView<AppUser> tvUsers;
    @FXML private TableColumn<AppUser, Long> tcId;
    @FXML private TableColumn<AppUser, String> tcUsername;
    @FXML private TableColumn<AppUser, AppUser.Role> tcRole;

    private final AppUserService appUserService;
    private final FormService formService;

    @Autowired
    public UpdateCustomerFormController(AppUserService appUserService, FormService formService) {
        this.appUserService = appUserService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        tcRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        loadUsers();
    }

    private void loadUsers() {
        List<AppUser> users = appUserService.getAllUsers();
        tvUsers.getItems().setAll(users);
    }

    @FXML
    private void handleUserDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            AppUser selectedUser = tvUsers.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                tfCustomerName.setText(selectedUser.getUsername());
                tfLogin.setText(selectedUser.getUsername());
                tfPassword.setText(selectedUser.getPassword());
            }
        }
    }

    @FXML
    private void updateCustomer() {
        String name = tfCustomerName.getText();
        String login = tfLogin.getText();
        String password = tfPassword.getText();

        if (name.isEmpty() || login.isEmpty() || password.isEmpty()) {
            lbInfo.setText("Все поля должны быть заполнены.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }

        AppUser selectedUser = tvUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setUsername(login);
            selectedUser.setPassword(password);
            appUserService.addOrUpdateCustomer(selectedUser);
            lbInfo.setText("Клиент обновлен.");
            lbInfo.setStyle("-fx-text-fill: green;");
            loadUsers();
        } else {
            lbInfo.setText("Выберите пользователя для обновления.");
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}