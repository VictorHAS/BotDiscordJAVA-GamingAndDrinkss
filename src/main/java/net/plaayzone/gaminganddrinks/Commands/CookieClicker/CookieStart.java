package net.plaayzone.gaminganddrinks.Commands.CookieClicker;

import java.io.File;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.managers.GuildController;
import net.plaayzone.Metodos;

public class CookieStart extends Command {

	public CookieStart() {
		this.name = "cookiestart";
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		File startcookie = new File("assets/cookie/startcokkie.png");
		String player = event.getMember().getUser().getName();
		GuildController gc = new GuildController(event.getGuild());
		Role cookieclicker = event.getGuild().getRolesByName("CookieGame", true).get(0);
		MessageChannel channel = event.getChannel().getJDA().getGuildById("427099848211431446")
				.getTextChannelById("427099848211431448");
		if (!(event.getChannel() == channel)) {
			event.reply("Use o canal <#427099848211431448> e o comando !cookiestart para começar a jogar!");
			return;
		}
		if (!Metodos.containsCookies(event.getMember().toString())&& !event.getMember().getRoles().contains(cookieclicker)) {
			Metodos.setPlayerCookie(player);
			if (!Metodos.containsProfile(event.getMember().toString())) {
				Metodos.setProfile(player);
			}
			gc.addSingleRoleToMember(event.getMember(), cookieclicker).queue();
			event.getChannel().sendFile(startcookie).append("`Você está agora participando do game de Cookie Clicker!`").queue();
		} else {
			return;
		}
	}

}
