package KtJVM.Code

import ClsParser.Attritubes.AttritubeParser
import ClsParser.ConstantPool.ConstantPool
import Tool.DataReader

class CodeParser(private val reader: DataReader,
                 private var code_length: Long,
                 private val constantPool: ConstantPool) {

    fun parse():CodeManager {
       val codeManager=CodeManager()


        while(code_length>0)
        {
            val code = reader.readUnsignedByte()
            val c=when(code){
                0x03,0x09,0x0b,0x0e->Code_Const(0)
                0x04,0x0a,0x0c,0x0f->Code_Const(1)
                0x05,0x0d->Code_Const(2)
                0x06->Code_Const(3)
                0x07->Code_Const(4)
                0x08->Code_Const(5)
                0x1A,0x1E,0x22,0x26,0x2A->Code_Load(0)
                0x1b,0x1f,0x23,0x27,0x2b->Code_Load(1)
                0x1c,0x20,0x28,0x24,0x2c->Code_Load(2)
                0x1d,0x21,0x25,0x29,0x2d->Code_Load(3)
                0x3b,0x3f,0x43,0x47,0x4b->Code_Store(0)
                0x3c,0x40,0x44,0x48,0x4c->Code_Store(1)
                0x3d,0x41,0x45,0x49,0x4d->Code_Store(2)
                0x3e,0x42,0x46,0x4a,0x4e->Code_Store(3)
                0xb7->Code_InvokeSpecial(reader.readUnsignedShort())
                0xb1->Code_Return()
                else -> throw RuntimeException("parse code error! code:$code")
            }

            code_length-=c.byteLength
            codeManager.add(c)
        }

        val exception_table_length=reader.readUnsignedShort()
        val attrs = AttritubeParser(reader, constantPool).parse()
        codeManager.attrMan.addAll(attrs)

        return codeManager
    }


}