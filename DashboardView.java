import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {
    private Stage primaryStage;

    public DashboardView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();

        // Menu Navigasi
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));

        Button todoButton = new Button("Manage Todos");
        todoButton.setOnAction(e -> {
            // Tampilkan TodoView
            TodoView todoView = new TodoView();
            primaryStage.setScene(new Scene(todoView.getView(), 800, 600));
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            // Arahkan ke LoginView
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
        });

        menu.getChildren().addAll(new Label("Dashboard"), todoButton, logoutButton);
        root.setLeft(menu);

        // Tampilan Utama
        Label welcomeLabel = new Label("Selamat datang di Dashboard!");
        root.setCenter(welcomeLabel);

        return root;
    }
}