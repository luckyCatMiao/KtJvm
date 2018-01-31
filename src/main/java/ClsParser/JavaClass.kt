package ClsParser

import ClsParser.ConstantPool.Constant
import ClsParser.ConstantPool.ConstantPool
import ClsParser.ConstantPool.IConstantPool
import ClsParser.Fields.FieldManager
import ClsParser.Fields.IFieldManager
import ClsParser.Methods.IMethodManager
import ClsParser.Methods.MethodManager

class JavaClass(
        var magic: Long = 0,
        var minorVersion: Int = 0,
        var majorVersion: Int = 0,
        val constantPool: ConstantPool = ConstantPool(),
        var accessFlags: Int = 0,
        var classNameIndex: Int = 0,
        var superClassNameIndex: Int = 0,
        var interfaceIndexs: ArrayList<Int> = ArrayList<Int>(),
        val fieldManager: FieldManager = FieldManager(),
        val methodMethodManager:MethodManager= MethodManager()

) :     IConstantPool by constantPool,
        IFieldManager by fieldManager,
        IMethodManager by methodMethodManager
{

    override fun toString(): String {
        return "JavaClass(magic=$magic, minorVersion=$minorVersion, majorVersion=$majorVersion, constantPool=$constantPool, accessFlags=$accessFlags, classNameIndex=$classNameIndex, superClassNameIndex=$superClassNameIndex)"
    }


}