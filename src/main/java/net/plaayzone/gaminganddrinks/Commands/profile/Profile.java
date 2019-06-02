package net.plaayzone.gaminganddrinks.Commands.profile;

import java.io.File;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.plaayzone.ImageManager;
import net.plaayzone.Metodos;

public class Profile extends Command {

	public Profile() {
		
		this.name = "profile";
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		Member mb = null;
		String[] args = event.getArgs().split(" ");
		if (args.length < 0) {
			mb = event.getMember();
		}
		if (event.getMessage().getMentionedUsers().isEmpty()) {
			try {
				mb = event.getMember();
			} catch (Exception e2) {
				mb = null;
			}
		} else
			mb = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));

		if (mb == null) {
			event.reply(event.getClient().getError() + " Não achei o player `" + event.getArgs() + "`!");
			return;
		}
		User user = mb.getUser();
		if (!Metodos.containsProfile(user.getId())) {
			Metodos.setProfile(user.getId());
		}
		// event.getMessage().delete().queue();
		// Width = largura , Height = altura
		
		ImageManager.drawProfile("assets/profile/profile3.png", "assets/profile/borda2.png","png",
				user.getEffectiveAvatarUrl(), user, event.getGuild());
		File Saida = new File("assets/profile/prof-" + user.getId() + ".png");
		event.getChannel().sendFile(Saida).queue();
		System.out.println("concluido!");
	}
//user.getName().replaceAll("[^a-zA-Z0-9]", "")
}
