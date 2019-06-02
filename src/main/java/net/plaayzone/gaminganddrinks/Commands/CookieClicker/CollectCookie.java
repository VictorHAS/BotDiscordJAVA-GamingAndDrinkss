package net.plaayzone.gaminganddrinks.Commands.CookieClicker;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.plaayzone.Metodos;

public class CollectCookie implements EventListener {

	@Override
	public void onEvent(Event event) {
		if (event instanceof MessageReceivedEvent)
			onMessageReceived((MessageReceivedEvent) event);

	}

	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().startsWith("!droptable")) {
			return;
		}
		if(!event.getMessage().getContentRaw().startsWith("getcookie")) {
			return;
		}
		if (event.getAuthor().isBot()) {
			return;
		}
		if (event.getMessage().isFromType(ChannelType.PRIVATE)) {
			return;
		}
		Message message = event.getMessage();
		String content = message.getContentRaw();
		MessageChannel channel = event.getChannel().getJDA().getGuildById("427099848211431446")
				.getTextChannelById("427099848211431448");
		Role cookieclicker = event.getGuild().getRolesByName("CookieGame", true).get(0);
		if(!event.getMember().getRoles().contains(cookieclicker)) {
			event.getChannel().sendMessage("Use o canal <#427099848211431448> e o comando !cookiestart para começar a jogar!").queue();
			return;
		}
		if (!(event.getChannel() == channel)) {
			event.getChannel().sendMessage("Use o canal <#427099848211431448> e o comando !cookiestart para começar a jogar!").queue();
			return;
		}
		if(content.startsWith("getcookie")) {
			String player = event.getMember().getUser().getName();
			int qntcookclick = Metodos.getCookClick(player);
			Metodos.addCookies(player, qntcookclick);
			event.getChannel().sendMessage("Você tem agora: "+Metodos.getCookies(player)).queue();
		}
	}
}
