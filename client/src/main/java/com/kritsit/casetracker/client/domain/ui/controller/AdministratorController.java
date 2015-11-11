package com.kritsit.casetracker.client.domain.ui.controller;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kritsit.casetracker.client.domain.services.Export;
import com.kritsit.casetracker.client.domain.services.IAdministratorService;
import com.kritsit.casetracker.client.domain.services.IMenuService;
import com.kritsit.casetracker.client.domain.services.IExportService;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdministratorController implements IController {

    private ObservableList<Staff> staffList;
    private FilteredList<Staff> filteredStaff;
    private IAdministratorService administratorService;
    private IMenuService menuService;
    private IExportService exportService;
    private Stage stage;
    private final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

    public void setAdministratorService(IAdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    public void setMenuService(IMenuService menuService){
        this.menuService = menuService;
    }

    public void setExportService(IExportService exportService){
	this.exportService = exportService;
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

        changePasswordItem.setOnAction(event->{
            menuService.changePasswordFrame();
        });

        newUserItem.setOnAction(event->{
            SingleSelectionModel<Tab> selection = tabPane.getSelectionModel();
            selection.select(addUserTab);
        });

        editUserItem.setOnAction(event->{
            editUser();
        });

        deleteUserItem.setOnAction(event->{
            deleteUser();
        });

        resetPasswordItem.setOnAction(event->{
            resetPassword();
        });

        logoutItem.setOnAction(event->{
            stage.close();
            menuService.restart();
        });
        exitItem.setOnAction(event->{
            menuService.closeConnection();
            stage.close();
        });
        
        helpItem.setOnAction(event->{
            showHelpFrame();
        });
        
        aboutItem.setDisable(true);

        btnResetPassword.setOnAction(event->{
            resetPassword();
        });

        btnDelete.setOnAction(event->{
          deleteUser();
        });

        btnAddUser.setOnAction(event->{
           addUser();
        });

        btnEdit.setOnAction(event->{
           editUser();
        });
        
        exportItem.setOnAction(event->{
            export();
        });

        aboutItem.setOnAction(event->{
            menuService.aboutFrame();
        });
    }

    private void export() {
        exportService = new Export();
        
        List<String> headers = new ArrayList<String>();
        headers.add("First name");
        headers.add("Last name");
        headers.add("Username");
        headers.add("Department");
        headers.add("Position");
        headers.add("Permissions");
        
        List<String[]> cells = new ArrayList<String[]>();
        for(Staff s : filteredStaff){
            String[] row = new String[6];
            row[0] = s.getFirstName();
            row[1] = s.getLastName();
            row[2] = s.getUsername();
            row[3] = s.getDepartment();
            row[4] = s.getPosition();
            row[5] = s.getPermission().toString();
            cells.add(row);
        }
                   
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save report");
        File file = fileChooser.showSaveDialog(stage);
        if(file==null){
        	logger.info("cancelling PDF export");
        	return;
        }
        if(!(file.getName().endsWith(".pdf"))){
            File fileWithExtension = new File(file.getAbsolutePath()+".pdf"); 
            exportService.exportToPDF(headers, cells, fileWithExtension);
        }
        else{
        exportService.exportToPDF(headers, cells, file);
        }
    }

    private void addUser() {
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("username", txfAddUsername.getText());
        inputMap.put("firstname", txfAddFirstName.getText());
        inputMap.put("lastname", txfAddLastName.getText());
        inputMap.put("department", cbxAddDepartment.getValue());
        inputMap.put("position", txfAddPosition.getText());
        inputMap.put("permission", cbxAddPermission.getValue());

        InputToModelParseResult result = administratorService.addUser(inputMap);
        if(result.isSuccessful()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Adding a new user");
            alert.setHeaderText("New user added succesfully");
            alert.setContentText("Click OK to proceed");
            alert.showAndWait();
            resetPassword(txfAddUsername.getText());
            resetAddUserTab();
            initStaffTable();
        } else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding new user");
            alert.setContentText(result.getReason());
            alert.showAndWait();
        }

    }
    
    private void showHelpFrame(){
        
        AnchorPane helpFrame = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/ui/fxml/AdministratorHelpFrame.fxml"));

        fxmlLoader.setRoot(helpFrame);

        try{
            helpFrame = (AnchorPane) fxmlLoader.load();
        } catch(IOException e){
            logger.error("Error loading help frame.", e);
            return;
        }

        Scene scene = new Scene(helpFrame);
        Stage stage = new Stage();
        stage.setTitle("Help");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void editUser(){
        TableViewSelectionModel<Staff> selection =  tblStaff.getSelectionModel();
        if(selection.getSelectedItem()==null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Editing user");
            alert.setHeaderText("Information");
            alert.setContentText("Select a user to edit");
            alert.showAndWait();
            return;
        }

        Staff s = selection.getSelectedItem();
        EditUserController c = new EditUserController(s, administratorService, this);
        AnchorPane EditUserPane = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/ui/fxml/EditUser.fxml"));

        fxmlLoader.setController(c);
        fxmlLoader.setRoot(EditUserPane);

        try{
            EditUserPane= (AnchorPane) fxmlLoader.load();
        } catch(IOException e){
            logger.error("Error loading frame to edit user.", e);
            return;
        }

        Scene scene = new Scene(EditUserPane);
        Stage stage = new Stage();
        stage.setTitle("Editing user "+s.getUsername());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void deleteUser() {
        Alert alert;
        TableViewSelectionModel<Staff> selection =  tblStaff.getSelectionModel();
        if(selection.getSelectedItem()==null){
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Deleting user");
            alert.setHeaderText("Information");
            alert.setContentText("Select a user to delete");
            alert.showAndWait();
            return;
        }
        String selectedUsername = selection.getSelectedItem().getUsername();
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("username", selectedUsername);
        int result = administratorService.deleteUser(inputMap);
        switch(result){
        case 200 :
          staffList = FXCollections.observableArrayList(administratorService.getStaff());
          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Deleting user");
          alert.setHeaderText("User deleted succesfully");
          alert.setContentText("Click OK to proceed");
          alert.showAndWait();
          break;

        case 500 :
          alert = new Alert(AlertType.ERROR);
          alert.setTitle("Deleting user");
          alert.setHeaderText("Error while deleting user");
          alert.setContentText("Error occured on the server side");
          alert.showAndWait();
          break;
        }
        initStaffTable();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @SuppressWarnings("unchecked")
    protected void initStaffTable(){

        logger.info("Initiating staff list table");
        staffList = FXCollections.observableArrayList(administratorService.getStaff());
        filteredStaff = new FilteredList<>(staffList, p -> true);
        txfFilterUsers.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStaff.setPredicate(s -> {
                if (newValue == null || newValue.isEmpty() || newValue.equals("All")) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (s.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        cbxFilterPermissions.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredStaff.setPredicate(s -> {
                if (newValue == null || newValue.isEmpty() || newValue.equals("All")) {
                    return true;
                }
                if (s.getPermission().toString().equals(newValue)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Staff> sortedStaff = new SortedList<>(filteredStaff);
        sortedStaff.comparatorProperty().bind(tblStaff.comparatorProperty());
        tblStaff.setItems(sortedStaff);

        colFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colDepartment.setCellValueFactory(new PropertyValueFactory("department"));
        colPermission.setCellValueFactory(new PropertyValueFactory("permission"));
    }

    private void resetAddUserTab(){
        txfAddFirstName.setText("");
        txfAddLastName.setText("");
        cbxAddDepartment.setValue("");
        txfAddPosition.setText("");
        txfAddUsername.setText("");
        cbxAddPermission.setValue("");
    }

    private void initPermissionCombobox(){
       ObservableList<String> permissions =
               FXCollections.observableArrayList(Permission.ADMIN.toString(),
               Permission.EDITOR.toString(), Permission.VIEWER.toString());
       cbxAddPermission.setItems(permissions);
    }

    private void initSearchCombobox(){
        ObservableList<String> permissions =
                FXCollections.observableArrayList("All", Permission.ADMIN.toString(),
                Permission.EDITOR.toString(), Permission.VIEWER.toString());
        cbxFilterPermissions.setItems(permissions);
        cbxFilterPermissions.setValue("All");
     }

    private void resetPassword(){
        SelectionModel<Staff> selection = tblStaff.getSelectionModel();
        Alert alert;
        if (selection.getSelectedItem() == null) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Reseting password");
            alert.setHeaderText("Information");
            alert.setContentText("Select a user");
            alert.showAndWait();
            return;
        }
        String username = selection.getSelectedItem().getUsername();
        String randomPass = administratorService.randomPassword();
        int hashedRandomPass = randomPass.hashCode();
        int result = administratorService.resetPassword(
                username, hashedRandomPass);
        switch (result) {
            case 200 :
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Password reset");
                alert.setHeaderText("Password reset succesfully");
                alert.setContentText("new password: "+randomPass);
                alert.showAndWait();
                break;
            case 500 :
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Password reset");
                alert.setHeaderText("Error during password reset");
                alert.setContentText("Error occured on the server side");
                alert.showAndWait();
                break;
          }
    }

    private void resetPassword(String username){
        Alert alert;
        String randomPass = administratorService.randomPassword();
        int hashedRandomPass = randomPass.hashCode();
        int result = administratorService.resetPassword(
                username, hashedRandomPass);
        switch (result) {
            case 200 :
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Password");
                alert.setHeaderText("Password for user "+username+" set succesfully");
                alert.setContentText("new password: "+randomPass);
                alert.showAndWait();
                break;
            case 500 :
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Password reset");
                alert.setHeaderText("Error during password reset");
                alert.setContentText("Error occured on the server side");
                alert.showAndWait();
                break;
          }
    }

    @FXML private TextField txfFilterUsers;
    @FXML private ComboBox<String> cbxFilterPermissions;
    @FXML private Button btnResetPassword;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private TableView<Staff> tblStaff;
    @FXML private TextField txfAddFirstName;
    @FXML private TextField txfAddLastName;
    @FXML private ComboBox<String> cbxAddDepartment;
    @FXML private TextField txfAddPosition;
    @FXML private TextField txfAddUsername;
    @FXML private ComboBox<String> cbxAddPermission;
    @FXML private Button btnAddUser;
    @FXML private TableColumn<Staff, String> colFirstName;
    @FXML private TableColumn<Staff, String> colLastName;
    @FXML private TableColumn<Staff, String> colUsername;
    @FXML private TableColumn<Staff, String> colDepartment;
    @FXML private TableColumn<Staff, String> colPermission;
    @FXML private MenuItem changePasswordItem;
    @FXML private MenuItem exportItem;
    @FXML private MenuItem logoutItem;
    @FXML private MenuItem exitItem;
    @FXML private MenuItem newUserItem;
    @FXML private MenuItem editUserItem;
    @FXML private MenuItem deleteUserItem;
    @FXML private MenuItem resetPasswordItem;
    @FXML private MenuItem aboutItem;
    @FXML private MenuItem helpItem;
    @FXML private TabPane tabPane;
    @FXML private Tab addUserTab;
}
