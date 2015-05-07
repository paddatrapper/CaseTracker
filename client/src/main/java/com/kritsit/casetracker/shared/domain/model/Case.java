package com.kritsit.casetracker.shared.domain.model;

import javafx.beans.property.Property;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Case implements Serializable {
    private static final long serialVersionUID = 10L;
    private StringProperty caseNumberProperty;
    private StringProperty caseNameProperty;
    private StringProperty descriptionProperty;
    private StringProperty animalsInvolvedProperty;
    private ObjectProperty<Staff> investigatingOfficerProperty;
    private ObjectProperty<Incident> incidentProperty;
    private ObjectProperty<Defendant> defendantProperty;
    private ObjectProperty<Person> complainantProperty;
    private ObjectProperty<Date> nextCourtDateProperty;
    private StringProperty outcomeProperty;
    private ObjectProperty<List<Evidence>> evidenceProperty;
    private BooleanProperty returnVisitProperty;
    private ObjectProperty<Date> returnDateProperty;
    private StringProperty caseTypeProperty;

    public Case(String caseNumber, String caseName, String description, String animalsInvolved, Staff investigatingOfficer, Incident incident, Defendant defendant, Person complainant, Date nextCourtDate, List<Evidence> evidence, boolean returnVisit, Date returnDate, String caseType, String outcome) {
        caseNumberProperty = new SimpleStringProperty();
        caseNameProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        animalsInvolvedProperty = new SimpleStringProperty();
        investigatingOfficerProperty = new SimpleObjectProperty<>();
        incidentProperty = new SimpleObjectProperty<>();
        defendantProperty = new SimpleObjectProperty<>();
        complainantProperty = new SimpleObjectProperty<>();
        nextCourtDateProperty = new SimpleObjectProperty<>();
        outcomeProperty = new SimpleStringProperty();
        evidenceProperty = new SimpleObjectProperty<>();
        returnVisitProperty = new SimpleBooleanProperty();
        returnDateProperty = new SimpleObjectProperty<>();
        caseTypeProperty = new SimpleStringProperty();

        setNumber(caseNumber);
        setName(caseName);
        setDescription(description);
        setAnimalsInvolved(animalsInvolved);
        setInvestigatingOfficer(investigatingOfficer);
        setIncident(incident);
        setDefendant(defendant);
        setComplainant(complainant);
        setNextCourtDate(nextCourtDate);
        setEvidence(evidence);
        setReturnVisit(returnVisit);
        setReturnDate(returnDate);
        setType(caseType);
        setRuling(outcome);
    }

    public void addEvidence(Evidence evidence) {
        evidenceProperty.getValue().add(evidence);
    }

    // Accessor methods:
    public String getAnimalsInvolved() {
        return animalsInvolvedProperty.getValue();
    }

    public String getName() {
        return caseNameProperty.getValue();
    }

    public String getNumber() {
        return caseNumberProperty.getValue();
    }

    public String getType() {
        return caseTypeProperty.getValue();
    }

    public Person getComplainant() {
        return complainantProperty.getValue();
    }

    public String getDescription() {
        return descriptionProperty.getValue();
    }

    public List<Evidence> getEvidence() {
        return evidenceProperty.getValue();
    }

    public Incident getIncident() {
        return incidentProperty.getValue();
    }

    public Staff getInvestigatingOfficer() {
        return investigatingOfficerProperty.getValue();
    }

    public Date getNextCourtDate() {
        return nextCourtDateProperty.getValue();
    }

    public Defendant getDefendant() {
        return defendantProperty.getValue();
    }

    public Date getReturnDate() {
        return returnDateProperty.getValue();
    }

    public String getRuling() {
        return outcomeProperty.getValue();
    }

    public boolean isReturnVisit() {
        return returnVisitProperty.getValue();
    }

    public Property caseNumberProperty() {
        return caseNumberProperty();
    }

    public Property caseNameProperty() {
        return caseNameProperty();
    }

    public Property caseTypeProperty() {
        return caseTypeProperty();
    }

    // Mutator methods:
    public void setAnimalsInvolved(String animalsInvolved) {
        animalsInvolvedProperty.setValue(animalsInvolved);
    }

    public void setName(String name) {
        caseNameProperty.setValue(name);
    }

    public void setNumber(String caseNumber) {
        caseNumberProperty.setValue(caseNumber);
    }

    public void setType(String caseType) {
        caseTypeProperty.setValue(caseType);
    }

    public void setComplainant(Person complainant) {
        complainantProperty.setValue(complainant);
    }

    public void setDescription(String description) {
        descriptionProperty.setValue(description);
    }

    public void setIncident(Incident incident) {
        incidentProperty.setValue(incident);
    }

    public void setInvestigatingOfficer(Staff investigatingOfficer) {
        investigatingOfficerProperty.setValue(investigatingOfficer);
    }

    public void setNextCourtDate(Date nextCourtDate) {
        nextCourtDateProperty.setValue(nextCourtDate);
    }

    public void setDefendant(Defendant defendant) {
        defendantProperty.setValue(defendant);
    }

    public void setReturnDate(Date returnDate) {
        returnDateProperty.setValue(returnDate);
    }

    public void setReturnVisit(boolean returnVisit) {
        returnVisitProperty.setValue(returnVisit);
    }

    public void setRuling(String outcome) {
        outcomeProperty.setValue(outcome);
    }

    public void setEvidence(List<Evidence> evidence) {
        evidenceProperty.setValue(evidence);
    }

    @Override
    public int hashCode() {
        return ((getNumber() + getName() + getDescription() + getAnimalsInvolved() + getRuling() + getType()).hashCode() + getInvestigatingOfficer().hashCode() + getIncident().hashCode() + getDefendant().hashCode() + getComplainant().hashCode()) / 3;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return obj.hashCode() == hashCode();
    }
    
    @Override
    public String toString() {
        String result = "Case: ";
        result += getNumber() + " ";
        result += "(" + getName() + ")";
        return result;
    }
}
