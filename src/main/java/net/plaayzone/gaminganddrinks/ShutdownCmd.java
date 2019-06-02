package net.plaayzone.gaminganddrinks;

import java.io.IOException;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;

public class ShutdownCmd extends Command {

	public ShutdownCmd() {
		this.name = "sd";
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			event.reply("<@" + event.getAuthor().getId() + "> apenas o dono do bot pode fazer isso!");
			return;
		}
		event.reply("**Salvando dados...**");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		event.reply("**Auto desligando...**");

		try {
			Thread.sleep(2000);
			Main.shutdown();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
