package ClsParser.Fields

import ClsParser.Attritubes.AttritubesManager
import ClsParser.Descriptor
import ClsParser.JavaClass

/**
 * represent a field of class
 */
class Field(val access_flags: Int,
            val name_index: Int,
            val descriptor_index: Int,
            val javaclass: JavaClass):Descriptor() {

    /**
     * the name of the field
     */
    var name:String
    private set



    init {

        val name=javaclass.getUtf8Constant(name_index).value
        val des=javaclass.getUtf8Constant(descriptor_index)
        this.name=name
        parseDescriptor(des)

    }

    /**
     * attrs of the field
     */
    val attrMan: AttritubesManager = AttritubesManager()


}