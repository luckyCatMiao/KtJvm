package Command

import KtJVM.Jvm

open class BaseCommand(args: Array<String>) {
    open fun run() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class HelpCommand(args: Array<String>) :BaseCommand(args){
    override fun run() {
        println("hello,it's a simple jvm and feel free to use it!")
    }

}

class VersionCommand(args: Array<String>) :BaseCommand(args){
    override fun run() {
        println("current version is 1.0 that last updated at 2018.1.19")
    }

}

class RunCommand(args: Array<String>) :BaseCommand(args){
    override fun run() {
        Jvm.start()
    }

}