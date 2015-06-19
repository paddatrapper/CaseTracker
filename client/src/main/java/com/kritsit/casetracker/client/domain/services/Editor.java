package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.model.Appointment;
import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Editor implements IEditorService {
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
            cases = connection.getCases(null);
        }
        return cases;
    }

    public List<List<Day>> getBlankMonth() {
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
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, 1);
        LocalDate day = LocalDate.of(year, month, 1);
        int numberOfDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); 
        int startOfMonth = day.getDayOfWeek().getValue() - 1; 
        List<List<Day>> monthList = getBlankMonth();
        List<Appointment> appointments = new ArrayList<>();
        for (Case c : getCases()) {
            if (getUser().equals(c.getInvestigatingOfficer())) {
                if (c.isReturnVisit()) {
                    String details = "Return visit: " + c.getName();
                    Appointment appointment = new Appointment(c.getReturnDate(), details);
                    appointments.add(appointment);
                }
                if (c.getNextCourtDate() != null) {
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
                    d.addAppointment(appointment);
                }
            }
            week.set(column, d);
            day = day.plusDays(1L);
        }
        return monthList;
    }

    public List<Staff> getInspectors() {
        return connection.getInspectors();
    }

    public List<String> getCaseTypes() {
        List<String> caseTypes = new ArrayList<>();
        for (Case c : getCases()) {
            if (!caseTypes.contains(c.getType())) {
                caseTypes.add(c.getType());
            }
        }
        return caseTypes;
    }

    public List<Defendant> getDefendants() {
        List<Defendant> defendants = new ArrayList<>();
        for (Case c : getCases()) {
            if (!defendants.contains(c.getDefendant())) {
                defendants.add(c.getDefendant());
            }
        }
        return defendants;
    }

    public List<Person> getComplainants() {
        List<Person> complainants = new ArrayList<>();
        for (Case c : getCases()) {
            if (!complainants.contains(c.getComplainant())) {
                complainants.add(c.getComplainant());
            }
        }
        return complainants;
    }

    public String getNextCaseNumber() {
        LocalDate today = LocalDate.now();
        String current = connection.getLastCaseNumber();
        String[] parts = current.split("-");
        int nextNumber = 1;
        int currentMonth = Integer.parseInt(parts[1]);
        int currentYear = Integer.parseInt(parts[2]);
        if (currentMonth == today.getMonthValue() && currentYear == today.getYear()) {
            nextNumber = Integer.parseInt(parts[0]) + 1;
        }
        String nextSequence = String.format("%04d", nextNumber);
        String nextMonth = String.format("%02d", today.getMonthValue());
        return today.getYear() + "-" + nextMonth + "-" + nextSequence;
    }
}
