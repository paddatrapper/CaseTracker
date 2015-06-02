package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.util.List;

public interface IEditorService {
    Staff getUser();
    List<Case> getCases();
    List<List<Day>> getBlankMonth();
    List<List<Day>> getMonthAppointments(int month, int year);
    List<Staff> getInspectors();
}
