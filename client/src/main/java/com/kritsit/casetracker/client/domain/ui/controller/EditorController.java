package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.IEditorService;
import com.kritsit.casetracker.client.domain.model.Appointment;
import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class EditorController implements IController {
    private final Logger logger = LoggerFactory.getLogger(EditorController.class);
    private IEditorService editorService;
    private Stage stage;
    private ObservableList<Case> cases;
    private int calendarCurrentYear;
    private int calendarCurrentMonth;

    public void setEditorService(IEditorService editorService) {
        this.editorService = editorService;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void populateTables() {
        initCasesTable();
        initCalendarTable();
    }

    @SuppressWarnings("unchecked")
    private void initCasesTable() {
        cases = FXCollections.observableArrayList(editorService.getCases());
        tblCases.setItems(cases);
        colCaseNumber.setCellValueFactory(new PropertyValueFactory("caseNumber"));
        colCaseName.setCellValueFactory(new PropertyValueFactory("caseName"));
        colCaseType.setCellValueFactory(new PropertyValueFactory("caseType"));
        colInvestigatingOfficer.setCellValueFactory(new Callback<CellDataFeatures<Case, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Case, String> c) {
                return c.getValue().getInvestigatingOfficer().nameProperty();
            }
        });
        colIncidentDate.setCellValueFactory(new Callback<CellDataFeatures<Case, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Case, String> c) {
                return c.getValue().getIncident().dateProperty();
            }
        });
        
        double numberWidthPercent = 0.15;
        double nameWidthPercent = 0.25;
        double officerWidthPercent = 0.25;
        double dateWidthPercent = 0.15;
        double typeWidthPercent = 0.2;

        colCaseNumber.prefWidthProperty().bind(tblCases.widthProperty().multiply(numberWidthPercent));
        colCaseName.prefWidthProperty().bind(tblCases.widthProperty().multiply(nameWidthPercent));
        colInvestigatingOfficer.prefWidthProperty().bind(tblCases.widthProperty().multiply(officerWidthPercent));
        colIncidentDate.prefWidthProperty().bind(tblCases.widthProperty().multiply(dateWidthPercent));
        colCaseType.prefWidthProperty().bind(tblCases.widthProperty().multiply(typeWidthPercent));
    }

    private void initCalendarTable() {
        tblCalendar.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblCalendar.getSelectionModel().setCellSelectionEnabled(true);
        List<TableColumn<List<Day>, String>> columns = new ArrayList<>();
        columns.add(colMonday);
        columns.add(colTuesday);
        columns.add(colWednesday);
        columns.add(colThursday);
        columns.add(colFriday);
        columns.add(colSaturday);
        columns.add(colSunday);
        for (int i = 0; i < columns.size(); i++) {
            TableColumn<List<Day>, String> column = columns.get(i);
            setCalendarColumnWidth(column);
            setCellValueFactory(column, i);
        }

        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        for (int i = year - 10; i <= year + 10; i++) {
            cmbCalendarYear.getItems().add(Integer.valueOf(i));
        }

        calendarCurrentYear = year;
        calendarCurrentMonth = month;

        refreshCalendarTable(month, year);
    }

    private void setCalendarColumnWidth(TableColumn column) {
        int numColumns = 7;
        column.prefWidthProperty().bind(tblCalendar.widthProperty().divide(numColumns));
    }

    private void setCellValueFactory(TableColumn<List<Day>, String> column, final int dayIndex) {
        column.setCellValueFactory(new Callback<CellDataFeatures<List<Day>, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<List<Day>, String> week) {
                Day day = week.getValue().get(dayIndex);
                return new SimpleStringProperty(day.toString());
            }
        });
    }

    private void refreshCalendarTable(int currentMonth, int currentYear) {
        String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        LocalDate today = LocalDate.now();
        int realMonth = today.getMonthValue();
        int realYear = today.getYear();

        btnCalendarNext.setDisable(false);
        btnCalendarPrevious.setDisable(false);
        if (currentMonth == 12 && currentYear >= realYear + 10) {
            btnCalendarNext.setDisable(true);
        }
        if (currentMonth == 1 && currentYear <= realYear - 10) {
            btnCalendarPrevious.setDisable(true);
        }

        txtCalendarMonth.setText(month[currentMonth - 1]);
        cmbCalendarYear.setValue(Integer.valueOf(currentYear));

        List<List<Day>> monthAppointments = editorService.getMonthAppointments(currentMonth, currentYear);

        tblCalendar.setItems(FXCollections.observableList(monthAppointments));
    }

    @FXML protected void handleFilterClearAction(ActionEvent e) {
    }

    @FXML protected void handleSummaryEditAction(ActionEvent e) {
    }

    @FXML protected void handleCalendarPreviousAction(ActionEvent e) {
        if (calendarCurrentMonth == 1) {
            calendarCurrentMonth = 12;
            calendarCurrentYear--;
        } else {
            calendarCurrentMonth--;
        }
        refreshCalendarTable(calendarCurrentMonth, calendarCurrentYear);
    }

    @FXML protected void handleCalendarNextAction(ActionEvent e) {
        if (calendarCurrentMonth == 12) {
            calendarCurrentMonth = 1;
            calendarCurrentYear++;
        } else {
            calendarCurrentMonth++;
        }
        refreshCalendarTable(calendarCurrentMonth, calendarCurrentYear);
    }

    @FXML protected void handleCalendarTodayAction(ActionEvent e) {
    }

    @FXML protected void handleAddNewDefendantAction(ActionEvent e) {
    }

    @FXML protected void handleAddNewComplainantAction(ActionEvent e) {
    }

    @FXML protected void handleAddEvidenceAction(ActionEvent e) {
    }

    @FXML protected void handleEditEvidenceAction(ActionEvent e) {
    }

    @FXML protected void handleDeleteEvidenceAction(ActionEvent e) {
    }

    @FXML protected void handleAddCaseAction(ActionEvent e) {
    }

    @FXML private Button btnCalendarNext;
    @FXML private Button btnCalendarPrevious;
    @FXML private ChoiceBox<Integer> cmbCalendarYear;
    @FXML private TableView<Case> tblCases;
    @FXML private TableView<List<Day>> tblCalendar;
    @FXML private TableColumn<Case, String> colCaseNumber;
    @FXML private TableColumn<Case, String> colCaseName;
    @FXML private TableColumn<Case, String> colInvestigatingOfficer;
    @FXML private TableColumn<Case, String> colIncidentDate;
    @FXML private TableColumn<Case, String> colCaseType;
    @FXML private TableColumn<List<Day>, String> colMonday;
    @FXML private TableColumn<List<Day>, String> colTuesday;
    @FXML private TableColumn<List<Day>, String> colWednesday;
    @FXML private TableColumn<List<Day>, String> colThursday;
    @FXML private TableColumn<List<Day>, String> colFriday;
    @FXML private TableColumn<List<Day>, String> colSaturday;
    @FXML private TableColumn<List<Day>, String> colSunday;
    @FXML private Text txtCalendarMonth;
}
