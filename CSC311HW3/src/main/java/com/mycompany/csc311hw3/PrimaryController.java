package com.mycompany.csc311hw3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class PrimaryController {
    
    Map<String, Book> map;
   
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> Key;
    @FXML
    private TableColumn<Book, String> Value;
    
    @FXML
    private BorderPane borderPane; //root container of the GUI
    
    //Textfields for adding a map entry
    @FXML
    private TextField idAdd;
    @FXML
    private TextField titleAdd;
    @FXML
    private TextField priceAdd;
    
    //Textfield for removing an entry
    @FXML
    private TextField bookRemove;
    
    @FXML
    private Label lastAction;
    @FXML
    private Label mapType;
    
    
    
    
    /**
     * Sets up the columns of the tableView to read in the correct data from the map
     */
    @FXML
    public void initialize(){
        Key.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Value.setCellValueFactory(cellData -> Bindings.createStringBinding(
                () -> cellData.getValue().getId() + ", " 
                        + cellData.getValue().getTitle() + ", "
                        + cellData.getValue().getPrice()));
    }
    
    
    /**
     * Closes the GUI when the menu button "close" is pressed.
     */
    @FXML
    public void handleCloseApp(){
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }
    
    
    /**
     * Displays an open file dialog to user to let them select a JSON file to read from.
     * Creates a new instance of TreeMap and stores the reference in the map member variable.
     * User selects a JSON file and the data from it is populated into a treeMap.
     * 
     */
    @FXML
    public void openTreeMapFromJson(){
        
        FileChooser fileChooser = new FileChooser();
        File current= null;
        map = new TreeMap<>();
 
        try{
            
            try {
                current = new File(new File(".").getCanonicalPath());
            } catch (IOException ex) {
                lastAction.setText("Last Action: Error setting file path, TreeMap not opened");
            }
            
            fileChooser.setInitialDirectory(current);
            FileChooser.ExtensionFilter extFilter = 
                    new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
            
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile != null){
                FileReader fr = new FileReader(selectedFile);
                //Setup GSON
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Book[] books = gson.fromJson(fr, Book[].class);

                //populate TreeMap
                for (Book book : books) {
                    map.put(book.getTitle(), book);
                }
            }
            lastAction.setText("Last Action: Opened into TreeMap from JSON");
        }
        catch (FileNotFoundException e){
            lastAction.setText("Last Action: Error selecting file. TreeMap not opened");
        }
    }
    /**
     * Displays an open file dialog to user to let them select a JSON file to read from.
     * Creates a new instance of TreeMap and stores the reference in the map member variable.
     * User selects a JSON file and the data from it is populated into a hashMap.
     * 
     */
    @FXML
    public void openHashMapFromJson(){
        
        FileChooser fileChooser = new FileChooser();
        File current= null;
        map = new HashMap<>();
 
        try{
            
            try {
                current = new File(new File(".").getCanonicalPath());
            } catch (IOException ex) {
                lastAction.setText("Last Action: Error setting file path, HashMap not opened");
            }
            
            fileChooser.setInitialDirectory(current);
            FileChooser.ExtensionFilter extFilter = 
                    new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
            
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile != null){
                FileReader fr = new FileReader(selectedFile);
                //Setup GSON
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Book[] books = gson.fromJson(fr, Book[].class);

                //populate hashMap
                for (Book book : books) {
                    map.put(book.getTitle(), book);
                }
            }
            lastAction.setText("Last Action: Opened into HashMap from JSON");
        }
        catch (FileNotFoundException e){
            lastAction.setText("Last Action: Error selecting file. HashMap not opened");
        }
    }
    /**
     * Takes the data from the map instance and displays it in the tableView. The key column is the book title,
     * the value column is all the data of each book object.
     * Informs the user which type of map (tree/hash) is being displayed.
     * Clears the tableView of any previous data to prevent any redundancies. 
     * Expands the width of the tableView columns to create space for the entries.
     */
    @FXML
    public void loadTableFromMap(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        if(map != null){
            tableView.setItems(null); //clear table view to avoid repeats
            for (Book book: map.values()){
                books.add(book);
            }
            tableView.getColumns().forEach(column -> {
                column.setPrefWidth(310);
            });
            tableView.setItems(books);
            lastAction.setText("Last Action: Loaded TableView from map");
            if (map instanceof HashMap){

                mapType.setText("Map Type: HashMap");
            }
            if (map instanceof TreeMap){
                mapType.setText("Map Type: TreeMap");
            }
        }
        else{
            //do nothing
        }
    }
    /**
     * Displays a save file dialog for the user to save the map data to an existing or new JSON file.
     * The map data is written to the JSON file as an array of Book objects, not a map.
     */
    @FXML
    public void saveToJson(){
        FileChooser fileChooser = new FileChooser();
        File current = null;
        try{
            current = new File(new File(".").getCanonicalPath());
        } catch (IOException e1){
            
        }
        
        fileChooser.setInitialDirectory(current);
        FileChooser.ExtensionFilter extFilter = 
                    new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
            
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showSaveDialog(null);
      
        if(selectedFile != null){
            if(map.isEmpty()==false){
                Book[] books = map.values().toArray(new Book[0]);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                try{
                    FileWriter wr = new FileWriter(selectedFile);
                    for(Book book : books){
                        System.out.println(book);
                        gson.toJson(book, wr);
                    }
                    wr.close();
                    lastAction.setText("Last Action: Saved map to JSON file");
                } catch (IOException ex){}                 
            }
            else{
                // do nothing
            }
            
        }
    }
    /**
     * Add a new entry to the map using the 3 text fields displayed in the GUI. Does not update the tableView.
     */
    @FXML
    public void addToMap(){
        if(idAdd.getText().isEmpty() == false && idAdd.getText().isEmpty() == false && idAdd.getText().isEmpty() == false){
            int id = Integer.parseInt(idAdd.getText());
            String title = titleAdd.getText();
            double price = Double.parseDouble(priceAdd.getText());
            Book bookToAdd = new Book(id, title, price);
            map.put(title, bookToAdd);
            lastAction.setText("Last Action: Added entry to the map");
        }
        else{
            //do nothing
        }
    }
    /**
     * Removes an entry from the map. User must enter a key (book title) and the key + corresponding value will be removed.
     * Doesn't update the tableView.
     */
    @FXML
    public void removeBook(){
        if(bookRemove.getText().isEmpty() == false && map.containsKey(bookRemove.getText())){
            map.remove(bookRemove.getText());
            lastAction.setText("Last Action: Removed entry from the map");
        }
        else{
            //do nothing
        }
    }
    /**
     * Clears the map of all entries. Does not update the tableView.
     */
    @FXML
    public void clearMap(){
        if(map != null){
            map.clear();
            lastAction.setText("Last Action: Cleared all entries from map");
        }
        else{
            //do nothing
        }
    }
}
