# Web API Validation for Java Project

### Available Validation Rules
Below is a list of all available validation rules and their function:
- [required](https://github.com/cahyo0118/vail#required)
- [in](https://github.com/cahyo0118/vail#in)
- [date](https://github.com/cahyo0118/vail#date)
- [required_if](https://github.com/cahyo0118/vail#required_if)
- [min](https://github.com/cahyo0118/vail#min)
- [max](https://github.com/cahyo0118/vail#max)
- [size](https://github.com/cahyo0118/vail#size)

### Installation
The best way to install Vail is quickly and easily with maven.
To install the most recent version, add this depedency to your `pom.xml`:
```xml
<dependency>
    <groupId>com.dicicip</groupId>
    <artifactId>vail</artifactId>
    <version>[selected-version]</version>
</dependency>
```

### Usage
```java
Validator validator = new Validator(requestBodyHashMap);
validator.setValidation("name", "required", "max:25");
validator.setValidation("description", "required");

System.out.println("isValid --> " + validator.isValid());
```
#### required
The field under validation must be present in the input data and not empty
```
validator.setValidation("columnName", "required");
```

#### in
The field under validation must be included in the given list of values
```
validator.setValidation("columnName", "in:value1,value2");
```

#### date
The field under validation must be date
```
validator.setValidation("columnName", "date");
```

#### required_if
The field under validation must be present and not empty if the anotherfield field is equal to any value.
```
validator.setValidation("columnName", "required_if:another_field,value");
```

#### min
The field under validation must have a minimum value. Strings, numerics, arrays are evaluated in the same fashion as the [size]((https://github.com/cahyo0118/vail#size)) rule.

#### max
The field under validation must have a maximum value. Strings, numerics, arrays are evaluated in the same fashion as the [size]((https://github.com/cahyo0118/vail#size)) rule.

#### size
The field under validation must have a size matching the given value
```
// Validate that a string is exactly 12 characters long...
validator.setValidation("columnName", "size:12");

// Validate that a provided integer equals 12...
validator.setValidation("columnName", "integer|size:12");

// Validate that an array has exactly 12 elements...
validator.setValidation("columnName", "array|size:12");
```

### Error Messages


## License

Vail is licensed under the [MIT License](http://opensource.org/licenses/MIT).

Copyright 2022 [cahyo0118](https://github.com/cahyo0118/)