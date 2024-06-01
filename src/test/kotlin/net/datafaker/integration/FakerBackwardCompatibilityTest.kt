package net.datafaker.integration

import io.github.classgraph.ClassGraph
import org.junit.jupiter.api.Test
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Modifier


private const val NAMESPACE = "net.datafaker"

class FakerBackwardCompatibilityTest {

    @Test
    fun shouldBeBackwardCompatible() {

//        val referenceModel: Map<String, Any> = Yaml().load(FileReader("/tmp/file.yaml"))
        val currentModel = mutableMapOf<String, Any>()

        ClassGraph().acceptPackages(NAMESPACE).enableClassInfo().scan().use { scanResult ->
            for (className in scanResult.allClasses.names) {
                if (isLibraryClass(className)) {
//                    println(className)
                    val methods = showMethods(className)

                    val result = methods.map {
                        mapOf(
                            it.name to mapOf(
                                "modifierName" to it.modifierName,
                                "returnType" to it.returnType.toString()
                            )
                        )
                    }

                    currentModel[className] = result
                }
            }
        }

//        showIncompatibilities(referenceModel, currentModel)

        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK;
        options.isPrettyFlow = true;

        val yaml = Yaml(options)
        val writer = FileWriter("/tmp/file.yaml")
        yaml.dump(currentModel, writer)
    }

    private fun showIncompatibilities(referenceModel: Map<String, Any>, currentModel: MutableMap<String, Any>) {

        referenceModel.keys.forEach { k ->
            val referenceValues = referenceModel[k] as Map<String, Any>
            val currentValues = currentModel[k] as Map<String, Any>

            val originalModifiers = referenceValues.get("modifier") as Int
            val newModifiers = currentValues.get("modifier") as Int
            if (!isCompatible(originalModifiers, newModifiers)) {
                println(
                    "Method ${referenceValues.get("name")} was weakened from ${Modifier.toString(originalModifiers)} to ${
                        Modifier.toString(
                            newModifiers
                        )
                    }"
                );
            }
        }
    }

    private fun isCompatible(originalModifiers: Int, newModifiers: Int): Boolean {
        return if (Modifier.isPublic(originalModifiers)) {
            Modifier.isPublic(newModifiers)
        } else if (Modifier.isProtected(originalModifiers)) {
            Modifier.isProtected(newModifiers) || Modifier.isPublic(newModifiers)
        } else if (Modifier.isPrivate(originalModifiers)) {
            Modifier.isPrivate(newModifiers)
        } else { // package-private (default)
            !Modifier.isPrivate(newModifiers)
        }
    }

    private fun isLibraryClass(className: String) = !className.endsWith("Test")

    private fun showMethods(classname: String?): List<CompatibilityMethod> {
        return Class.forName(classname).methods
            .filter { Modifier.isPublic(it.modifiers) && it.declaringClass.packageName.startsWith(NAMESPACE) }
            .map { CompatibilityMethod(it.name, Modifier.toString(it.modifiers), it.modifiers, it.returnType) }
    }
}

data class CompatibilityMethod(val name: String, val modifierName: String, val modifier: Int, val returnType: Class<*>)
