package Parser

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
        this.reader=DataReader(path)
        parseClass()


    }

    /**
     * start parse
     */
    private fun parseClass() {

        parseMagic()
        parseVersion()

        println(javaclass)

//        val bytes = Files.asByteSource(file).read()
////        Observable.fromIterable(bytes.toList())
////                .map {  parseUnsigned(it) }
////                .map { Integer.toHexString(it) }
////                .toList()
////                .subscribe{s -> System.out.println(s)}
//
//
//        val stream=DataInputStream(ByteArrayInputStream(bytes))
//        stream.readInt();
//        //println(bytes)
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
    private fun parseError(name:String)="parse error! reason:${name},class file:${path}"





}