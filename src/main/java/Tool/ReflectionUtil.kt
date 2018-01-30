package Tool

import Parser.ConstantPool.Constant
import Parser.ConstantPool.ConstantPool
import com.google.common.base.Preconditions
import io.reactivex.Observable
import java.util.function.BiConsumer
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

/**
 * helper object
 */
object ReflectionUtil
{
    fun isProperty(it:Any)=it is KProperty<*>
    fun isFunction(it:Any)=it is KFunction<*>


    /**
     * use reflection to call parsing method then add resulted constant to pool
     */
    fun callMethodByName(name: String, target: Any, resultConsumer:(Any?)->Unit) {
        val klass=target::class.java.kotlin
        Observable.fromIterable(klass.members)
                .filter { isFunction(it) }
                .cast(KFunction::class.java)
                .filter { equal(it.name, name) }
                .toList()
                .subscribe({
                    //println(it.size)
                    Preconditions.checkState(it.size == 1, "internal error!method ${name} do not exist!")
                    val c=it[0].call(target)
                    resultConsumer(c)
                },{
                    RuntimeException("rxjava internal error")
                })
    }
}