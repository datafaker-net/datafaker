package net.datafaker;

import net.datafaker.base.AbstractProvider;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.util.ReflectionUtilsPredicates.withModifier;
import static org.reflections.util.ReflectionUtilsPredicates.withParametersCount;
import static org.reflections.util.ReflectionUtilsPredicates.withReturnType;
import static org.reflections.util.ReflectionUtilsPredicates.withReturnTypeAssignableFrom;

class SameSeedTest {
  @Test
  void sameSeedTest() throws IllegalAccessException, InvocationTargetException {
    final int seed = 42;
    final Random random1 = new Random(seed);
    final Random random2 = new Random(seed);
    Faker faker1 = new Faker(random1);
    Faker faker2 = new Faker(random2);
    for (Method m :
        getAllMethods(
            Faker.class,
            withModifier(Modifier.PUBLIC),
            withReturnTypeAssignableFrom(AbstractProvider.class),
            withParametersCount(0))) {
      Class clazz = m.getReturnType();
      for (Method m2 :
          getAllMethods(
              clazz,
              withModifier(Modifier.PUBLIC),
              withReturnType(String.class),
              withParametersCount(0))) {
        assertThat(m2.invoke(m.invoke(faker1)))
            .as(
                "Return values for "
                    + clazz.getName()
                    + "#"
                    + m2.getName()
                    + " for both fakers with start random seed "
                    + seed
                    + " should be same")
            .isEqualTo(m2.invoke(m.invoke(faker2)));
      }
    }
  }
}
