package net.plaayzone.gaminganddrinks.Commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.plaayzone.gaminganddrinks.Main;

public class AnnoucementCommand extends Command{
	
	public AnnoucementCommand() {
		this.name= "anc";
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
		String text = "**Anunciamento do "+event.getMember().getUser().getName()+": **";
		for(String arg : args) {
			text+=arg +" ";
		}
		event.getMessage().delete().queue();
		for(Guild guild : Main.getJda().getGuilds()) {
			if(guild== event.getGuild())continue;
			event.reply(text);
		}
		event.reply(text);
	}

}
