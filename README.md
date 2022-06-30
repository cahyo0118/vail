# Web API Validation for Java Project

### Available Validation Rules
Below is a list of all available validation rules and their function:
- required
- in
- date
- required_if

#### required
```
validator.setValidation("columnName", "required");
```

#### in
```
validator.setValidation("columnName", "in:value1,value2");
```

#### date
```
validator.setValidation("columnName", "date");
```

#### required_if
```
validator.setValidation("columnName", "required_if:another_field,value");
```