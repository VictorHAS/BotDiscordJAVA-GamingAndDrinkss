package net.plaayzone.gaminganddrinks.Commands.Utils;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class PLZ implements EventListener {

	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		Message message = event.getMessage();
		String content = message.getContentRaw();
		MessageChannel channel = event.getChannel();
		if (content.startsWith("!mencionar")) {
			if (event.getMessage().getMentionedMembers().isEmpty()) {
				channel.sendMessage("MENCIONA UM USUARIO POHA!").queue();
				return;
			}
			Member mencionado = message.getMentionedMembers().get(0);
			channel.sendMessage("<@" + mencionado.getUser().getId() + "> TU FOI MENCIONADO POHA").queue();
		}
		if (content.startsWith("!dxeudormirpoha")) {
			if (event.getMessage().getMentionedMembers().isEmpty()) {
				channel.sendMessage("MENCIONA UM USUARIO POHA!").queue();
				return;
			}
			Member mencionado = message.getMentionedMembers().get(0);
			Member mencionado2 = message.getMentionedMembers().get(1);
			channel.sendMessage("<@" + mencionado.getUser().getId() + "> <@" + mencionado2.getUser().getId()
					+ "> VCS FORAM MENCIONADO POHA").queue();
		}
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof MessageReceivedEvent)
			onMessageReceived((MessageReceivedEvent) event);

	}
}
