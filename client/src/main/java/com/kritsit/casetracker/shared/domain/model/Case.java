package com.kritsit.casetracker.shared.domain.model;

import javafx.beans.property.Property;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Case implements Externalizable {
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

    public Case() {
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
        evidenceProperty = new SimpleObjectProperty<List<Evidence>>(new ArrayList<Evidence>());
        returnVisitProperty = new SimpleBooleanProperty();
        returnDateProperty = new SimpleObjectProperty<>();
        caseTypeProperty = new SimpleStringProperty();
    }

    public Case(String caseNumber, String caseName, String description, String animalsInvolved, Staff investigatingOfficer, Incident incident, Defendant defendant, Person complainant, Date nextCourtDate, List<Evidence> evidence, boolean returnVisit, Date returnDate, String caseType, String outcome) {
        caseNumberProperty = new SimpleStringProperty(caseNumber);
        caseNameProperty = new SimpleStringProperty(caseName);
        descriptionProperty = new SimpleStringProperty(description);
        animalsInvolvedProperty = new SimpleStringProperty(animalsInvolved);
        investigatingOfficerProperty = new SimpleObjectProperty<>(investigatingOfficer);
        incidentProperty = new SimpleObjectProperty<>(incident);
        defendantProperty = new SimpleObjectProperty<>(defendant);
        complainantProperty = new SimpleObjectProperty<>(complainant);
        nextCourtDateProperty = new SimpleObjectProperty<>(nextCourtDate);
        outcomeProperty = new SimpleStringProperty(outcome);
        evidenceProperty = new SimpleObjectProperty<>(evidence);
        returnVisitProperty = new SimpleBooleanProperty(returnVisit);
        returnDateProperty = new SimpleObjectProperty<>(returnDate);
        caseTypeProperty = new SimpleStringProperty(caseType);
    }

    public void addEvidence(Evidence evidence) {
        getEvidence().add(evidence);
    }

    // Accessor methods:
    public String getAnimalsInvolved() {
        return animalsInvolvedProperty.get();
    }

    public String getName() {
        return caseNameProperty.get();
    }

    public String getNumber() {
        return caseNumberProperty.get();
    }

    public String getType() {
        return caseTypeProperty.get();
    }

    public Person getComplainant() {
        return complainantProperty.get();
    }

    public String getDescription() {
        return descriptionProperty.get();
    }

    public List<Evidence> getEvidence() {
        return evidenceProperty.get();
    }

    public Incident getIncident() {
        return incidentProperty.get();
    }

    public Staff getInvestigatingOfficer() {
        return investigatingOfficerProperty.get();
    }

    public Date getNextCourtDate() {
        return nextCourtDateProperty.get();
    }

    public Defendant getDefendant() {
        return defendantProperty.get();
    }

    public Date getReturnDate() {
        return returnDateProperty.get();
    }

    public String getRuling() {
        return outcomeProperty.get();
    }

    public boolean isReturnVisit() {
        return returnVisitProperty.get();
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
        animalsInvolvedProperty.set(animalsInvolved);
    }

    public void setName(String name) {
        caseNameProperty.set(name);
    }

    public void setNumber(String caseNumber) {
        caseNumberProperty.set(caseNumber);
    }

    public void setType(String caseType) {
        caseTypeProperty.set(caseType);
    }

    public void setComplainant(Person complainant) {
        complainantProperty.set(complainant);
    }

    public void setDescription(String description) {
        descriptionProperty.set(description);
    }

    public void setIncident(Incident incident) {
        incidentProperty.set(incident);
    }

    public void setInvestigatingOfficer(Staff investigatingOfficer) {
        investigatingOfficerProperty.set(investigatingOfficer);
    }

    public void setNextCourtDate(Date nextCourtDate) {
        nextCourtDateProperty.set(nextCourtDate);
    }

    public void setDefendant(Defendant defendant) {
        defendantProperty.set(defendant);
    }

    public void setReturnDate(Date returnDate) {
        returnDateProperty.set(returnDate);
    }

    public void setReturnVisit(boolean returnVisit) {
        returnVisitProperty.set(returnVisit);
    }

    public void setRuling(String outcome) {
        outcomeProperty.set(outcome);
    }

    public void setEvidence(List<Evidence> evidence) {
        evidenceProperty.set(evidence);
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getNumber());
        out.writeObject(getName());
        out.writeObject(getDescription());
        out.writeObject(getAnimalsInvolved());
        out.writeObject(getInvestigatingOfficer());
        out.writeObject(getIncident());
        out.writeObject(getDefendant());
        out.writeObject(getComplainant());
        out.writeObject(getNextCourtDate());
        out.writeObject(getRuling());
        out.writeObject(getEvidence());
        out.writeBoolean(isReturnVisit());
        out.writeObject(getReturnDate());
        out.writeObject(getType());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setNumber((String) in.readObject());
        setName((String) in.readObject());
        setDescription((String) in.readObject());
        setAnimalsInvolved((String) in.readObject());
        setInvestigatingOfficer((Staff) in.readObject());
        setIncident((Incident) in.readObject());
        setDefendant((Defendant) in.readObject());
        setComplainant((Person) in.readObject());
        setNextCourtDate((Date) in.readObject());
        setRuling((String) in.readObject());
        setEvidence((List<Evidence>) in.readObject());
        setReturnVisit(in.readBoolean());
        setReturnDate((Date) in.readObject());
        setType((String) in.readObject());
    }
}
