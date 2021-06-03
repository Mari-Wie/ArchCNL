package example

class ClassWithInnerClass protected constructor(parameter1: Int, parameter2: Float) {
    private val field1: InnerClass
    private val field2: Float
    fun method(): Double {
        return (field1.get() * field2).toDouble()
    }

    private class InnerClass(private val innerField: Int) {
        fun get(): Int {
            return innerField
        }

        private class InsideClass(private val insideField: Int) {
            fun get(): Int {
                return insideField
            }
        }
    }

    init {
        field1 = InnerClass(parameter1)
        field2 = parameter2
    }
}