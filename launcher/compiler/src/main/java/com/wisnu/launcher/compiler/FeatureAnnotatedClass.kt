package com.wisnu.launcher.compiler

import com.squareup.javapoet.ClassName
import com.wisnu.launcher.annotations.RegisterFeature
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class FeatureAnnotatedClass(element: TypeElement, elementUtils: Elements) {

    val className: ClassName
    val features: Array<String>

    init {
        val packageName = getPackageName(elementUtils, element)
        val originalClassName = getClassName(element, packageName)
        this.className = ClassName.get(packageName, originalClassName)
        this.features = element.getAnnotation(RegisterFeature::class.java).value
    }

    private fun getPackageName(elementUtils: Elements, type: TypeElement): String {
        return elementUtils.getPackageOf(type).qualifiedName.toString()
    }

    private fun getClassName(element: TypeElement, packageName: String): String {
        return element.qualifiedName.toString().substring(packageName.length + 1).replace('.', '$')
    }

    override fun toString(): String {
        return className.toString()
    }

}
