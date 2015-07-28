package com.kritsit.casetracker.client.domain.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kritsit.casetracker.client.domain.services.IAdministratorService;
import com.kritsit.casetracker.client.domain.services.InputToModelParseResult;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class AdministratorController implements IController {
    
    private ObservableList<Staff> staffList;
    private IAdministratorService administratorService;
    private Stage stage;
    private final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
    
    public void setAdministratorService(IAdministratorService administratorService) {
        this.administratorService = administratorService;
        
    }
    
    public void initFrame(){
        logger.info("Initiating frame");
        if (administratorService.getUser().getPermission() == Permission.ADMIN) {
            initStaffTable();
            initPermissionCombobox();
            initSearchCombobox();
        } else {
            logger.debug("Administrator view disabled");
        } 
    }
    
    public void initialize(){
        
        addUserButton.setOnAction(event->{
           
            Map<String, Object> inputMap = new HashMap<String, Object>();
            inputMap.put("username", usernameField.getText());
            inputMap.put("firstname", firstNameField.getText());
            inputMap.put("lastname", lastNameField.getText());
            inputMap.put("department", departmentCombobox.getValue());
            inputMap.put("position", positionField.getText());
            inputMap.put("permission", Permission.valueOf(permissionCombobox.getValue()));
            
            InputToModelParseResult result = administratorService.addUser(inputMap);
            if(result.isSuccessful()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Adding a new user");
            alert.setHeaderText("New user added succesfully");
            alert.setContentText("Click OK to proceed");
            alert.showAndWait();
            }
            else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding new user");
            alert.setContentText(result.getReason());
            alert.showAndWait();
            }
           
            resetAddUserTab();
        });
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @SuppressWarnings("unchecked")
    private void initStaffTable(){
       
        logger.info("Initiating staff list table");
        staffList = FXCollections.observableArrayList(administratorService.getInspectors());
    
        FilteredList<Staff> filteredStaff = new FilteredList<>(staffList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStaff.setPredicate(s -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (s.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                
                return false;
            });
        });
        
        searchCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredStaff.setPredicate(s -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
               
                if (s.getPermission().toString().equals(newValue)) {
                    return true;
                }
                
                return false;
            });
        });
        
        SortedList<Staff> sortedStaff = new SortedList<>(filteredStaff);
        sortedStaff.comparatorProperty().bind(staffTable.comparatorProperty());
        staffTable.setItems(sortedStaff);
        
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory("username"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory("department"));
        permissionColumn.setCellValueFactory(new PropertyValueFactory("permission"));
    }
    
    private void resetAddUserTab(){
        firstNameField.setText("");
        lastNameField.setText("");
        departmentCombobox.setValue("");
        positionField.setText("");
        usernameField.setText("");
        permissionCombobox.setValue("");
    }
    
    private void initPermissionCombobox(){
       ObservableList<String> permissions = 
               FXCollections.observableArrayList(Permission.ADMIN.toString(),
               Permission.EDITOR.toString(), Permission.VIEWER.toString());
       permissionCombobox.setItems(permissions);
    }
    
    private void initSearchCombobox(){
        ObservableList<String> permissions = 
                FXCollections.observableArrayList(Permission.ADMIN.toString(),
                Permission.EDITOR.toString(), Permission.VIEWER.toString());
        searchCombobox.setItems(permissions);
     }

    @FXML private TextField searchField;
    @FXML private ComboBox<String> searchCombobox;
    @FXML private Button resetPasswordButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private TableView<Staff> staffTable;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> departmentCombobox;
    @FXML private TextField positionField;
    @FXML private TextField usernameField;
    @FXML private ComboBox<String> permissionCombobox;
    @FXML private Button addUserButton;
    @FXML private TableColumn<Staff, String> firstNameColumn;
    @FXML private TableColumn<Staff, String> lastNameColumn;
    @FXML private TableColumn<Staff, String> usernameColumn;
    @FXML private TableColumn<Staff, String> departmentColumn;
    @FXML private TableColumn<Staff, String> permissionColumn;

}
