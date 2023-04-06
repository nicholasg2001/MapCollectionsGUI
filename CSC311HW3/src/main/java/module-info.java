module com.mycompany.csc311hw3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.mycompany.csc311hw3 to javafx.fxml;
    exports com.mycompany.csc311hw3;
}
