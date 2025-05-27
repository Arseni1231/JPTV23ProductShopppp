package ee.ivkhkdev.productshop.controller;

import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.services.AppUserService;
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ListCustomersFormController {

    @FXML private TableView<AppUser> tvCustomers;
    @FXML private TableColumn<AppUser, Long> tcId;
    @FXML private TableColumn<AppUser, String> tcUsername;
    @FXML private TableColumn<AppUser, AppUser.Role> tcRole;
    @FXML private Label lbInfo;

    private final AppUserService appUserService;
    private final FormService formService;

    @Autowired
    public ListCustomersFormController(AppUserService appUserService, FormService formService) {
        this.appUserService = appUserService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        tcRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        loadCustomers();
    }

    private void loadCustomers() {
        List<AppUser> customers = appUserService.getAllUsers();
        if (customers.isEmpty()) {
            lbInfo.setText("Список клиентов пуст.");
            lbInfo.setStyle("-fx-text-fill: red;");
        } else {
            tvCustomers.getItems().setAll(customers);
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}