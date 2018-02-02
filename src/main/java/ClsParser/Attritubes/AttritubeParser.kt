package ClsParser.Attritubes

import ClsParser.ConstantPool.ConstantPool
import ClsParser.ConstantPool.NumberConstant
import ClsParser.Fields.Field
import Tool.DataReader
import Tool.ReflectionUtil


class AttritubeParser(val reader: DataReader,
                      val constantPool: ConstantPool,
                      val field: Field) {

    fun parse(): List<Attritube> {

        val list = ArrayList<Attritube>()
        val attribute_name_index = reader.readUnsignedShort()
        val attribute_length = reader.readUnsignedInt()
        val name = constantPool.getUtf8Constant(attribute_name_index).value

        val methodName = "parse" + name
        ReflectionUtil.callMethodByName(methodName, this, { list.add(it as Attritube) }, name to attribute_length)

        return list
    }

    fun parseConstantValue(pair: Pair<Int, Long>): ConstantValueAttritube {

        val (name, length) = pair


        val constantvalue_index = reader.readUnsignedShort()
        val value = constantPool[constantvalue_index] as NumberConstant


        return ConstantValueAttritube(value)
    }


}