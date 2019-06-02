package net.plaayzone.gaminganddrinks.Commands.admin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class EvalCmd extends Command {
	public EvalCmd() {
		this.name = "eval";
		this.help = "evaluates nashorn code";
		this.ownerCommand = true;
		this.guildOnly = false;
	}

	@Override
	protected void execute(CommandEvent event) {
		ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
		se.put("event", event);
		se.put("jda", event.getJDA());
		se.put("message", event.getMessage());
		se.put("guild", event.getGuild());
		se.put("user", event.getMessage().getMentionedUsers());
		se.put("channel", event.getChannel());
		try {
			event.reply(event.getClient().getSuccess() + " Evaluated Successfully:\n```\n" + se.eval(event.getArgs())
					+ " ```");
		} catch (Exception e) {
			event.reply(event.getClient().getError() + " An exception was thrown:\n```\n" + e + " ```");
		}
	}
}
