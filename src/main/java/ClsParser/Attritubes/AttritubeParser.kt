package ClsParser.Attritubes

import ClsParser.ConstantPool.ConstantPool
import ClsParser.ConstantPool.NumberConstant
import KtEx.times
import Tool.DataReader
import Tool.ReflectionUtil


class AttritubeParser(val reader: DataReader,
                      val constantPool: ConstantPool) {

    fun parse(): List<Attritube> {

        val list = ArrayList<Attritube>()
        val attributesCount = reader.readUnsignedShort()
        attributesCount.times {
            parseAttribute(list)
        }
        return list
    }

    private fun parseAttribute(list: ArrayList<Attritube>) {
        val attribute_name_index = reader.readUnsignedShort()
        val attribute_length = reader.readUnsignedInt()
        val name = constantPool.getUtf8Constant(attribute_name_index).value

        val methodName = "parse" + name
        ReflectionUtil.callMethodByName(methodName, this, { list.add(it as Attritube) }, name to attribute_length)

    }

    fun parseConstantValue(pair: Pair<String, Long>): ConstantValueAttritube {

        val (name, length) = pair


        val constantvalue_index = reader.readUnsignedShort()
        val value = constantPool[constantvalue_index] as NumberConstant


        return ConstantValueAttritube(value)
    }


    fun parseCode(pair: Pair<String, Long>): CodeAttribute {

        val (name, length) = pair
        val max_stack = reader.readUnsignedShort()
        val max_locals=reader.readUnsignedShort()
        val code_length=reader.readUnsignedInt()
        val codes = reader.readFully(code_length.toInt())

        return CodeAttribute()
    }


}