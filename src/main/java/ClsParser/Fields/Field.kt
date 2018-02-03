package ClsParser.Fields

import ClsParser.Descriptor
import ClsParser.JavaClass
import KtJVM.FieldTypeInfo
import KtJVM.MemberType

/**
 * represent a field of class
 */
class Field( access_flags: Int,
             name_index: Int,
             descriptor_index: Int,
            javaclass: JavaClass):Descriptor(MemberType.Field,access_flags,name_index,descriptor_index,javaclass) {


    val type
        get() = {(typeInfo as FieldTypeInfo).type}


    init {





    }






}