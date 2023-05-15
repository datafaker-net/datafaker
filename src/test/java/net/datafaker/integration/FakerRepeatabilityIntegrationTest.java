package net.datafaker.integration;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import net.datafaker.Faker;
import net.datafaker.providers.base.AbstractProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.SAME_THREAD)
public class FakerRepeatabilityIntegrationTest {

    @Test
    void shouldCreateRepeatableValues() throws InvocationTargetException, IllegalAccessException {

        Faker faker1 = new Faker(new Random(0));
        Faker faker2 = new Faker(new Random(0));

        Map<String, String> report1 = buildReport(faker1);
        Map<String, String> report2 = buildReport(faker2);

        for (var entry1: report1.entrySet()) {
            assertThat(report2).containsEntry(entry1.getKey(), entry1.getValue());
        }

        for (var entry2: report2.entrySet()) {
            assertThat(report1).containsEntry(entry2.getKey(), entry2.getValue());
        }
    }

    @Test
    void shouldCreateUniqueValues() throws InvocationTargetException, IllegalAccessException {
        Faker faker1 = new Faker();
        Faker faker2 = new Faker();

        Map<String, String> report1 = buildReport(faker1);
        Map<String, String> report2 = buildReport(faker2);

        MapDifference<String, String> difference = Maps.difference(report1, report2);

        assertThat(difference.entriesDiffering()).hasSizeGreaterThan(difference.entriesInCommon().size());
    }

    private static Map<String, String> buildReport(Faker faker) throws IllegalAccessException, InvocationTargetException {
        Map<String, String> result = new HashMap<>();

        // Need to sort the methods since they are sometimes returned in a different order
        Method[] methods = faker.getClass().getMethods();
        List<Method> providerList = Arrays.asList(methods);
        providerList.sort(Comparator.comparing(Method::getName));

        for (Method provider : providerList) {

            if (AbstractProvider.class.isAssignableFrom(provider.getReturnType()) && provider.getParameterCount() == 0) {
                AbstractProvider providerImpl = (AbstractProvider) provider.invoke(faker);

                Method[] generatorMethods = providerImpl.getClass().getDeclaredMethods();

                List<Method> generatorMethodList = Arrays.asList(generatorMethods);
                generatorMethodList.sort(Comparator.comparing(Method::getName));

                for (Method generatorMethod : generatorMethodList) {

                    if (String.class.isAssignableFrom(generatorMethod.getReturnType()) && generatorMethod.getParameterCount() == 0 && Modifier.isPublic(generatorMethod.getModifiers())) {
                        result.put(provider.getName() + "." + generatorMethod.getName(), generatorMethod.invoke(providerImpl).toString());
                    }
                }
            }
        }

        return result;
    }
}
