package com.wisnu.launcher.compiler

import com.squareup.javapoet.*
import java.io.IOException
import java.util.*
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier

class FeatureListWriter(private val featureAnnotatedClass: FeatureAnnotatedClass) {

    @Throws(IOException::class)
    fun write(filer: Filer) {
        val classBuilder = TypeSpec.classBuilder("${featureAnnotatedClass.className.simpleName()}Utils")
        classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        classBuilder.addMethod(createBuildFeaturesMethod())
        JavaFile.builder(featureAnnotatedClass.className.packageName(), classBuilder.build()).build().writeTo(filer)
    }

    private fun createBuildFeaturesMethod(): MethodSpec {
        val methodName = "buildFeatures"
        val localVariableName = "features"
        val featureType = ClassName.get("com.wisnu.launcher.main", "Application")
        val listType = ClassName.get(List::class.java)
        val arrayListType = ClassName.get(ArrayList::class.java)
        val returnType = ParameterizedTypeName.get(listType, featureType)

        val buildFeatures = MethodSpec.methodBuilder(methodName)
            .addModifiers(Modifier.STATIC)
            .addModifiers(Modifier.FINAL)
            .returns(returnType)
            .addStatement("\$T $localVariableName = new \$T<>()", returnType, arrayListType)

        featureAnnotatedClass.features
            .filter { it.loadClassOrNull<Any>() != null }
            .forEach { buildFeatures.addStatement("$localVariableName.add(new $it())") }

        buildFeatures.addStatement("return $localVariableName")

        return buildFeatures.build()
    }

}
