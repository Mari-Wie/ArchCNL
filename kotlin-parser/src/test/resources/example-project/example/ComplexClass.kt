package example

import example.subpackage.ClassInSubpackage

import example.anotherPackage.AnotherClass

@Deprecated("")
class ComplexClass : EmptyClass, Interface {
    private val radius: Double
    private val area: Double

    @Deprecated("")
    constructor(diameter: Double) {
        radius = diameter * 0.5
        area = calculateArea()
    }

    constructor(
        radius: Double, otherHalfOfRadius: Double
    ) {
        val diameter = radius + otherHalfOfRadius
        this.radius = diameter * 0.5
        area = calculateArea()
    }

    private fun calculateArea(): Double {
        return radius * radius * PI
    }

    override fun stringMethod(): String {
        return "Circle with area: $area"
    }

    override fun referenceMethod(parameter: ClassInSubpackage?): SimpleClass? {
        // some important comment
        return null
    }

    override fun primitiveMethod(parameter_with_long__snake_case_name: Boolean): Char {
        val characters: List<Char> = ArrayList()
        return '0'
    }

    companion object {
        private const val PI = 3.141
    }
}