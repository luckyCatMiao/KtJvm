package KtEx

inline fun Int.times(action:()->Unit){
    for(i in 0..this-1){
        action()
    }
}