package Main

import Parser.ClassParser

object Entry {



    @JvmStatic
    fun main(args: Array<String>) {

//        Preconditions.checkArgument(args.size>0,"error params,the jvm will exit now!")
//
//        Observable.just(args[0])
//                .map { CommandParser.parseCommand(args) }
//                .subscribe{command->command.run()}


        ClassParser().parse("test.class")


    }

    private fun startJVM(args: Array<String>) {

    }


}