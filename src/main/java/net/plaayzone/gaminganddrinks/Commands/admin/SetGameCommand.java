package net.plaayzone.gaminganddrinks.Commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.plaayzone.gaminganddrinks.Main;

public class SetGameCommand extends Command{

	public SetGameCommand() {
		this.name="sg";
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
		String text = new String();
		String[] args = event.getArgs().split(" ");
		for(String arg : args) {
			text +=arg+" ";
		}
		Main.getJda().getPresence().setGame(Game.of(GameType.DEFAULT, text));
	}

}
