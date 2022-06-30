package com.dicicip.vail.validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Validator {

    List<HashMap> validations = new ArrayList<>();
    HashMap value = null;

    public Validator(HashMap o) {
        value = o;
    }

    public void setValidation(String columnName, String... validators) {


        HashMap validation = new HashMap();
        validation.put("columnName", columnName);
        validation.put("validators", validators);

        validations.add(validation);
    }

    public boolean isValid() {
        boolean valid = true;

        for (int i = 0; i < validations.size(); i++) {

            HashMap validation = validations.get(i);
//            System.out.println("columnName --> " + validation.get("columnName"));
            for (String validator : (String[]) validation.get("validators")) {
//                System.out.println("VALIDATOR --> " + validator);
                if (validator.equals("required")) {
                    if (value.get(validation.get("columnName")) == null) {
                        valid = false;
                    }
                }

                if (validator.contains("in:")) {
                    String[] contents = validator.replace("in:", "").split(",");
                    boolean isCharFound = false;

                    for (String content : contents) {
                        System.out.println("content --> " + content);
                        if (String.valueOf(value.get(validation.get("columnName"))).equals(content)) {
                            isCharFound = true;
                        }
                    }

                    if (!isCharFound) {
                        valid = false;
                    }

                }
            }
        }

        return valid;
    }
}
