package com.kritsit.casetracker.client.domain.services;

import java.util.ArrayList;
import java.util.List;

public class InputToModelParseResult<T> {
    boolean isSuccessful;
    String reason;
    List<String> failedInputs;
    T result;

    public InputToModelParseResult(boolean isSuccessful) {
        this(isSuccessful, null, null);
    }

    public InputToModelParseResult(boolean isSuccessful, T result) {
        this(isSuccessful, null, result);
    }

    public InputToModelParseResult(boolean isSuccessful, String reason) {
        this(isSuccessful, reason, null);
    }

    public InputToModelParseResult(boolean isSuccessful, String reason, T result) {
        this.isSuccessful = isSuccessful;
        this.reason = reason;
        this.result = result;
        failedInputs = new ArrayList<>();
    }

    public void addFailedInput(String input) {
        setIsSuccessful(false);
        failedInputs.add(input);
    }

    public boolean contains(String input) {
        return failedInputs.contains(input);
    }

    public T getResult() {
        return result;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getReason() {
        if (isSuccessful()) {
            return reason;
        }
        if (reason != null && !reason.isEmpty() && failedInputs.isEmpty()) {
            return reason;
        }
        if (failedInputs.isEmpty()) {
            return "Input failed to parse into model";
        }
        StringBuilder message = new StringBuilder(failedInputs.get(0));
        for (int i = 1; i < failedInputs.size(); i++) {
            message.append(", ");
            message.append(failedInputs.get(i));
        }
        int andIndex = message.lastIndexOf(",");
        if (andIndex != -1) {
            message.replace(andIndex, andIndex + 1, " and");
        }
        message.append(" required");
        return message.toString();
    }
}
