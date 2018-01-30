package Parser.ConstantPool

import Tool.ReflectionUtil

open class Constant(){
    var tag:Int=0
    init {
        //use reflection to set tag
        val klass = this.javaClass.kotlin
        val fieldName="CONSTANT_"+klass.simpleName?.replace("Constant","")
        ReflectionUtil.getPropertyByName(fieldName,ConstantType,{ this.tag=it as Int})

    }
}



/**
 * number
 */
open class NumberConstant():Constant()
data class IntegerConstant(val value:Int):NumberConstant();
data class FloatConstant(val value:Float):NumberConstant();
data class LongConstant(val value:Long):NumberConstant();
data class DoubleConstant(val value:Double):NumberConstant();

/**
 * string
 */
open class BaseStringConstant():Constant()
data class UTF8Constant(val value:String):BaseStringConstant();
data class StringConstant(val index:Int):BaseStringConstant();


/**
 *
 */
data class ClassConstant(val index:Int):Constant();
data class NameAndTypeConstant(val name_index:Int,val descriptor_index:Int):Constant();
data class FieldrefConstant(val class_index:Int,val name_and_type_index:Int):Constant();
data class MethodrefConstant(val class_index:Int,val name_and_type_index:Int):Constant();
data class InterfaceMethodrefConstant(val class_index:Int,val name_and_type_index:Int):Constant();



