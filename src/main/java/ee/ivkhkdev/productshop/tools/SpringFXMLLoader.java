package ee.ivkhkdev.productshop.tools;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class SpringFXMLLoader {

    private final ApplicationContext applicationContext;

    public SpringFXMLLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FXMLLoader load(String fxmlPath) {
        URL resource = getClass().getResource(fxmlPath);
        if (resource == null) {
            throw new IllegalArgumentException("FXML файл не найден по пути: " + fxmlPath);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader;
    }
}

