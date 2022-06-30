package com.dicicip.vail;

import com.dicicip.vail.validations.Validator;
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
        requestBody.put("type", "bbb");
        requestBody.put("expired_on", "2019-02-29");

        Validator validator = new Validator(requestBody);
        validator.setValidation("name", "required", "max:25");
        validator.setValidation("description", "required");
        validator.setValidation("type", "required", "in:aaa,bbb");
        validator.setValidation("expired_on", "date");

        System.out.println("isValid --> " + validator.isValid());
    }

}
