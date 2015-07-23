package com.kritsit.casetracker.client.domain.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import com.kritsit.casetracker.client.domain.services.IAdministratorService;
import com.kritsit.casetracker.client.domain.services.ServerConnection;
import com.kritsit.casetracker.shared.domain.Request;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            initialize();
            initStaffTable();
            initPermissionCombobox();
        } else {
            logger.debug("Administrator view disabled");
        } 
    }
    
    
    public void initialize(){
        
        addUserButton.setOnAction(event->{
            Permission permission = Permission.valueOf(permissionCombobox.getValue());
            
            Staff staff = new Staff(usernameField.getText(), 
                    firstNameField.getText(), 
                    lastNameField.getText(), 
                    departmentCombobox.getValue(), 
                    positionField.getText(), 
                    permission);
            
            List<Staff> list = new ArrayList<Staff>();
            list.add(staff);
            Request request = new Request("addUser", list);
           
            // sernding serialized request through socket
            
            resetAddUserTab();
        });
        
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initStaffTable(){
        TableColumn firstNameColumn = new TableColumn("First name");
        TableColumn lastNameColumn = new TableColumn("Last name");
        TableColumn usernameColumn = new TableColumn("Username");
        TableColumn departmentColumn = new TableColumn("Department");
        TableColumn positionColumn = new TableColumn("Position");
        
        staffTable.getColumns().addAll(firstNameColumn, lastNameColumn,
                usernameColumn, departmentColumn, positionColumn);
        
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("First name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Last name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Username"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Department"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Position"));
        
        staffTable.setItems(staffList);
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
    @FXML private TableView staffTable;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> departmentCombobox;
    @FXML private TextField positionField;
    @FXML private TextField usernameField;
    @FXML private ComboBox<String> permissionCombobox;
    @FXML private Button addUserButton;
    
    
}
