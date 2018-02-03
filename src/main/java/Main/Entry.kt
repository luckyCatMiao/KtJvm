package Main

import ClsParser.ClassParser

object Entry {



    @JvmStatic
    fun main(args: Array<String>) {

//        Preconditions.checkArgument(args.size>0,"error params,the jvm will exit now!")
//
//        Observable.just(args[0])
//                .map { CommandParser.parseCommand(args) }
//                .subscribe{command->command.run()}


        ClassParser().parse("TestClass2.class")


    }

    private fun startJVM(args: Array<String>) {

    }


}