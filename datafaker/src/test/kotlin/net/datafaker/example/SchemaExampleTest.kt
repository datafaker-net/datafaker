package net.datafaker.example

import net.datafaker.Faker
import net.datafaker.transformations.Field
import net.datafaker.transformations.JavaObjectTransformer
import net.datafaker.transformations.Schema
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.function.Supplier

class SchemaExampleTest {

    val faker = Faker()

    @Test
    fun `should support kotlin data classes`() {

        val jTransformer = JavaObjectTransformer()
        val schema: Schema<Any, Any> = Schema.of(
            Field.field("firstName", Supplier { faker.name().firstName() }),
            Field.field("lastName", Supplier { faker.name().lastName() }),
            Field.field("birthDate", Supplier { faker.timeAndDate().birthday() }),
            Field.field("id", Supplier { faker.number().positive() })
        )

        val person = jTransformer.apply(Person::class.java, schema) as Person

        assertThat(person.id).isPositive()
        assertThat(person.birthDate).isInThePast()
        assertThat(person.firstName).isMixedCase()
        assertThat(person.lastName).isMixedCase()
    }
}

data class Person(
    var firstName: String,
    var lastName: String,
    var birthDate: LocalDate,
    var id: Int
)

