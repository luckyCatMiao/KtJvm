package Tool

import Command.BaseCommand
import Command.HelpCommand
import Command.RunCommand
import Command.VersionCommand



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