package ClsParser.Fields

import ClsParser.Descriptor
import ClsParser.JavaClass
import ClsParser.MemberType

/**
 * represent a field of class
 */
class Field(val access_flags: Int,
            val name_index: Int,
            val descriptor_index: Int,
            val javaclass: JavaClass):Descriptor(MemberType.Field) {

    /**
     * the name of the field
     */
    var name:String
    private set



    init {

        val name=javaclass.getUtf8Constant(name_index).value
        val des=javaclass.getUtf8Constant(descriptor_index)
        this.name=name
        parseFieldDescriptor(des)

    }




}