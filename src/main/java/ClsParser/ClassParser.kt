package ClsParser

import ClsParser.Attritubes.AttritubeParser
import ClsParser.ConstantPool.ConstantPoolParser
import ClsParser.Fields.Field
import KtEx.times
import Tool.DataReader
import com.google.common.base.Preconditions.checkArgument
import com.google.common.base.Preconditions.checkState
import java.io.File


class ClassParser{
    private lateinit var path: String
    private lateinit var javaclass: JavaClass
    private lateinit var reader: DataReader

    fun parse(path: String) {
        checkArgument(File(path).exists(),"the file ${path} do not exist!")
        this.path=path;
        this.javaclass=JavaClass()
        this.reader= DataReader(path)
        parseClass()


    }

    /**
     * start parse
     */
    private fun parseClass() {

        parseMagic()
        parseVersion()
        parseConstantPool()
        parseAccessFlags()
        parseClassName()
        parseSuperClassName()
        parseInterfaceNames()
        parseFields()

        println(javaclass)


    }

    private fun parseFields() {
        val count=reader.readUnsignedShort()
        parseField()
    }

    private fun parseField() {

        val access_flags=reader.readUnsignedShort()
        val name_index=reader.readUnsignedShort()
        val descriptor_index=reader.readUnsignedShort()

        val field= Field(access_flags,name_index,descriptor_index,javaclass)

        val attributesCount=reader.readUnsignedShort()
        val attrs = AttritubeParser(reader,javaclass.constantPool,field).parse()
        field.attrMan.addAll(attrs)

    }

    private fun parseInterfaceNames() {
        val interfaceCount= reader.readUnsignedShort()
        interfaceCount.times {
            val nameIndex=reader.readUnsignedShort()
            val check=ClassChecker.checkIndexConstantPoolSingle(javaclass.constantPool,nameIndex)
            checkState(check, parseError("interfaceNameIndex"))
            javaclass.interfaceIndexs.add(nameIndex)
        }



    }

    private fun parseSuperClassName() {
        val index= reader.readUnsignedShort()
        val check=ClassChecker.checkIndexConstantPoolSingle(javaclass.constantPool,index)
        checkState(check, parseError("superClassNameIndex"))
        javaclass.superClassNameIndex = index
    }

    private fun parseClassName() {
        val index= reader.readUnsignedShort()
        val check=ClassChecker.checkIndexConstantPoolSingle(javaclass.constantPool,index)
        checkState(check, parseError("classNameIndex"))
        javaclass.classNameIndex = index
    }

    private fun parseAccessFlags() {
        val flag= reader.readUnsignedShort()
        val check=ClassChecker.checkFlag(flag)
        checkState(check, parseError("access Flag"))
        javaclass.accessFlags = flag
    }

    private fun parseConstantPool() {
        val map=ConstantPoolParser(javaclass,reader).parse()
        javaclass.constantPool.putAll(map)
        ClassChecker.checkIndexConstantPool( javaclass.constantPool)
    }

    private fun parseVersion(){
        val minorVersion= reader.readUnsignedShort()
        val majorVersion=reader.readUnsignedShort()
        val check=ClassChecker.checkVersion(minorVersion,majorVersion)
        checkState(check, parseError("version"))
        javaclass.minorVersion = minorVersion
        javaclass.majorVersion = majorVersion

    }

    private fun parseMagic() {
        val magic = reader.readUnsignedInt()
        val check=ClassChecker.checkMagic(magic)
        checkState(check, parseError("magic"))
        javaclass.magic = magic
    }

    /**
     * return a description of error
     */
    protected fun parseError(name:String)="parse error! reason:${name},class file:${path}"





}