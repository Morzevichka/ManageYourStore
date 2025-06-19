package org.example.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.controllers.main.MainController;
import org.example.entity.Role;
import org.example.entity.Worker;
import org.example.services.WorkerServiceImpl;
import org.example.services.impl.WorkerService;
import org.example.utils.HibernateUtil;

import javax.naming.AuthenticationException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label actionLabel;

    private final MainController mainController;

    private final WorkerServiceImpl workerService;

    public LoginController(WorkerServiceImpl workerService, MainController mainController) {
        this.workerService = workerService;
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.sceneProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                newVal.setOnKeyPressed(key -> {
                    switch (key.getCode()) {
                        case ENTER -> logIn(null);
                        case ESCAPE -> close(null);
                    }
                });
            }
        });

        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                actionLabel.setText("Введите логин или пароль для продолжения");
                logInButton.setDisable(true);
            } else {
                actionLabel.setText(null);
                logInButton.setDisable(false);
            }
        });

        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                actionLabel.setText("Введите логин или пароль для продолжения");
                logInButton.setDisable(true);
            } else {
                actionLabel.setText(null);
                logInButton.setDisable(false);
            }
        });
    }

    @FXML
    private void logIn(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Worker worker = workerService.loginWorker(username, password);
            handleSuccessful(worker);
        } catch (AuthenticationException e) {
            actionLabel.setText(e.getMessage());
        } catch (Exception e) {
            actionLabel.setText("Произошла ошибка. Повторите еще раз!");
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stageLogin = (Stage) (anchorPane.getScene().getWindow());
        stageLogin.close();
    }

    private void handleSuccessful(Worker worker) {
        mainController.topMenuController.toggleLogInSuccessful(true);
        mainController.leftMenuController.logInSuccessful(worker.getRole());
        close(null);
    }
}
