package com.dicicip.vail.validations;

import com.dicicip.vail.validations.date.DateValidatorUsingDateFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Validator {

    List<HashMap> validations = new ArrayList<>();
    HashMap value = null;

    HashMap<String, Object> errors = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    public Validator(HashMap o) {
        value = o;
    }

    public Validator(String jsonString) {
        try {
            value = objectMapper.readValue(jsonString, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
                    if (value.get(validation.get("columnName")) == null || value.get(validation.get("columnName")).equals("")) {
                        addErrorMessage(
                                String.valueOf(validation.get("columnName")),
                                validation.get("columnName") + " cannot be empty"
                        );
                        valid = false;
                    }
                }

                if (validator.contains("in:") && !validator.contains("min:")) {
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
                                validation.get("columnName") + " value must be " + String.join(",", contents)
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

////                Untested
//                if (validator.contains("required_if:")) {
//                    String[] contents = validator.replace("required_if:", "").split(",");
//                    boolean isCharFound = false;
//                    if (value.get(contents[0]) == null || !String.valueOf(value.get(contents[0])).equals(contents[1])) {
//                        isCharFound = true;
//                    }
//
//                    if (!isCharFound) {
//                        addErrorMessage(
//                                String.valueOf(validation.get("columnName")),
//                                validation.get("columnName") + " value must be " + String.join(",", contents)
//                        );
//                        valid = false;
//                    }
//
//                }
//
////                Untested
//                if (validator.contains("required_with:")) {
//                    String[] contents = validator.replace("required_with:", "").split(",");
//                    boolean isAllFieldFounds = true;
//
//                    for (String content : contents) {
//
//                        if (value.get(validation.get(content)) == null || String.valueOf(value.get(validation.get(content))).equals("")) {
//                            isAllFieldFounds = false;
//                        }
//
//                    }
//
//                    if (value.get(validation.get("columnName")) == null || isAllFieldFounds) {
//                        addErrorMessage(
//                                String.valueOf(validation.get("columnName")),
//                                validation.get("columnName") + " value must be " + String.join(",", contents)
//                        );
//                        valid = false;
//                    }
//
//                }
//
//                //                Untested
//                if (validator.equals("email")) {
//                    if (value.get(validation.get("columnName")) == null) {
//                        addErrorMessage(
//                                String.valueOf(validation.get("columnName")),
//                                validation.get("columnName") + " cannot be empty"
//                        );
//                        valid = false;
//                    }
//                }
//
//                //                Untested
//                if (validator.equals("email")) {
//                    if (value.get(validation.get("columnName")) == null) {
//                        addErrorMessage(
//                                String.valueOf(validation.get("columnName")),
//                                validation.get("columnName") + " cannot be empty"
//                        );
//                        valid = false;
//                    }
//                }
//
//                //                Untested
//                if (validator.equals("url")) {
//                    if (value.get(validation.get("columnName")) == null) {
//                        addErrorMessage(
//                                String.valueOf(validation.get("columnName")),
//                                validation.get("columnName") + " cannot be empty"
//                        );
//                        valid = false;
//                    }
//                }
//
//                //                Untested
//                if (validator.equals("boolean")) {
//                    if (value.get(validation.get("columnName")) == null) {
//                        addErrorMessage(
//                                String.valueOf(validation.get("columnName")),
//                                validation.get("columnName") + " cannot be empty"
//                        );
//                        valid = false;
//                    }
//                }

                if (validator.contains("min:")) {

                    String[] contents = validator.split("\\|");


                    if (contents.length > 1) {
//                        not digit

                        String type = contents[0];
                        int validationValue = Integer.parseInt(contents[1].replace("min:", ""));

                        if (type.equals("integer")) {
                            if (value.get(validation.get("columnName")) != null && Integer.parseInt(String.valueOf(value.get(validation.get("columnName")))) < validationValue) {
                                addErrorMessage(
                                        String.valueOf(validation.get("columnName")),
                                        validation.get("columnName") + " must must be more than " + validationValue
                                );
                                valid = false;

                            }
                        }

                        if (type.equals("array")) {

                            try {

                                JSONArray array = new JSONArray(objectMapper.writeValueAsString(value.get(validation.get("columnName"))));

                                if (value.get(validation.get("columnName")) != null && array.length() < validationValue) {
                                    addErrorMessage(
                                            String.valueOf(validation.get("columnName")),
                                            validation.get("columnName") + " must have at least " + validationValue + " items"
                                    );
                                    valid = false;
                                }
                            } catch (Exception exArray) {
                                addErrorMessage(
                                        String.valueOf(validation.get("columnName")),
                                        "Please use valid array format"
                                );
                                valid = false;
                            }

                        }

                    } else {
//                        digit
                        int digit = Integer.parseInt(contents[0].replace("min:", ""));

                        if (value.get(validation.get("columnName")) != null && String.valueOf(value.get(validation.get("columnName"))).length() < digit) {
                            addErrorMessage(
                                    String.valueOf(validation.get("columnName")),
                                    validation.get("columnName") + " must have at least " + digit + " digits"
                            );
                            valid = false;

                        }
                    }
                }

            }
        }

        return valid;
    }

    public HashMap<String, Object> getErrors() {
        return errors;
    }

    public String getErrorsInJSONString() {
        try {
            return objectMapper.writeValueAsString(errors);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private void addErrorMessage(String columnName, String message) {

        System.out.println("---------------------------------------------");
        System.out.println("columnName ==> " + columnName);
        System.out.println("message ==> " + message);

        List<String> messages = (List<String>) errors.get(columnName);

        if (messages == null) {
            messages = new ArrayList<>();
        }

        messages.add(message);
        errors.put(columnName, messages);

    }
}
