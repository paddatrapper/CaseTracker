package com.kritsit.casetracker.client.domain.datastructures;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Case {
    private String caseNumber;
    private String caseName;
    private String description;
    private String animalsInvolved;
    private Staff investigatingOfficer;
    private Incident incident;
    private Defendant defendant;
    private Person complainant;
    private Date nextCourtDate;
    private String outcome;
    private List<Evidence> evidence;
    private boolean returnVisit;
    private Date returnDate;
    private String caseType;

    public Case(String caseNumber, String caseName, String description, String animalsInvolved, Staff investigatingOfficer, Incident incident, Defendant defendant, Person complainant, Date nextCourtDate, List<Evidence> evidence, boolean returnVisit, Date returnDate, String caseType, String outcome) {
        this.caseNumber = caseNumber;
        this.caseName = caseName;
        this.description = description;
        this.animalsInvolved = animalsInvolved;
        this.investigatingOfficer = investigatingOfficer;
        this.incident = incident;
        this.defendant = defendant;
        this.complainant = complainant;
        this.nextCourtDate = nextCourtDate;
        this.evidence = evidence;
        this.returnVisit = returnVisit;
        this.returnDate = returnDate;
        this.caseType = caseType;
        this.outcome = outcome;
    }

    public void addEvidence(Evidence evidence) {
        this.evidence.add(evidence);
    }

    // Accessor methods:
    public String getAnimalsInvolved() {
        return animalsInvolved;
    }

    public String getName() {
        return caseName;
    }

    public String getNumber() {
        return caseNumber;
    }

    public String getType() {
        return caseType;
    }

    public Person getComplainant() {
        return complainant;
    }

    public String getDescription() {
        return description;
    }

    public List<Evidence> getEvidence() {
        return evidence;
    }

    public Incident getIncident() {
        return incident;
    }

    public Staff getInvestigatingOfficer() {
        return investigatingOfficer;
    }

    public Date getNextCourtDate() {
        return nextCourtDate;
    }

    public Defendant getDefendant() {
        return defendant;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getRuling() {
        return outcome;
    }

    public boolean isReturnVisit() {
        return returnVisit;
    }

    // Mutator methods:
    public void setAnimalsInvolved(String animalsInvolved) {
        this.animalsInvolved = animalsInvolved;
    }

    public void setName(String name) {
        this.caseName = name;
    }

    public void setNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public void setType(String caseType) {
        this.caseType = caseType;
    }

    public void setComplainant(Person complainant) {
        this.complainant = complainant;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public void setInvestigatingOfficer(Staff investigatingOfficer) {
        this.investigatingOfficer = investigatingOfficer;
    }

    public void setNextCourtDate(Date nextCourtDate) {
        this.nextCourtDate = nextCourtDate;
    }

    public void setDefendant(Defendant defendant) {
        this.defendant = defendant;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturnVisit(boolean returnVisit) {
        this.returnVisit = returnVisit;
    }

    public void setRuling(String outcome) {
        this.outcome = outcome;
    }

    public void setEvidence(List<Evidence> evidence) {
        this.evidence = evidence;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Case other = (Case) obj;
        return Objects.equals(this.caseNumber, other.caseNumber);
    }
    
    @Override
    public String toString() {
        String result = "Case: ";
        result += caseNumber + " ";
        result += "(" + caseName + ")";
        return result;
    }
}
