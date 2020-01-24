package com.wisnu.launcher.compiler

import com.wisnu.launcher.annotations.RegisterFeature
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic
import kotlin.properties.Delegates
import javax.annotation.processing.ProcessingEnvironment

class FeatureProcessor : AbstractProcessor() {
    private var filer: Filer by Delegates.notNull()
    private var messager: Messager by Delegates.notNull()
    private var elementUtils: Elements by Delegates.notNull()

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
        messager = processingEnv.messager
        elementUtils = processingEnv.elementUtils
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = hashSetOf(RegisterFeature::class.java.canonicalName)

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        parseEnv(roundEnv, elementUtils)
            .map { FeatureListWriter(it) }
            .forEach {
                try {
                    it.write(filer)
                } catch (e: IOException) {
                    messager.printMessage(Diagnostic.Kind.ERROR, e.message)
                }
            }
        return true
    }

    private fun parseEnv(roundEnv: RoundEnvironment, elementUtils: Elements): List<FeatureAnnotatedClass> {
        return roundEnv.getElementsAnnotatedWith(RegisterFeature::class.java).map {
            FeatureAnnotatedClass(it as TypeElement, elementUtils)
        }
    }

}
