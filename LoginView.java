import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private Stage primaryStage;

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

            UserOperations userOps = new UserOperations();
            if (userOps.login(username, password)) {
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