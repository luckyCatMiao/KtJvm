package ClsParser.ConstantPool

interface IConstantPool {
    fun getBaseStringConstant(index: Int): BaseStringConstant
    fun getUtf8Constant(index: Int): Utf8Constant
    fun getStringConstant(index: Int): StringConstant
    fun getClassConstant(index: Int): ClassConstant
    fun getNameAndTypeConstant(index: Int): NameAndTypeConstant
    fun getFieldrefConstant(index: Int): FieldrefConstant
    fun getMethodrefConstant(index: Int): MethodrefConstant
    fun getInterfaceMethodrefConstant(index: Int): InterfaceMethodrefConstant
    fun getNumberConstant(index: Int): NumberConstant
    fun getIntegerConstant(index: Int): IntegerConstant
    fun getFloatConstant(index: Int): FloatConstant
    fun getLongConstant(index: Int): LongConstant
    fun getDoubleConstant(index: Int): DoubleConstant
    fun getRawConstant(index: Int): Constant
}

class ConstantPool(
        private val innerMap: MutableMap<Int, Constant> = HashMap<Int, Constant>()
) :     MutableMap<Int, Constant> by innerMap,
        IConstantPool,
        Iterable<Constant> by innerMap.values {


    override fun toString(): String {

        return "ConstantPool(constants=$innerMap)"
    }


    override fun getBaseStringConstant(index: Int): BaseStringConstant {
        return getConstant<BaseStringConstant>(index)
    }

    override fun getUtf8Constant(index: Int): Utf8Constant {
        return getConstant<Utf8Constant>(index)
    }

    override fun getStringConstant(index: Int): StringConstant {
        return getConstant<StringConstant>(index)
    }

    override fun getClassConstant(index: Int): ClassConstant {
        return getConstant<ClassConstant>(index)
    }

    override fun getNameAndTypeConstant(index: Int): NameAndTypeConstant {
        return getConstant<NameAndTypeConstant>(index)
    }

    override fun getFieldrefConstant(index: Int): FieldrefConstant {
        return getConstant<FieldrefConstant>(index)
    }

    override fun getMethodrefConstant(index: Int): MethodrefConstant {
        return getConstant<MethodrefConstant>(index)
    }

    override fun getInterfaceMethodrefConstant(index: Int): InterfaceMethodrefConstant {
        return getConstant<InterfaceMethodrefConstant>(index)
    }

    override fun getNumberConstant(index: Int): NumberConstant {
        return getConstant<NumberConstant>(index)
    }

    override fun getIntegerConstant(index: Int): IntegerConstant {
        return getConstant<IntegerConstant>(index)
    }

    override fun getFloatConstant(index: Int): FloatConstant {
        return getConstant<FloatConstant>(index)
    }

    override fun getLongConstant(index: Int): LongConstant {
        return getConstant<LongConstant>(index)
    }

    override fun getDoubleConstant(index: Int): DoubleConstant {
        return getConstant<DoubleConstant>(index)
    }

    override fun getRawConstant(index: Int): Constant {
        val value = get(index)
        if (value == null) {
            throw RuntimeException("\"constant pool error! index:${index} not found!\"")
        } else {
            return value

        }

    }

    private fun <E : Constant> getConstant(index: Int): E {


        val value = get(index)

        if (value == null) {
            throw RuntimeException("\"constant pool error! index:${index} not found!\"")
        } else {
            try {
                return value as E
            } catch (e: TypeCastException) {
                throw RuntimeException("\"constant pool error! index:${index} can not be cast!\"")
            }

        }


    }


}