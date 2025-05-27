package ee.ivkhkdev.productshop.tools;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FormService {

    private final ee.ivkhkdev.productshop.tools.SpringFXMLLoader springFXMLLoader;

    public FormService(ee.ivkhkdev.productshop.tools.SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    private Stage getPrimaryStage() {
        return JPTV23ProductShopApplication.primaryStage;
    }

    public void loadForm(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle(title);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки формы: " + fxmlPath, e);
        }
    }

    public void loadLoginForm() {
        FXMLLoader loader = springFXMLLoader.load("/ee/ivkhkdev/ProductShopJavaFX/login/login.fxml");
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Вход");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки login.fxml", e);
        }
    }



    public void loadRegistrationForm() {
        loadForm("/ee.ivkhkdev.ProductShopJavaFX/registration/registration.fxml", "Productshop - Регистрация пользователя");
    }

    public void loadMainForm() {
        loadForm("/ee.ivkhkdev.ProdcutShopJavaFX/main/mainForm.fxml", "Productshop - Главная");
    }

    public void loadNewProductForm() {
        loadForm("/ee.ivkhkdev.ProductShopJavaFX/product/newProductForm.fxml", "Productshop - Добавление товара");
    }

}