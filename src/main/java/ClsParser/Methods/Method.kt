package ClsParser.Methods

import ClsParser.Attritubes.AttritubesManager
import ClsParser.ConstantPool.Utf8Constant
import ClsParser.Descriptor
import ClsParser.JavaClass
import KtJVM.MemberType

/**
 * represent a field of class
 */
class Method( access_flags: Int,
              name_index: Int,
              descriptor_index: Int,
              javaclass: JavaClass): Descriptor(MemberType.Method,access_flags,name_index,descriptor_index,javaclass)  {


}