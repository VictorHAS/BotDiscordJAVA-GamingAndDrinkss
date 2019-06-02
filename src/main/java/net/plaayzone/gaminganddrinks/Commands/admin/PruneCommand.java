package net.plaayzone.gaminganddrinks.Commands.admin;

import java.util.ArrayList;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class PruneCommand extends Command {

	public PruneCommand() {
		this.name = "prune";
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
		String[] args = event.getArgs().split(" ");
		if (args.length == 2) {
			try {
				int num = Integer.parseInt(args[1]);
				if (num > 1 && num <= 100) {
					TextChannel textChannel = (TextChannel) event.getChannel();
					textChannel.getHistory().retrievePast(num).queue(messages -> {
						try {
							textChannel.deleteMessages(messages).queue(aVoid -> {
								event.reply("Successfully deleted " + num + " messages");
							});
						} catch (Exception e) {
							event.reply("Make sure the requested messages aren't over 2 weeks old and that I have "
									+ "permission " + "to delete messages.");
						}
					});

				} else
					event.reply("You need to specify a number between 1 and 100");
			} catch (NumberFormatException ex) {
				event.reply("That wasn't a number!");
			}
		} else {
			List<User> mentionedUsers = event.getMessage().getMentionedUsers();
			if (mentionedUsers.size() == 1) {
				User mentioned = mentionedUsers.get(0);
				try {
					int num = Integer.parseInt(replace(event.getMessage().getContentRaw(), 2));
					if (num > 1 && num < 100) {
						TextChannel textChannel = (TextChannel) event.getChannel();
						ArrayList<Message> messagesToDelete = new ArrayList<>();
						textChannel.getHistory().retrievePast(200).queue(messages -> {
							for (Message m : messages) {
								if (m.getAuthor().getId().equals(mentioned.getId()) && messagesToDelete.size() < num)
									messagesToDelete.add(m);
							}
							textChannel.deleteMessages(messagesToDelete).queue(aVoid -> {
								try {
									event.reply("Deleted " + messagesToDelete.size() + " messages from that user");
								} catch (Exception e) {
									e.printStackTrace();
								}
							});
						});
					} else
						event.reply("You need to specify a number between 1 and 100");
				} catch (NumberFormatException ex) {
					event.reply("That's not a number!");
				}
			} else
				event.reply("You need to mention a user");
		}
	}

	public String replace(String content, int amountOfArgs) {
		String[] arrayed = content.split(" ");
		StringBuilder toReplace = new StringBuilder();
		for (int start = 0; start < amountOfArgs; start++) {
			toReplace.append(arrayed[start] + " ");
		}
		return content.replace(toReplace.toString(), "");
	}
}
