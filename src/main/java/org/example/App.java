package org.example;

/*
userLabelOnAction
userLabel
setPasswordMenuItemOnAction

functions
setMainSceneController (what func do and name)
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.repository.ProductRepository;
import org.example.utils.HibernateUtil;
import org.example.utils.LoadStagesUtil;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class App extends Application {

    private final boolean SET_MAXIMIZED_WINDOW = true;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenRes.getWidth();
        int height = (int) screenRes.getHeight();

        HibernateUtil.getSessionFactory();

        Scene scene = new Scene(LoadStagesUtil.loadFXML("views/main/main").load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Clothing Store App");
        primaryStage.setMaximized(SET_MAXIMIZED_WINDOW);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}