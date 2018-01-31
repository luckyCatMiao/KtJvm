package ClsParser.Attritubes

import ClsParser.ConstantPool.ConstantPool
import ClsParser.ConstantPool.Utf8Constant
import Tool.DataReader

class AttritubeParser(val reader: DataReader, val constantPool: ConstantPool) {
    fun parse():List<Attritube> {

        val list=ArrayList<Attritube>()
        val attribute_name_index=reader.readUnsignedShort()
        val name= (constantPool[attribute_name_index] as Utf8Constant).value


        when(name){
            "ConstantValue"->parseConstantValue()
            else->throw RuntimeException("")
        }

        println(name)
       // val attribute_length=reader.readUnsignedInt()




        return list
    }

    private fun parseConstantValue() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}