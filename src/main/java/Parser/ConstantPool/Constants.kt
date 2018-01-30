package Parser.ConstantPool

open class Constant(val tag:Int)



/**
 * number
 */
open class NumberConstant(tag:Int):Constant(tag)
data class IntegerConstant(val value:Int):NumberConstant(ConstantTag.CONSTANT_Integer);
data class FloatConstant(val value:Float):NumberConstant(ConstantTag.CONSTANT_Float);
data class LongConstant(val value:Long):NumberConstant(ConstantTag.CONSTANT_Long);
data class DoubleConstant(val value:Double):NumberConstant(ConstantTag.CONSTANT_Double);

/**
 * string
 */
open class BaseStringConstant(tag:Int):Constant(tag)
data class UTF8Constant(val value:String):BaseStringConstant(ConstantTag.CONSTANT_Utf8);
data class StringConstant(val index:Int):BaseStringConstant(ConstantTag.CONSTANT_String);


/**
 *
 */
data class ClassConstant(val index:Int):Constant(ConstantTag.CONSTANT_Class);
data class NameAndTypeConstant(val name_index:Int,val descriptor_index:Int):Constant(ConstantTag.CONSTANT_NameAndType);
data class FieldrefConstant(val class_index:Int,val name_and_type_index:Int):Constant(ConstantTag.CONSTANT_Fieldref);
data class MethodrefConstant(val class_index:Int,val name_and_type_index:Int):Constant(ConstantTag.CONSTANT_Methodref);
data class InterfaceMethodrefConstant(val class_index:Int,val name_and_type_index:Int):Constant(ConstantTag.CONSTANT_InterfaceMethodref);



