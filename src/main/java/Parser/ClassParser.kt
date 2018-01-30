package Parser

import Parser.ConstantPool.ConstantPoolParser
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

        println(javaclass)


    }

    private fun parseSuperClassName() {
        val index= reader.readUnsignedShort()
        val check=ClassChecker.checkIndexConstantPoolSingle(javaclass.constantPool,index)
        checkState(check, parseError("superClassName"))
        javaclass.superClassNameIndex = index
    }

    private fun parseClassName() {
        val index= reader.readUnsignedShort()
        val check=ClassChecker.checkIndexConstantPoolSingle(javaclass.constantPool,index)
        checkState(check, parseError("className"))
        javaclass.classNameIndex = index
    }

    private fun parseAccessFlags() {
        val flag= reader.readUnsignedShort()
        val check=ClassChecker.checkFlag(flag)
        checkState(check, parseError("access Flag"))
        javaclass.accessFlags = flag
    }

    private fun parseConstantPool() {
        javaclass.constantPool=ConstantPoolParser(javaclass,reader).parse()
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