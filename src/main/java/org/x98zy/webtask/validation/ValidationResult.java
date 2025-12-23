package org.x98zy.webtask.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    private final Map<String, String> errors = new HashMap<>();
    private boolean valid = true;

    public void addError(String field, String message) {
        errors.put(field, message);
        valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    public Map<String, String> getErrors() {
        return new HashMap<>(errors);
    }

    public String getError(String field) {
        return errors.get(field);
    }

    public boolean hasError(String field) {
        return errors.containsKey(field);
    }
}