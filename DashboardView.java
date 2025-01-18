import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {
    private Stage primaryStage;
    private UserOperations userOperations;
    private String username;

    public DashboardView(Stage primaryStage, String username) {
        this.primaryStage = primaryStage;
        this.username = username;
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // Add padding to the root

        // Menu Navigasi
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(10));
        menu.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px;"); // Add background color to menu

        Button todoButton = new Button("Todos");
        todoButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        todoButton.setOnAction(e -> {
            // Tampilkan TodoView
            System.out.println("Fitur Todo");
            TodoView todoView = new TodoView(primaryStage, username); 
            primaryStage.setScene(new Scene(todoView.getView(), 800, 600));
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        logoutButton.setOnAction(e -> {
            // Arahkan ke LoginView
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
        });

        menu.getChildren().addAll(new Label("Todo Apps"), todoButton, logoutButton);
        root.setLeft(menu);

        // Tampilan Utama
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        Label welcomeLabel = new Label("Selamat datang "+username+" di Dashboard!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Add more content here (e.g., recent todos, statistics, etc.)
        try {
            userOperations = new UserOperations();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        User user = userOperations.getProfile(username);

        content.getChildren().add(welcomeLabel);
        if (user != null) {
            // Display user profile information (e.g., in a Label)
            Label profileLabel = new Label("Username: " + user.getUsername());
            Label roleLabel = new Label("Role: " + user.getRole());
            content.getChildren().addAll(profileLabel, roleLabel);
        } 
        root.setCenter(content);

        return root;
    }
}
