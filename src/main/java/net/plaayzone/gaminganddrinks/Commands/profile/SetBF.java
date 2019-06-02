package net.plaayzone.gaminganddrinks.Commands.profile;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Member;
import net.plaayzone.Metodos;

public class SetBF extends Command {

	public SetBF() {
		this.name = "setbf";
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		Member mb = null;
		if (event.getMessage().getMentionedUsers().isEmpty()) {
			event.reply("Mencione alguem!");
			return;
		} else
			mb = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));

		if (mb == null) {
			event.reply(event.getClient().getError() + " Não achei o player `" + event.getArgs() + "`!");
			return;
		}
		String player = event.getMember().getUser().getId();
		if (!Metodos.containsProfile(player)) {
			Metodos.setProfile(player);
		} else {
			Metodos.setBF(player, mb.getUser().getId());
			event.reply("Seu novo melhor amigo é: " + mb.getUser().getName());
		}
	}
	
}
