package ClsParser.Attritubes

import ClsParser.ConstantPool.ConstantPool
import ClsParser.Fields.Field
import Tool.DataReader
import Tool.ReflectionUtil


class AttritubeParser(val reader: DataReader,
                      val constantPool: ConstantPool,
                      val field: Field) {

    fun parse():List<Attritube> {

        val list=ArrayList<Attritube>()
        val attribute_name_index=reader.readUnsignedShort()
        val attribute_length=reader.readUnsignedInt()
        val name= (constantPool.getUtf8Constant(attribute_name_index)).value

//        val methodName="parse"+name
//        ReflectionUtil.callMethodByName(methodName,this,{ println(it)}, listOf(name,attribute_length))

//        when(name){
//            "ConstantValue"->parseConstantValue(name,attribute_length)
//            else->throw RuntimeException("")
//        }

       // println(name)




        return list
    }

    private fun parseConstantValue(args:List<Any>) {

        println(args)

        val constantvalue_index=reader.readUnsignedShort()
        val value=constantPool[constantvalue_index]


        //println(value)
    }


}