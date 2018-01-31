package ClsParser.Attritubes

import ClsParser.ConstantPool.ConstantPool
import ClsParser.ConstantPool.Utf8Constant
import ClsParser.Fields.Field
import Tool.DataReader


class AttritubeParser(val reader: DataReader,
                      val constantPool: ConstantPool,
                      val field: Field) {

    fun parse():List<Attritube> {

        val list=ArrayList<Attritube>()
        val attribute_name_index=reader.readUnsignedShort()
        val attribute_length=reader.readUnsignedInt()
        val name= (constantPool[attribute_name_index] as Utf8Constant).value


        when(name){
            "ConstantValue"->parseConstantValue(name,attribute_length)
            else->throw RuntimeException("")
        }

        println(name)




        return list
    }

    private fun parseConstantValue(name: String, attribute_length: Long) {

        val constantvalue_index=reader.readUnsignedShort()
        val value=constantPool[constantvalue_index]


        println(value)
    }


}