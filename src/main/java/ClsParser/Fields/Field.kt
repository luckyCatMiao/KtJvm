package ClsParser.Fields

import ClsParser.Attritubes.AttritubesManager
import ClsParser.ConstantPool.Utf8Constant
import ClsParser.JavaClass

/**
 * represent a field of class
 */
class Field(val access_flags: Int,
            val name_index: Int,
            val descriptor_index: Int,
            val javaclass: JavaClass) {

    var name:String
    private set


    init {
        val name=(javaclass.constantPool[name_index] as Utf8Constant).value
        this.name=name
        println(name)

    }

    /**
     * attrs of the field
     */
    val attrMan: AttritubesManager = AttritubesManager()


}