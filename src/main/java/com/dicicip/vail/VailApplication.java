package com.dicicip.vail;

import com.dicicip.vail.validations.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class VailApplication {

    public static void main(String[] args) {
        SpringApplication.run(VailApplication.class, args);

        HashMap requestBody = new HashMap();
        requestBody.put("name", "tayoo");
        requestBody.put("description", "tayoo desc");
        requestBody.put("type", "bbbz");
//        requestBody.put("expired_on", "2019-02-29");
//        requestBody.put("age", "min:2");

//        Validator validator = new Validator(requestBody);
        Validator validator = new Validator("{\"name\":\"tayoo\",\"description\":\"tayoo desc\",\"type\":\"\",\"age\":[3]}");
        validator.setValidation("name", "required", "max:25");
        validator.setValidation("description", "required");
        validator.setValidation("type", "required", "in:aaa,bbb");
        validator.setValidation("expired_on", "date");
        validator.setValidation("age", "array|min:2");

        System.out.println("isValid --> " + validator.isValid());
        System.out.println("errors --> " + validator.getErrorsInJSONString());
        System.out.println("errorsMap --> " + validator.getErrors());
//        try {
//            validator.getErrors();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }

}
