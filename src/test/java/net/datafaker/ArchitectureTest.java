package net.datafaker;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import net.datafaker.annotations.InternalApi;
import net.datafaker.idnumbers.IdNumberGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class ArchitectureTest {
    /*
     * 1. Don't hold these in static fields - otherwise they will consume memory till the end of all tests
     * 2. Use `@TestInstance(PER_CLASS)` to reuse they between tests
     */
    private final JavaClasses PRODUCTION_CLASSES = new ClassFileImporter().withImportOption(DO_NOT_INCLUDE_TESTS).importPackages("net.datafaker");

    @Test
    public void packagePrivateMethodsShouldBeMarkedAsInternalAPI() {
        ArchRule rule = methods()
            .that().arePackagePrivate()
            .should().beAnnotatedWith(InternalApi.class);

        rule.check(PRODUCTION_CLASSES);
    }

    @Test
    public void idNumberGeneratorImplementations() {
        ArchRule rule = classes().that()
            .areAssignableTo(IdNumberGenerator.class)
            .and(not(type(IdNumberGenerator.class)))
            .and().areNotPrivate()
            .should().beAnnotatedWith(InternalApi.class);

        rule.check(PRODUCTION_CLASSES);
    }
}
