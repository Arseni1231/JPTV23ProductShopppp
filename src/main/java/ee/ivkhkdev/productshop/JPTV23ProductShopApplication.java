package ee.ivkhkdev.productshop;

import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.services.AppUserService;  // Добавляем сервис для работы с пользователями
import ee.ivkhkdev.productshop.tools.FormService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JPTV23ProductShopApplication extends Application {

    public static AppUser currentUser;
    public static Stage primaryStage;
    private static ConfigurableApplicationContext applicationContext;


    private AppUserService appUserService;

    public static void main(String[] args) {

        applicationContext = SpringApplication.run(JPTV23ProductShopApplication.class, args);
        launch();
    }

    @Override
    public void init() {

        appUserService = applicationContext.getBean(AppUserService.class);
    }

    @Override
    public void start(Stage primaryStage) {
        JPTV23ProductShopApplication.primaryStage = primaryStage;
        FormService formService = applicationContext.getBean(FormService.class);
        formService.loadLoginForm();


    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
