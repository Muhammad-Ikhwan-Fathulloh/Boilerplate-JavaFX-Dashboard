import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private Stage primaryStage;
    private UserOperations userOperations;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // Elemen Login
        Label titleLabel = new Label("Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");

        // Elemen Register
        Label registerLink = new Label("Belum punya akun? Register di sini.");
        registerLink.setStyle("-fx-text-fill: blue; -fx-underline: true;");
        registerLink.setOnMouseClicked(event -> {
            // Arahkan ke RegisterView
            RegisterView registerView = new RegisterView(primaryStage);
            primaryStage.setScene(new Scene(registerView.getView(), 800, 600));
        });

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                userOperations = new UserOperations();
            } catch (SQLException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (userOperations.loginUser(username, password)) {
                // Login sukses, arahkan ke Dashboard
                DashboardView dashboardView = new DashboardView(primaryStage);
                primaryStage.setScene(new Scene(dashboardView.getView(), 800, 600));
            } else {
                showError("Login gagal! Periksa username dan password Anda.");
            }
        });
        
        root.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, registerLink);
        return root;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
