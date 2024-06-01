package net.datafaker.integration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class BackwardsCompatibilityChecker {

    public static void checkClassCompatibility(Class<?> v1, Class<?> v2) {
        checkMethodsCompatibility(v1, v2);
        checkFieldsCompatibility(v1, v2);
    }

    private static void checkMethodsCompatibility(Class<?> v1, Class<?> v2) {
        Method[] v1Methods = v1.getDeclaredMethods();
        Map<String, Method> v2MethodsMap = new HashMap<>();

        for (Method method : v2.getDeclaredMethods()) {
            v2MethodsMap.put(getMethodSignature(method), method);
        }

        for (Method v1Method : v1Methods) {
            if (Modifier.isPublic(v1Method.getModifiers()) || Modifier.isProtected(v1Method.getModifiers())) {
                String signature = getMethodSignature(v1Method);
                if (!v2MethodsMap.containsKey(signature)) {
                    System.out.println("Incompatible: Method " + signature + " is missing in v2.");
                } else {
                    Method v2Method = v2MethodsMap.get(signature);
                    if (v1Method.getModifiers() != v2Method.getModifiers()) {
                        System.out.println("Incompatible: Method " + signature + " has different access modifiers in v2.");
                    }
                }
            }
        }
    }

    private static void checkFieldsCompatibility(Class<?> v1, Class<?> v2) {
        Field[] v1Fields = v1.getDeclaredFields();
        Map<String, Field> v2FieldsMap = new HashMap<>();

        for (Field field : v2.getDeclaredFields()) {
            v2FieldsMap.put(field.getName(), field);
        }

        for (Field v1Field : v1Fields) {
            if (Modifier.isPublic(v1Field.getModifiers()) || Modifier.isProtected(v1Field.getModifiers())) {
                if (!v2FieldsMap.containsKey(v1Field.getName())) {
                    System.out.println("Incompatible: Field " + v1Field.getName() + " is missing in v2.");
                } else {
                    Field v2Field = v2FieldsMap.get(v1Field.getName());
                    if (v1Field.getType() != v2Field.getType() || v1Field.getModifiers() != v2Field.getModifiers()) {
                        System.out.println("Incompatible: Field " + v1Field.getName() + " has different type or access modifiers in v2.");
                    }
                }
            }
        }
    }

    private static String getMethodSignature(Method method) {
        StringBuilder signature = new StringBuilder(method.getName() + "(");
        for (Class<?> paramType : method.getParameterTypes()) {
            signature.append(paramType.getName()).append(",");
        }
        if (method.getParameterCount() > 0) {
            signature.setLength(signature.length() - 1); // remove trailing comma
        }
        signature.append(")");
        return signature.toString();
    }

    public static void main(String[] args) {
        checkClassCompatibility(Version1.class, Version2.class);
    }

    // Example classes for demonstration
    public static class Version1 {
        public void method1() {}
        protected void method2() {}
        public int field1;
        protected String field2;
    }

    public static class Version2 {
        public void method1() {}
        protected void method2() {}
        public int field1;
        protected String field2;
        // method3 and field3 are new additions
        public void method3() {}
        protected double field3;
    }
}
