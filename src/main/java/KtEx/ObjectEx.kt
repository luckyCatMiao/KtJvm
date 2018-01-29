package KtEx

import kotlin.reflect.KProperty

inline fun Int.times(action:()->Unit){
    for(i in 0..this-1){
        action()
    }
}

fun <T>KProperty<T>.valueOfObject(o:Any)=this.call(o)

