package example

import example.subpackage.ClassInSubpackage

interface Interface {
    fun stringMethod(): String?

    fun referenceMethod(parameter: ClassInSubpackage?): SimpleClass?

    fun primitiveMethod(parameter_with_long__snake_case_name: Boolean): Char
}