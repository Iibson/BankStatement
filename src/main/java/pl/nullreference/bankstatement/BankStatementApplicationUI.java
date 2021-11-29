package pl.nullreference.bankstatement;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class BankStatementApplicationUI extends Application {

    private ConfigurableApplicationContext context;
    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext -> {
            genericApplicationContext.registerBean(Application.class, () -> BankStatementApplicationUI.this);
            genericApplicationContext.registerBean(Parameters.class, this::getParameters);
            genericApplicationContext.registerBean(HostServices.class, this::getHostServices);
        };

        this.context = new SpringApplicationBuilder().sources(BankstatementApplication.class)
                .build().run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }

    class StageReadyEvent extends ApplicationEvent {

        public Stage getStage() {
            return Stage.class.cast(getSource());
        }

        public StageReadyEvent(Object source) {
            super(source);
        }
    }
}
