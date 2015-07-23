package com.kritsit.casetracker.client.domain.ui.controller;

import java.util.List;
import java.util.ArrayList;

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
import javafx.fxml.FXML;

public class AdministratorController implements IController {
    
    
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
            // need a socket to send serialized request through
            resetAddUserTab();
        });
        
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
    private ObservableList<Staff> staffList;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> departmentCombobox;
    @FXML private TextField positionField;
    @FXML private TextField usernameField;
    @FXML private ComboBox<String> permissionCombobox;
    @FXML private Button addUserButton;
    
}
