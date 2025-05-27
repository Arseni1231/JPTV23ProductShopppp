package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.services.AppUserService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @FXML private TableView<AppUser> tvUsers;
    @FXML private TableColumn<AppUser, Long> tcId;
    @FXML private TableColumn<AppUser, String> tcUsername;
    @FXML private TableColumn<AppUser, AppUser.Role> tcRole;
    @FXML private Label lbInfo;

    private final AppUserService appUserService;
    private final FormService formService;

    @Autowired
    public UserController(AppUserService appUserService, FormService formService) {
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
        if (users.isEmpty()) {
            lbInfo.setText("Список пользователей пуст.");
            lbInfo.setStyle("-fx-text-fill: red;");
        } else {
            tvUsers.getItems().setAll(users);
        }
    }

    @FXML
    private void addUser() {
        if (JPTV23ProductShopApplication.currentUser.getRole() != AppUser.Role.ADMIN) {
            lbInfo.setText("У вас нет прав для добавления пользователей.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавить пользователя");
        dialog.setHeaderText("Введите имя пользователя:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(username -> {
            if (!username.trim().isEmpty()) {
                AppUser newUser = new AppUser(username, "password", AppUser.Role.USER);
                appUserService.addOrUpdateCustomer(newUser);
                lbInfo.setText("Пользователь добавлен.");
                lbInfo.setStyle("-fx-text-fill: green;");
                loadUsers();
            } else {
                lbInfo.setText("Имя пользователя не может быть пустым.");
                lbInfo.setStyle("-fx-text-fill: red;");
            }
        });
    }

    @FXML
    private void deleteUser() {
        if (JPTV23ProductShopApplication.currentUser.getRole() != AppUser.Role.ADMIN) {
            lbInfo.setText("У вас нет прав для удаления пользователей.");
            lbInfo.setStyle("-fx-text-fill: red;");
            return;
        }
        AppUser selectedUser = tvUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            appUserService.deleteUser(selectedUser.getId());
            lbInfo.setText("Пользователь удалён.");
            lbInfo.setStyle("-fx-text-fill: green;");
            loadUsers();
        } else {
            lbInfo.setText("Выберите пользователя для удаления.");
            lbInfo.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
