import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TodoView {
    private TodoOperations todoOperations;
    private TableView<Todo> tableView;
    private ObservableList<Todo> todoList;

    public TodoView() {
        try {
            todoOperations = new TodoOperations();
            todoList = FXCollections.observableArrayList(todoOperations.getTodos());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading todos: " + e.getMessage());
        }
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // TableView
        tableView = new TableView<>();
        tableView.setItems(todoList);

        // Kolom
        TableColumn<Todo, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());

        TableColumn<Todo, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());

        TableColumn<Todo, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

        TableColumn<Todo, Boolean> isCompletedColumn = new TableColumn<>("Completed");
        isCompletedColumn.setCellValueFactory(data -> data.getValue().isCompletedProperty());

        TableColumn<Todo, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private Button editButton = new Button("Edit");
            private Button deleteButton = new Button("Delete");
            private Button markCompletedButton = new Button("Mark as Completed");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    editButton.setOnAction(event -> {
                        Todo selected = getTableView().getItems().get(getIndex());
                        showTodoModal(selected);
                    });

                    deleteButton.setOnAction(event -> {
                        Todo selected = getTableView().getItems().get(getIndex());
                        todoOperations.deleteTodo(selected.getId());
                        refreshTable();
                        showSuccess("Todo deleted successfully!");
                    });

                    markCompletedButton.setOnAction(event -> {
                        Todo selected = getTableView().getItems().get(getIndex());
                        todoOperations.markAsCompleted(selected.getId());
                        refreshTable();
                        showSuccess("Todo marked as completed!");
                    });

                    HBox buttonContainer = new HBox(5);
                    buttonContainer.getChildren().addAll(editButton, deleteButton, markCompletedButton);
                    setGraphic(buttonContainer);
                }
            }
        });

        tableView.getColumns().addAll(idColumn, titleColumn, descriptionColumn, isCompletedColumn, actionColumn);

        // Add Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            showTodoModal(null);
        });

        root.getChildren().addAll(addButton, tableView);
        return root;
    }

    private void showTodoModal(Todo todo) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(todo == null ? "Add Todo" : "Edit Todo");

        TextField titleField = new TextField(todo == null ? "" : todo.getTitle());
        TextField descriptionField = new TextField(todo == null ? "" : todo.getDescription());

        Button saveButton = new Button(todo == null ? "Add" : "Save");
        saveButton.setOnAction(e -> {
            if (todo == null) {
                todoOperations.addTodo(new Todo(0, titleField.getText(), descriptionField.getText(), false, null));
                showSuccess("Todo added successfully!");
            } else {
                todoOperations.updateTodo(todo.getId(), titleField.getText(), descriptionField.getText());
                showSuccess("Todo updated successfully!");
            }
            refreshTable();
            modalStage.close();
        });

        VBox modalContent = new VBox(10);
        modalContent.setPadding(new Insets(10));
        modalContent.getChildren().addAll(new Label("Title:"), titleField, new Label("Description:"), descriptionField, saveButton);

        Scene modalScene = new Scene(modalContent);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    private void refreshTable() {
        todoList.setAll(todoOperations.getTodos());
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}