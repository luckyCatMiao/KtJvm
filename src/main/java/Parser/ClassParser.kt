package Parser

import com.google.common.base.Preconditions
import com.google.common.base.Preconditions.*
import com.google.common.io.Files
import com.sun.corba.se.impl.activation.CommandHandler.parseError
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.File


object ClassParser{
    private lateinit var path: String

    fun parse(path: String) {
        checkArgument(File(path).exists(),"the file ${path} do not exist!")
        this.path=path;

        val javaclass=JavaClass()
        val reader=DataReader(path)
        parseClass(javaclass,reader)


    }

    /**
     * start parse
     */
    private fun parseClass(javaclass: JavaClass, reader: DataReader) {
        //magic
        val magic=reader.readUnsignedInt()
        checkState(ClassChecker.checkMagic(magic),parseError("magic"))
        //javaclass.magic=magic

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

    /**
     * return a description of error
     */
    private fun parseError(name:String)="parse error! reason:${name},class file:${path}"





}