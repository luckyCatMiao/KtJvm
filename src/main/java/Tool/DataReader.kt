package Tool


import com.google.common.base.Preconditions
import com.google.common.base.Preconditions.*
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream

/**
 *
 */
class DataReader(path: String):DataInputStream(BufferedInputStream(FileInputStream(File(path)))){
    private val LongMask = 0xffffffff

    fun readUnsignedInt()=readInt().toLong() and LongMask

    fun readFully(size:Int):ByteArray{
        checkArgument(size>0,"size must>0!")
        val arr=ByteArray(size)
        readFully(arr)
        return arr
//        var size=size
//        val arr=ByteArray(size)
//        while(size>0){
//            val hasRead=read(arr)
//            if(hasRead<0){
//                throw RuntimeException("read Error!")
//            }
//            size-=hasRead
//        }
//        return arr

    }
}