package Parser.ConstantPool

open class Constant(var tag:Int){
    init {
        //use reflection to set tag
        val klass = this.javaClass.kotlin
        val fieldName="CONSTANT_"+klass.simpleName?.replace("Constant","")
        println(fieldName)
        //this.tag=ReflectionUtil.callMethodByName()

    }
}



/**
 * number
 */
open class NumberConstant(tag:Int):Constant(tag)
data class IntegerConstant(val value:Int):NumberConstant(ConstantType.CONSTANT_Integer);
data class FloatConstant(val value:Float):NumberConstant(ConstantType.CONSTANT_Float);
data class LongConstant(val value:Long):NumberConstant(ConstantType.CONSTANT_Long);
data class DoubleConstant(val value:Double):NumberConstant(ConstantType.CONSTANT_Double);

/**
 * string
 */
open class BaseStringConstant(tag:Int):Constant(tag)
data class UTF8Constant(val value:String):BaseStringConstant(ConstantType.CONSTANT_Utf8);
data class StringConstant(val index:Int):BaseStringConstant(ConstantType.CONSTANT_String);


/**
 *
 */
data class ClassConstant(val index:Int):Constant(ConstantType.CONSTANT_Class);
data class NameAndTypeConstant(val name_index:Int,val descriptor_index:Int):Constant(ConstantType.CONSTANT_NameAndType);
data class FieldrefConstant(val class_index:Int,val name_and_type_index:Int):Constant(ConstantType.CONSTANT_Fieldref);
data class MethodrefConstant(val class_index:Int,val name_and_type_index:Int):Constant(ConstantType.CONSTANT_Methodref);
data class InterfaceMethodrefConstant(val class_index:Int,val name_and_type_index:Int):Constant(ConstantType.CONSTANT_InterfaceMethodref);



