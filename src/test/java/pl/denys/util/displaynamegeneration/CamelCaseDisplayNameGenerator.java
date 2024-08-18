package pl.denys.util.displaynamegeneration;

import org.junit.jupiter.api.DisplayNameGenerator.Simple;

import java.lang.reflect.Method;

public class CamelCaseDisplayNameGenerator extends Simple {
    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return replaceCamelCaseToSpace(super.generateDisplayNameForMethod(testClass, testMethod));
    }

    private String replaceCamelCaseToSpace(String sample) {
        var format =
                String.format(
                        "%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])");
        String upperFirstLetter = sample.substring(0, 1).toUpperCase();
        String camelCaseToSpace = sample.replaceAll(format, " ").toLowerCase().substring(1);
        return upperFirstLetter + camelCaseToSpace;
    }
}
