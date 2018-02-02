package Tool

import KtJVM.Command.BaseCommand
import KtJVM.Command.HelpCommand
import KtJVM.Command.RunCommand
import KtJVM.Command.VersionCommand

fun equal(a:Any?,b:Any?):Boolean{
    if(a==null||b==null)
    {
        return false
    }else{
        return a.equals(b)
    }

}

object CommandParser
{
    fun parseCommand(args: Array<String>): BaseCommand {
        //parse command line
       return when(args[0])
        {
            "help"-> HelpCommand(args)
            "version"->VersionCommand(args)
            "run"->RunCommand(args)
            else ->throw RuntimeException("parse command error!")
       }

    }

}