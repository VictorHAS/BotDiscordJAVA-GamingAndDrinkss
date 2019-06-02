package net.plaayzone.gaminganddrinks.Commands.admin;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Guild.VerificationLevel;
import net.dv8tion.jda.core.entities.Member;

public class Server extends Command {

	public Server() {
		this.name = "server";
		this.aliases = new String[] { "serverinfo", "srvr", "guildinfo" };
		this.guildOnly=true;
	}

	@Override
	protected void execute(CommandEvent event) {
		Guild guild = event.getGuild();
		long onlineCount = guild.getMembers().stream()
				.filter((u) -> (u.getOnlineStatus() == OnlineStatus.ONLINE
						|| u.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB
						|| u.getOnlineStatus() == OnlineStatus.IDLE))
				.count();
		int bot = 0;
		List<Member> members = guild.getMembers();
		for (Member memberM : members) {
			if (memberM.getUser().isBot())
				bot++;
		}
		String LINESTART = "  ➣  ";
		String str = "\uD83D\uDDA5 Informação sobre **" + guild.getName() + "**:\n" + LINESTART + "ID: **"
				+ guild.getId() + "**\n" + LINESTART + "Owner: **" + guild.getOwner().getUser().getName() + "** #"
				+ guild.getOwner().getUser().getDiscriminator() + "\n"

				+ LINESTART + "Localização: **" + guild.getRegion().getName() + "**\n" + LINESTART
				+ "Criação: **" + formatOffsetDateTime(guild.getCreationTime())+ "**\n"
				+ LINESTART + "Users: **" + guild.getMembers().size() + "** (" + onlineCount + " online, "
				+ bot + " bots)\n" + LINESTART + "Channels: **" + guild.getTextChannels().size()
				+ "** Text, **" + guild.getVoiceChannels().size() + "** Voice\n" + LINESTART
				+ "Verificação: **" + (guild.getVerificationLevel().equals(VerificationLevel.HIGH) ? "(╯°□°）╯︵ ┻━┻"
						: guild.getVerificationLevel())
				+ "**";
		if (guild.getIconUrl() != null)
			str += "\n" + LINESTART + "Server Icon: " + guild.getIconUrl();
		event.reply(str);

	}
	
	public static String formatOffsetDateTime(OffsetDateTime time) {
        return DateTimeFormatter.ofPattern("M/d/u h:m:s a").format(time);
    }

}
