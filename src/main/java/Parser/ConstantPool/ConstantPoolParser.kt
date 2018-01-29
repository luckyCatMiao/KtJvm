package Parser.ConstantPool

import KtEx.times
import Parser.DataReader
import Parser.JavaClass
import com.google.common.base.Preconditions
import com.google.common.base.Preconditions.*
import io.reactivex.Observable
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty


class ConstantPoolParser(val javaclass:JavaClass,val reader:DataReader) {
    fun parse() {
        val count=reader.readUnsignedShort()
//        count.times {
//            parseConstant()
//        }
        parseConstant()
    }

    private fun parseConstant() {
        val tag=reader.readUnsignedByte()
        //use reflection to call function

        val klass=ConstantTag::class.java.kotlin
        Observable.fromIterable(klass.members)
                .filter { it is  KProperty}
                .filter { it.call(ConstantTag)==tag }
                .map { "parse"+it.name.replace("CONSTANT_","") }
                .subscribe{
                    //call functions by names
                    callByName(it)
                }


    }

    private fun callByName(name: String) {
        val klass=this::class.java.kotlin
        Observable.fromIterable(klass.members)
                .filter { it is KFunction}
                .filter { it.name.equals(name) }
                .toList()
                .subscribe({
                    println(it.size)
                    checkState(it.size==1,"internal error!method ${name} do not exist!")
                    it[0].call(this)
                },{
                    RuntimeException("rxjava error")
                })
    }

     fun parseMethodref(){
        println("6666")
    }
}