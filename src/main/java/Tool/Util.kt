package Tool

import KtJVM.CmdCommand.BaseCommand
import KtJVM.CmdCommand.HelpCommand
import KtJVM.CmdCommand.RunCommand
import KtJVM.CmdCommand.VersionCommand

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