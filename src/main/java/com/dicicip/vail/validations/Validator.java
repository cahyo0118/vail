package com.dicicip.vail.validations;

import com.dicicip.vail.validations.date.DateValidatorUsingDateFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Validator {

    List<HashMap> validations = new ArrayList<>();
    HashMap value = null;

    HashMap errors = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

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
            for (String validator : (String[]) validation.get("validators")) {
                if (validator.equals("required")) {
                    if (value.get(validation.get("columnName")) == null) {
                        addErrorMessage(
                                String.valueOf(validation.get("columnName")),
                                value.get(validation.get("columnName")) + " cannot be empty"
                        );
                        valid = false;
                    }
                }

                if (validator.contains("in:")) {
                    String[] contents = validator.replace("in:", "").split(",");
                    boolean isCharFound = false;

                    for (String content : contents) {
                        if (String.valueOf(value.get(validation.get("columnName"))).equals(content)) {
                            isCharFound = true;
                        }
                    }

                    if (!isCharFound) {
                        addErrorMessage(
                                String.valueOf(validation.get("columnName")),
                                value.get(validation.get("columnName")) + " value must be " + String.join(",", contents)
                        );
                        valid = false;
                    }

                }

                if (validator.equals("date") && value.get(validation.get("columnName")) != null) {
                    DateValidatorUsingDateFormat dateValidator = new DateValidatorUsingDateFormat("yyyy-MM-dd");
                    if (!dateValidator.isValid(String.valueOf(value.get(validation.get("columnName"))))) {
                        valid = false;
                    }
                }
            }
        }

        return valid;
    }

    public List<HashMap> getErrors() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(errors));
        return new ArrayList<>();
    }

    public List<HashMap> getErrorsInJSONString() {
        return new ArrayList<>();
    }

    private void addErrorMessage(String columnName, String message) {

        System.out.println("columnName ==> " + columnName);
        HashMap error = (HashMap) errors.get(columnName);

        if (error != null) {
            List<String> messages = (List<String>) error.get(columnName);

            if (error == null) {
                error = new HashMap();
                messages = new ArrayList<>();
            }

            messages.add(message);
            errors.put(columnName, messages);
        }
    }
}
