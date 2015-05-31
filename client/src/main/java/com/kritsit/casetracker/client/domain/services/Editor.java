package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.model.Appointment;
import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.shared.domain.model.Case;
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
}
