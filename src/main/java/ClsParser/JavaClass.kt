package ClsParser

import ClsParser.ConstantPool.ConstantPool
import ClsParser.Fields.FieldManager

class JavaClass()
{
    var magic: Long=0
    var minorVersion: Int=0
    var majorVersion: Int=0
    lateinit var constantPool: ConstantPool
    var accessFlags: Int=0
    var classNameIndex: Int=0
    var superClassNameIndex: Int=0
    var interfaceIndexs= ArrayList<Int>()
    var fieldManager=FieldManager()
    override fun toString(): String {
        return "JavaClass(magic=$magic, minorVersion=$minorVersion, majorVersion=$majorVersion, constantPool=$constantPool, accessFlags=$accessFlags, classNameIndex=$classNameIndex, superClassNameIndex=$superClassNameIndex)"
    }


}