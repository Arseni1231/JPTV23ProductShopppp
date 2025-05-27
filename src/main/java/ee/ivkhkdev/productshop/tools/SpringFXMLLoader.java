package ee.ivkhkdev.productshop.tools;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringFXMLLoader {

    private ApplicationContext applicationContext;

    public SpringFXMLLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FXMLLoader load(String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader;
    }
}

