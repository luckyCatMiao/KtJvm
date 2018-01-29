package Parser.ConstantPool

import KtEx.valueOfObject
import Parser.DataReader
import Parser.JavaClass
import com.google.common.base.Preconditions.checkState
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
                .filter { Helper.isProperty(it)}
                .cast(KProperty::class.java)
                .filter { Helper.equal(it.valueOfObject(ConstantTag),tag) }
                .map { Helper.toMethodName(it) }
                .subscribe{
                    //call functions by names
                    Helper.callMethodByName(it,this)
                }


    }



     fun parseMethodref(){
        println("6666")
    }

    /**
     * internal helper object
     */
    object Helper
    {
        fun isProperty(it:Any)=it is KProperty<*>
        fun isFunction(it:Any)=it is KFunction<*>
        fun equal(a:Any?,b:Any?):Boolean{
             if(a==null||b==null)
            {
                return false
            }else{
                return a.equals(b)
            }

        }

        fun toMethodName(it: KProperty<*>)="parse"+it.name.replace("CONSTANT_","")


        fun callMethodByName(name: String,target:Any) {
            val klass=target::class.java.kotlin
            Observable.fromIterable(klass.members)
                    .filter { isFunction(it)}
                    .cast(KFunction::class.java)
                    .filter { equal(it.name,name) }
                    .toList()
                    .subscribe({
                        println(it.size)
                        checkState(it.size==1,"internal error!method ${name} do not exist!")
                        it[0].call(target)
                    },{
                        RuntimeException("rxjava error")
                    })
        }
    }

}