package net.plaayzone.gaminganddrinks.Commands.admin;

import java.io.IOException;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.managers.GuildController;
import net.plaayzone.Metodos;
import net.plaayzone.gaminganddrinks.Main;

public class DropTable extends Command{
	
	public DropTable() {
		this.name="droptables";
		this.guildOnly = true;
	}
	@Override
	protected void execute(CommandEvent event) {
		if (event.getAuthor().isBot())
			return;

		if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			event.reply("<@" + event.getAuthor().getId() + "> apenas o dono do bot pode fazer isso!");
			return;
		}
		GuildController gc = new GuildController(event.getGuild());
		List<Member> mm = event.getGuild().getMembers();
		Role cookiegame = event.getGuild().getRolesByName("CookieGame", true).get(0);
		for(Member membros : mm) {
			gc.removeSingleRoleFromMember(membros, cookiegame).queue();
		}
		Metodos.dropTable();
		event.reply("**Tabelas limpas, roles removidas, conexão com mysql fechada! reinicie o bot para continuar.**");
		try {
			Main.shutdown();
		} catch (IOException e) {
			event.reply(event.getClient().getError()+ " Erro ao desligar bot!");
		}
	}

}
