package KtJVM.Code

import Tool.DataReader

class CodeParser(private val reader: DataReader,
                 private var code_length: Long) {

    fun parse():List<Code> {
       val list=ArrayList<Code>()

        println(reader.readUnsignedByte())

        return list
    }

}