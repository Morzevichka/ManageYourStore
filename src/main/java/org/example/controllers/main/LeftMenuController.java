package org.example.controllers.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.controllers.center.categories.CategoryController;
import org.example.controllers.center.products.ProductController;
import org.example.controllers.center.purchase.PurchaseController;
import org.example.controllers.center.report.ReportController;
import org.example.dto.UserSesssion;
import org.example.entity.Role;
import org.example.services.*;
import org.example.utils.LoadStagesUtil;
import org.example.utils.PrivilegesUtil;

import java.io.IOException;
import java.util.Objects;

public class LeftMenuController {
    @FXML
    private VBox adminVbox;
    @FXML
    private Label adminLabel;

    private MainController mainController;

    public void setMainSceneController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void categories(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = LoadStagesUtil.loadFXMLLoaderWithController("views/center/categories/categories",
                CategoryController.class,
                new CategoryServiceImpl());

        Objects.requireNonNull(fxmlLoader);

        mainController.setMainScreen(fxmlLoader.load());
    }

    @FXML
    private void products(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = LoadStagesUtil.loadFXMLLoaderWithController("views/center/products/products",
                ProductController.class,
                new ProductServiceImpl(),
                new CategoryServiceImpl());

        Objects.requireNonNull(fxmlLoader);

        mainController.setMainScreen(fxmlLoader.load());
    }

    @FXML
    private void purchase(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = LoadStagesUtil.loadFXMLLoaderWithController(
                "views/center/purchase/purchase",
                PurchaseController.class,
                new ProductServiceImpl(),
                new OrderServiceImpl(),
                new ClientServiceImpl()
        );

        Objects.requireNonNull(fxmlLoader);

        mainController.setMainScreen(fxmlLoader.load());
    }

    @FXML
    private void report(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = LoadStagesUtil.loadFXMLLoaderWithController(
                "views/center/report/reportView",
                ReportController.class,
                new OrderItemServiceImpl(),
                new ProductServiceImpl(),
                new OrderServiceImpl()
        );

        Objects.requireNonNull(fxmlLoader);

        mainController.setMainScreen(fxmlLoader.load());
    }

    public void logInSuccessful(Role role) {
        if (role.name().equals("ADMIN")) {
            adminVbox.setVisible(true);
            adminLabel.setVisible(true);
        }
    }
}
