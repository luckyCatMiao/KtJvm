package ClsParser

import ClsParser.Attritubes.AttritubesManager
import ClsParser.ConstantPool.Utf8Constant
import KtJVM.FieldTypeInfo
import KtJVM.MemberType
import KtJVM.TypeInfo
import KtEx.toType
import KtJVM.RuntimeType


/**
 * the base class for all number of class which have descriptors
 */
abstract class Descriptor(val memberType: MemberType,
                          val access_flags: Int,
                          val name_index: Int,
                          val descriptor_index: Int,
                          javaclass: JavaClass) {

    companion object {
        private val runtimeType = listOf(
                Byte::class.toType(),
                Short::class.toType(),
                Char::class.toType(),
                Int::class.toType(),
                Long::class.toType(),
                Float::class.toType(),
                Double::class.toType(),
                Boolean::class.toType()
        )
    }

    /**
     * the name of the field
     */
    var name:String
        private set

    /**
     * attrs
     */
    val attrMan: AttritubesManager = AttritubesManager()

    lateinit var typeInfo: TypeInfo
        private set

    init {
        val name=javaclass.getUtf8Constant(name_index).value
        val des=javaclass.getUtf8Constant(descriptor_index)
        this.name=name
        when(memberType)
        {
            MemberType.Field-> parseFieldDescriptor(des)
            MemberType.Method->parseMethodDescriptor(des)
            MemberType.Class->parseClassDescriptor(des)
        }


    }

     fun parseClassDescriptor(des: Utf8Constant){

     }

    fun parseMethodDescriptor(des: Utf8Constant){

     }

    val f_reg1 = """[BSCIJFDZ]""".toRegex()
    val f_reg2 = """L.+?;""".toRegex()
    val f_reg3 = """\[+[BSCIJFDZ]""".toRegex()
    val f_reg4 = """\[+L.+?;""".toRegex()

    fun parseFieldDescriptor(des: Utf8Constant) {
        val v = des.value

        var match = f_reg1.matchEntire(v)
        if (match != null) {
            parseFieldType(match.value)
            return
        }

        match = f_reg2.matchEntire(v)
        if (match != null) {
            //parseObjectType(match)
            return
        }

        match = f_reg3.matchEntire(v)
        if (match != null) {
            return
        }

        match = f_reg4.matchEntire(v)
        if (match != null) {
            return
        }


    }

    private fun parseFieldType(value: String) {
        fun getMemberByName(value: String): RuntimeType<out Any> {
            return runtimeType
                    .filter {
                        it.simpleName!!
                                .toUpperCase()
                                .startsWith(value)
                    }[0]
        }

        //基本类型 byte 、 short 、 char 、 int 、 long 、 float 和 double 的描述符是单个字母，分别对应 B 、 S 、 C 、 I 、
        //J 、 F 和 D 。注意， long 的描述符是 J 而不是 L 。

        val type = when (value) {
            "J" -> Long::class.toType()
            "Z" -> Boolean::class.toType()
            "B", "S", "C", "I", "F", "D" -> getMemberByName(value)
            else -> throw RuntimeException("fieldType parse error! the value:$value can't be parsed!")
        }



        this.typeInfo = FieldTypeInfo(type)

    }
}