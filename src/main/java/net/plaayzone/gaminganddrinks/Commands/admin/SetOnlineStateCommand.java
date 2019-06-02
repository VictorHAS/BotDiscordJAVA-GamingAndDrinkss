package net.plaayzone.gaminganddrinks.Commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.plaayzone.gaminganddrinks.Main;

public class SetOnlineStateCommand extends Command{
	
	public SetOnlineStateCommand() {
		this.name= "st";
		this.guildOnly=true;
	}
	@Override
	protected void execute(CommandEvent event) {
		if (event.getAuthor().isBot())
			return;
		
		if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			event.reply("<@" + event.getAuthor().getId() + "> apenas o dono do bot pode fazer isso!");
			return;
		}
		
		String[] args = event.getArgs().split(" ");
		try {
		if(args[0].equals("dnd")) {
			Main.getJda().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
		}else {
			Main.getJda().getPresence().setStatus(OnlineStatus.valueOf(args[0].toUpperCase()));
		}
		event.reply(" **Status updated para "+args[0]+"**");
		}catch(IllegalArgumentException e) {
			event.reply("Status incorreto!");
		}
		
	}

}
