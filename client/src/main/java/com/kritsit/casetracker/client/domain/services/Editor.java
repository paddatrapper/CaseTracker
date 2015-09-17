package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.model.Appointment;
import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.client.domain.model.Period;
import com.kritsit.casetracker.client.validation.BooleanValidator;
import com.kritsit.casetracker.client.validation.DateValidator;
import com.kritsit.casetracker.client.validation.DoubleValidator;
import com.kritsit.casetracker.client.validation.EmailValidator;
import com.kritsit.casetracker.client.validation.IdValidator;
import com.kritsit.casetracker.client.validation.IValidator;
import com.kritsit.casetracker.client.validation.StringValidator;
import com.kritsit.casetracker.client.validation.TelephoneValidator;
import com.kritsit.casetracker.client.validation.Validator;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.model.Incident;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class Editor implements IEditorService {
    private final Logger logger = LoggerFactory.getLogger(Editor.class);
    private List<Case> cases;
    Staff user;
    IConnectionService connection;

    public Editor(Staff user, IConnectionService connection) {
        this.user = user;
        this.connection = connection;
    }

    public Staff getUser() {
        return user;
    }

    public List<Case> getCases() {
        if (cases == null) {
            logger.info("Retrieving cases from the server");
            cases = connection.getCases(null);
        }
        return cases;
    }

    public List<Case> refreshCases() {
        cases = null;
        return getCases();
    }

    public List<List<Day>> getBlankMonth() {
        logger.info("Building blank month model");
        List<List<Day>> blankMonth = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<Day> week = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                week.add(new Day());
            }
            blankMonth.add(week);
        }
        return blankMonth;
    }

    public List<List<Day>> getMonthAppointments(int month, int year) {
        logger.info("Fetching appointments for {}/{}", month, year);
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, 1);
        LocalDate day = LocalDate.of(year, month, 1);
        int numberOfDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int startOfMonth = day.getDayOfWeek().getValue() - 1;
        List<List<Day>> monthList = getBlankMonth();
        List<Appointment> appointments = new ArrayList<>();
        for (Case c : getCases()) {
            if (getUser().equals(c.getInvestigatingOfficer())) {
                logger.debug("Found case for user: {}", c);
                if (c.isReturnVisit()) {
                    logger.debug("Adding return visit for {}", c.getName());
                    String details = "Return visit: " + c.getName();
                    Appointment appointment = new Appointment(c.getReturnDate(), details);
                    appointments.add(appointment);
                }
                if (c.getNextCourtDate() != null) {
                    logger.debug("Adding court date for {}", c.getName());
                    String details = "Court date: " + c.getName();
                    Appointment appointment = new Appointment(c.getNextCourtDate(), details);
                    appointments.add(appointment);
                }
            }
        }

        for (int i = 1; i <= numberOfDays; i++) {
            int row = (startOfMonth + i - 1) / 7;
            int column = (startOfMonth + i - 1) % 7;
            List<Day> week = monthList.get(row);
            Day d = new Day(i);
            for (Appointment appointment : appointments) {
                if (day.equals(appointment.getDate())) {
                    logger.debug("Adding appointment to {}/{}/{}", i, month, year);
                    d.addAppointment(appointment);
                }
            }
            week.set(column, d);
            day = day.plusDays(1L);
        }
        return monthList;
    }

    public List<Staff> getInspectors() {
        logger.info("Fetching inspectors");
        return connection.getInspectors();
    }

    public List<String> getCaseTypes() {
        logger.info("Building case type list");
        List<String> caseTypes = new ArrayList<>();
        for (Case c : getCases()) {
            if (!caseTypes.contains(c.getType())) {
                logger.debug("Adding case type {}", c.getType());
                caseTypes.add(c.getType());
            }
        }
        return caseTypes;
    }

    public List<Defendant> getDefendants() {
        logger.info("Building defendant list");
        List<Defendant> defendants = new ArrayList<>();
        for (Case c : getCases()) {
            if (!defendants.contains(c.getDefendant())) {
                logger.debug("Adding defendant {}", c.getDefendant());
                defendants.add(c.getDefendant());
            }
        }
        return defendants;
    }

    public List<Person> getComplainants() {
        logger.info("Building complainant list");
        List<Person> complainants = new ArrayList<>();
        for (Case c : getCases()) {
            if (!complainants.contains(c.getComplainant())) {
                logger.debug("Adding complainant {}", c.getComplainant());
                complainants.add(c.getComplainant());
            }
        }
        return complainants;
    }

    public String getNextCaseNumber() {
        logger.info("Generating next case number");
        LocalDate today = LocalDate.now();
        logger.debug("Fetching last case number");
        String current = connection.getLastCaseNumber();
        String[] parts = current.split("-");
        int nextNumber = 1;
        int currentYear = Integer.parseInt(parts[0]);
        int currentMonth = Integer.parseInt(parts[1]);
        if (currentMonth == today.getMonthValue() && currentYear == today.getYear()) {
            logger.debug("Last case number references current month");
            nextNumber = Integer.parseInt(parts[2]) + 1;
        }
        String nextSequence = String.format("%04d", nextNumber);
        String nextMonth = String.format("%02d", today.getMonthValue());
        return today.getYear() + "-" + nextMonth + "-" + nextSequence;
    }

    public InputToModelParseResult<Case> addCase(Map<String, Object> inputMap) {
        InputToModelParseResult<Case> result = createCase(inputMap);
        if (result.isSuccessful()) {
            logger.debug("Adding case to server");
            boolean isAdded = connection.addCase(result.getResult());
            String reason = (isAdded) ? "Case uploaded successfully" :
                "Unable to upload case to server. Please see log for details";
            result = new InputToModelParseResult<Case>(isAdded, reason, result.getResult());
        }
        return result;
    }

    public InputToModelParseResult<Case> editCase(Map<String, Object> inputMap) {
        InputToModelParseResult<Case> result = createCase(inputMap);
        if (result.isSuccessful()) {
            logger.debug("Requesting case update from server");
            boolean isAdded = connection.editCase(result.getResult());
            String reason = (isAdded) ? "Case updated successfully" :
                "Unable to update case. Please see log for details";
            result = new InputToModelParseResult<Case>(isAdded, reason, result.getResult());
        }
        return result;
    }

    private InputToModelParseResult<Case> createCase(Map<String, Object> inputMap) {
        if (inputMap == null || inputMap.isEmpty()) {
            logger.debug("InputMap empty or null. Aborting");
            return new InputToModelParseResult<Case>(false, "Required information missing");
        }
        logger.info("Edit case {}", inputMap.get("caseName"));
        InputToModelParseResult<Case> result = validateCase(inputMap);
        if (!result.isSuccessful()) {
            return result;
        }
        Case c = parseCase(inputMap);
        result.setResult(c);
        return result;
    }

    private InputToModelParseResult<Case> validateCase(Map<String, Object> inputMap) {
        InputToModelParseResult<Case> result = new InputToModelParseResult<>(true);
        for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
            String inputKey = getHumanReadableName(entry.getKey());
            IValidator validator = null;
            switch (entry.getKey()) {
                case "caseNumber" :
                case "caseName" :
                case "caseType" :
                case "region" :
                case "animalsInvolved" :
                    validator = new StringValidator();
                    break;
                case "incidentDate" :
                    validator = new DateValidator(Period.BEFORE, LocalDate.now().plusDays(1));
                    break;
                case "investigatingOfficer" :
                    validator = new Validator<Staff>(Staff.class);
                    break;
                case "complainant" :
                    validator = new Validator<Person>(Person.class);
                    break;
                case "defendant" :
                    validator = new Validator<Defendant>(Defendant.class);
                    break;
                case "isReturnVisit" :
                    validator = new BooleanValidator();
                    break;
                case "returnDate" :
                    if ((boolean) inputMap.get("isReturnVisit")) {
                        validator = new DateValidator();
                    }
                    break;
                case "longitude" :
                case "latitude" :
                    String address = (String) inputMap.get("address");
                    if (address.isEmpty()) {
                        validator = new DoubleValidator();
                    }
                    break;
                case "address" :
                    String longitude = inputMap.get("longitude").toString();
                    String latitude = inputMap.get("latitude").toString();
                    if (longitude.isEmpty() && latitude.isEmpty()) {
                        validator = new StringValidator();
                    }
                    break;
            }
            if (validator != null && !validator.validate(entry.getValue())) {
                if (inputKey.equals("Address")) {
                    String longitude = inputMap.get("longitude").toString();
                    String latitude = inputMap.get("latitude").toString();
                    if (longitude.isEmpty() && latitude.isEmpty()) {
                        logger.debug("Input failed parsing: {}", inputKey);
                        result.addFailedInput(inputKey);
                    }
                } else if (inputKey.equals("Latitude") || inputKey.equals("Longitude")) {
                    String address = (String) inputMap.get("address");
                    if (address.isEmpty() && !result.contains("Address")) {
                        logger.debug("Input failed parsing: {}", inputKey);
                        result.addFailedInput(inputKey);
                    }
                } else {
                    logger.debug("Input failed parsing: {}", inputKey);
                    result.addFailedInput(inputKey);
                }
            }
        }
        return result;
    }

    private Case parseCase(Map<String, Object> inputMap) {
        logger.info("Parsing case {}", inputMap.get("caseName"));
        String caseNumber = (String) inputMap.get("caseNumber");
        String caseName = (String) inputMap.get("caseName");
        String caseType = (String) inputMap.get("caseType");
        String details = (String) inputMap.get("details");
        String animalsInvolved = (String) inputMap.get("animalsInvolved");
        Staff investigatingOfficer = (Staff) inputMap.get("investigatingOfficer");
        LocalDate incidentDate = (LocalDate) inputMap.get("incidentDate");
        String address = (String) inputMap.get("address");
        String region = (String) inputMap.get("region");
        boolean isReturnVisit = (boolean) inputMap.get("isReturnVisit");
        LocalDate returnDate = (LocalDate) inputMap.get("returnDate");
        Defendant defendant = (Defendant) inputMap.get("defendant");
        Person complainant = (Person) inputMap.get("complainant");
        List<Evidence> evidence = (List<Evidence>) inputMap.get("evidence");

        Incident incident = null;
        if (address == null || address.isEmpty()) {
            double longitude = Double.parseDouble(inputMap.get("longitude").toString());
            double latitude = Double.parseDouble(inputMap.get("latitude").toString());
            incident = new Incident(-1, longitude, latitude, region, incidentDate,
                    Incident.getDefaultFollowUpDate(incidentDate), false);
        } else {
            incident = new Incident(-1, address, region, incidentDate,
                    Incident.getDefaultFollowUpDate(incidentDate), false);
        }
        Case c = new Case(caseNumber, caseName, details, animalsInvolved,
                investigatingOfficer, incident, defendant, complainant, null,
                evidence, isReturnVisit, returnDate, caseType, null);
        return c;
    }

    public InputToModelParseResult<Person> createPerson(Map<String, Object> inputMap) {
        if (inputMap == null || inputMap.isEmpty()) {
            logger.debug("InputMap empty or null. Aborting");
            return new InputToModelParseResult<Person>(false, "Required information missing");
        }
        logger.info("Creating person {} {}", inputMap.get("firstName"),
                inputMap.get("lastName"));
        InputToModelParseResult<Person> result = validatePerson(inputMap);
        if (!result.isSuccessful()) {
            return result;
        }
        Person p = parsePerson(inputMap);
        result.setResult(p);
        return result;
    }

    private InputToModelParseResult<Person> validatePerson(Map<String, Object> inputMap) {
        InputToModelParseResult<Person> result = new InputToModelParseResult<>(true);
        for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
            String inputKey = getHumanReadableName(entry.getKey());
            IValidator validator = null;
            switch (entry.getKey()) {
                case "id" :
                    String id = (String) inputMap.get("id");
                    if (id != null && !id.isEmpty()) {
                        validator = new IdValidator();
                    }
                    break;
                case "telephoneNumber" :
                    String telephoneNumber = (String) inputMap.get("telephoneNumber");
                    if (telephoneNumber != null && !telephoneNumber.isEmpty()) {
                        validator = new TelephoneValidator();
                    }
                    break;
                case "emailAddress" :
                    String emailAddress = (String) inputMap.get("emailAddress");
                    if (emailAddress != null && !emailAddress.isEmpty()) {
                        validator = new EmailValidator();
                    }
                    break;
                case "firstName" :
                    String firstName = (String) inputMap.get("firstName");
                    if (firstName == null || firstName.isEmpty()) {
                        break;
                    }
                case "address" :
                    String address = (String) inputMap.get("address");
                    if (address == null || address.isEmpty()) {
                        break;
                    }
                case "lastName" :
                    validator = new StringValidator();
                    break;
            }
            if (validator != null && !validator.validate(entry.getValue())) {
                logger.debug("Input failed parsing: {}", inputKey);
                result.addFailedInput(inputKey);
            }
        }
        return result;
    }

    private Person parsePerson(Map<String, Object> inputMap) {
        logger.info("Parsing person {}", inputMap.get("lastName"));
        int indexId = -1;
        String id = (String) inputMap.get("id");
        String firstName = (String) inputMap.get("firstName");
        String lastName = (String) inputMap.get("lastName");
        String address = (String) inputMap.get("address");
        String telephoneNumber = (String) inputMap.get("telephoneNumber");
        String emailAddress = (String) inputMap.get("emailAddress");
        
        return new Person(indexId, id, firstName, lastName, address, 
                telephoneNumber, emailAddress);
    }

    private String getHumanReadableName(String camelCaseString) {
        String result = "";
        for (int i = 0; i < camelCaseString.length(); i++) {
            char letter = camelCaseString.charAt(i);
            if (Character.isUpperCase(letter)) {
                result += " " + letter;
            } else {
                result += letter;
            }
        }

        String firstLetter = result.substring(0, 1).toUpperCase();
        result = firstLetter + result.substring(1).toLowerCase();
        return result;
    }
}
