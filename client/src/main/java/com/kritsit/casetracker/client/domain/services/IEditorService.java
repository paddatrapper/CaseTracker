package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.util.List;
import java.util.Map;

public interface IEditorService {
    Staff getUser();
    List<Case> getCases();
    List<List<Day>> getBlankMonth();
    List<List<Day>> getMonthAppointments(int month, int year);
    List<Staff> getInspectors();
    List<String> getCaseTypes();
    List<Defendant> getDefendants();
    List<Person> getComplainants();
    String getNextCaseNumber();
    InputToModelParseResult addCase(Map<String, Object> inputMap);
}
