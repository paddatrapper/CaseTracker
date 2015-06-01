package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.IEditorService;
import com.kritsit.casetracker.client.domain.model.Appointment;
import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.time.LocalDate;
import java.io.File;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void initFrame() {
        initCasesTable();
        initCalendarTable();
    }

    @SuppressWarnings("unchecked")
    private void initCasesTable() {
        cases = FXCollections.observableArrayList(editorService.getCases());
        cbxFilterCaseType.getItems().add("All");
        cbxFilterCaseType.setValue("All");
        for (Case c : cases) {
            if (!cbxFilterCaseType.getItems().contains(c.getType())) {
                cbxFilterCaseType.getItems().add(c.getType());
            }
        }
        FilteredList<Case> filteredCases = new FilteredList<>(cases, p -> true);
        txfFilterCases.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCases.setPredicate(c -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (c.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (c.getNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        cbxFilterCaseType.valueProperty().addListener((obs, oldValue, newValue) -> {
            filteredCases.setPredicate(c -> {
                if (newValue == null || newValue.isEmpty() || newValue.equals("All")) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (c.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Case> sortedCases = new SortedList<>(filteredCases);
        sortedCases.comparatorProperty().bind(tblCases.comparatorProperty());
        tblCases.setItems(sortedCases);

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
        tblCases.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateShownCase(newSelection);
        });
        updateShownCase(null);
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
            cbxCalendarYear.getItems().add(Integer.valueOf(i));
        }

        calendarCurrentYear = year;
        calendarCurrentMonth = month;

        cbxCalendarYear.valueProperty().addListener((obs, oldValue, newValue) -> {
            calendarCurrentYear = newValue;
            refreshCalendarTable(calendarCurrentMonth, calendarCurrentYear);
        });

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
        cbxCalendarYear.setValue(Integer.valueOf(currentYear));

        List<List<Day>> monthAppointments = editorService.getMonthAppointments(currentMonth, currentYear);

        tblCalendar.setItems(FXCollections.observableList(monthAppointments));
    }

    private void updateShownCase(Case c) {
        if (c == null) {
            panCaseSummary.setVisible(false);
        } else { 
            panCaseSummary.setVisible(true);
            txtSummaryCaseName.setText(c.getName());
            txtSummaryCaseNumber.setText(c.getNumber());
            txtSummaryCaseType.setText(c.getType());
            txtSummaryInvestigatingOfficer.setText(c.getInvestigatingOfficer().getName());
            txtSummaryIncidentDate.setText(c.getIncident().getDate().toString());
            txtSummaryDefendant.setText(c.getDefendant().getName());
            if (c.getNextCourtDate() != null) {
                txtSummaryCourtDate.setText(c.getNextCourtDate().toString());
            } else {
                txtSummaryCourtDate.setText("N/A");
            }
            if (c.isReturnVisit()) {
                txtSummaryReturnDate.setText(c.getReturnDate().toString());
            } else {
                txtSummaryReturnDate.setText("N/A");
            }
            if (c.getIncident().getAddress() != null) {
                txtSummaryLocation.setText("Address: ");
                txtSummaryLocationValue.setText(c.getIncident().getAddress());
                txtSummaryLatitude.setVisible(false);
                txtSummaryLatitudeValue.setVisible(false);
            } else {
                txtSummaryLocation.setText("Longitude: ");
                txtSummaryLocationValue.setText(c.getIncident().getLongitude() + "");
                txtSummaryLatitude.setVisible(true);
                txtSummaryLatitudeValue.setText(c.getIncident().getLatitude() + "");
            }
            txaSummaryDetails.setText(c.getDescription());
            if (c.getEvidence() == null) {
                lstSummaryEvidence.setItems(FXCollections.observableList(new ArrayList<Evidence>()));
            } else {
                lstSummaryEvidence.setItems(FXCollections.observableList(c.getEvidence()));
            }
        }
    }

    @FXML protected void handleFilterClearAction(ActionEvent e) {
        cbxFilterCaseType.setValue("All");
        txfFilterCases.setText("");
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
        LocalDate today = LocalDate.now();
        calendarCurrentMonth = today.getMonthValue();
        calendarCurrentYear = today.getYear();
        refreshCalendarTable(calendarCurrentMonth, calendarCurrentYear);
    }

    @FXML protected void handleAddNewDefendantAction(ActionEvent e) {
    }

    @FXML protected void handleAddNewComplainantAction(ActionEvent e) {
    }

    @FXML protected void handleAddEvidenceAction(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add Evidence Files");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt", "*.docx", "*.xmlx", "*.doc", "*.xml", "*.pdf"),
                new ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif", "*.jpeg"),
                new ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv", "*.mts"),
                new ExtensionFilter("All Files", "*.*"));
        File evidenceFile = fileChooser.showOpenDialog(stage);
        if (evidenceFile != null) {
            String name = evidenceFile.getName();
            Evidence evidence = new Evidence(name, null, evidenceFile);
            lstAddEvidence.getItems().add(evidence);
        }
    }

    @FXML protected void handleEditEvidenceAction(ActionEvent e) {
    }

    @FXML protected void handleDeleteEvidenceAction(ActionEvent e) {
        Evidence evidence = lstAddEvidence.getSelectionModel().getSelectedItem();
        if (evidence != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setContentText("Are you sure you want to remove this evidence?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                lstAddEvidence.getItems().remove(evidence);
            }
        } else {
            Alert selectionWarning = new Alert(AlertType.WARNING);
            selectionWarning.setTitle("No Evidence Selected");
            selectionWarning.setContentText("No evidence selected to delete");
            selectionWarning.showAndWait();
        }
    }

    @FXML protected void handleAddCaseAction(ActionEvent e) {
    }

    @FXML private Button btnCalendarNext;
    @FXML private Button btnCalendarPrevious;
    @FXML private CheckBox cbxAddIsReturnVisit;
    @FXML private ChoiceBox<Integer> cbxCalendarYear;
    @FXML private ChoiceBox<String> cbxFilterCaseType;
    @FXML private ComboBox<Person> cmbAddComplainant;
    @FXML private ComboBox<Defendant> cmbAddDefendant;
    @FXML private ComboBox<Staff> cmbAddInvestigatingOfficer;
    @FXML private ComboBox<String> cmbAddCaseType;
    @FXML private DatePicker dpkAddIncidentDate;
    @FXML private DatePicker dpkAddReturnDate;
    @FXML private GridPane panCaseSummary;
    @FXML private ListView<Evidence> lstSummaryEvidence;
    @FXML private ListView<Evidence> lstAddEvidence;
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
    @FXML private TextArea txaAddAnimalsInvolved;
    @FXML private TextArea txaAddDetails;
    @FXML private TextArea txaSummaryDetails;
    @FXML private TextField txfAddAddress;
    @FXML private TextField txfAddCaseName;
    @FXML private TextField txfAddCaseNumber;
    @FXML private TextField txfAddLatitude;
    @FXML private TextField txfAddLongitude;
    @FXML private TextField txfFilterCases;
    @FXML private Text txtCalendarMonth;
    @FXML private Text txtSummaryDefendant;
    @FXML private Text txtSummaryCaseName;
    @FXML private Text txtSummaryCaseNumber;
    @FXML private Text txtSummaryCaseType;
    @FXML private Text txtSummaryCourtDate;
    @FXML private Text txtSummaryLatitude;
    @FXML private Text txtSummaryLatitudeValue;
    @FXML private Text txtSummaryLocation;
    @FXML private Text txtSummaryLocationValue;
    @FXML private Text txtSummaryIncidentDate;
    @FXML private Text txtSummaryInvestigatingOfficer;
    @FXML private Text txtSummaryReturnDate;
}
