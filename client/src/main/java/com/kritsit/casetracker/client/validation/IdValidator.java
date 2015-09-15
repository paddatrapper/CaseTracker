package com.kritsit.casetracker.client.validation;

public class IdValidator implements IValidator<String> {
    public boolean validate(Object obj) {
        if (obj == null || obj.getClass() != String.class) {
            return false;
        }
        String stringObj = (String) obj;
        return isID(stringObj);
    }

    private boolean isID(String id) {
        if (id.length() != 13) {
            return false;
        }
        int control = Integer.parseInt("" + id.charAt(id.length() - 1));
        int controlTest = calculateControl(id);
        return control == controlTest;
    }

    private int calculateControl(String id) {
        String[] aString = new String[6];
        int i = 0;
        for (int j = 0; i < aString.length; j = j + 2) {
            aString[i++] = String.valueOf(id.charAt(j));
        }
        int a = 0;
        for (String digit : aString) {
            a += Integer.parseInt(digit);
        }
        
        String bString = "";
        for (i = 1; i < id.length() - 1; i = i + 2) {
            bString += id.charAt(i);
        }
        int b = Integer.parseInt(bString) * 2;
        bString = String.valueOf(b);
        b = 0;
        for (i = 0; i < bString.length(); i++) {
            b += Integer.parseInt(String.valueOf(bString.charAt(i)));
        }

        int c = a + b;
        String cString = String.valueOf(c);
        int ones = Integer.parseInt(String.valueOf(cString.charAt(cString.length() - 1)));
        return (c % 10 == 0) ? 0 : Math.abs(10 - ones);
    }
}
