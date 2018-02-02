package Tool

import com.google.common.base.Preconditions
import io.reactivex.Observable
import kotlin.reflect.KCallable
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

/**
 * helper object
 */
object ReflectionUtil
{
    private val exception = RuntimeException("RXJava internal error")

    fun isProperty(it:Any)=it is KProperty<*>
    fun isFunction(it:Any)=it is KFunction<*>


    private fun<T:KCallable<*>> getByName(name: String, target: Any, resultConsumer: (Any?) -> Unit, filter: (KCallable<*>) -> Boolean, castClass: Class<T>, args:Any?=null) {
        val klass=target::class.java.kotlin

        Observable.fromIterable(klass.members)
                .filter { filter(it) }
                .cast(castClass)
                .filter { equal(it.name, name) }
                .toList()
                .subscribe({
                    //println(it.size)
                    Preconditions.checkState(it.size == 1, "internal error!method or field ${name} do not exist!")
                    val c=if(args==null) it[0].call(target) else  it[0].call(target,args)
                    resultConsumer(c)
                },{
                    throw exception
                })

    }

    /**
     * use reflection to call parsing method then add resulted constant to pool
     */
    fun callMethodByName(name: String, target: Any, resultConsumer:(Any?)->Unit,args:Any?=null) {
        getByName(name,target,resultConsumer,{ isFunction(it) },KFunction::class.java,args)

    }

    fun getPropertyByName(name: String, target: Any, resultConsumer:(Any?)->Unit) {
        getByName(name, target, resultConsumer, { isProperty(it) }, KProperty::class.java)
    }
}