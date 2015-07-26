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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
        TableColumn<Staff, String>  firstNameColumn = new TableColumn<Staff, String>("First name");
        TableColumn<Staff, String> lastNameColumn = new TableColumn<Staff, String>("Last name");
        TableColumn<Staff, String> usernameColumn = new TableColumn<Staff, String>("Username");
        TableColumn<Staff, String> departmentColumn = new TableColumn<Staff, String>("Department");
        TableColumn<Staff, String> positionColumn = new TableColumn<Staff, String>("Position");
        
        staffTable.getColumns().addAll(firstNameColumn, lastNameColumn,
                usernameColumn, departmentColumn, positionColumn);
        
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("First name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Last name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Username"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Department"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Position"));
        
        List<Staff> users = administratorService.getInspectors();
        if(users!=null){
            for(Staff s : users){
                staffList.add(s);
            }
            
            staffTable.setItems(staffList);
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error downloading users list");
            alert.setContentText("Check logs for details");
            alert.showAndWait();
        }
        
    }
    
    private void resetAddUserTab(){
        firstNameField.setText("");
        lastNameField.setText("");
        departmentCombobox.setValue("");
        positionField.setText("");
        usernameField.setText("");
        permissionCombobox.setValue("");
    }
    
    private void resetSearch(){
        searchField.setText("");
        searchCombobox.setValue("");
    }
    
    private void initPermissionCombobox(){
       ObservableList<String> permissions = 
               FXCollections.observableArrayList(Permission.ADMIN.toString(),
               Permission.EDITOR.toString(), Permission.VIEWER.toString());
       permissionCombobox = new ComboBox<String>(permissions);
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
    
    
}
