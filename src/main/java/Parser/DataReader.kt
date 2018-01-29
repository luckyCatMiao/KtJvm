package Parser


import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream

class DataReader(path: String):DataInputStream(FileInputStream(File(path))){
    private val LongMask = 0xffffffff

    fun readUnsignedInt()=readInt().toLong() and LongMask

}