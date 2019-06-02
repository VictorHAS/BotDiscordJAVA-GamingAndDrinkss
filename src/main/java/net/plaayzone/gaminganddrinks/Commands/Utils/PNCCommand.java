package net.plaayzone.gaminganddrinks.Commands.Utils;

import java.io.File;
import java.io.IOException;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Member;

public class PNCCommand extends Command {

	public PNCCommand() {
		this.name = "foto";
		this.help = "Manda um meme";
		this.arguments = "<nome> <nome>";
	}

	@Override
	protected void execute(CommandEvent e) {
		if (e.getArgs().isEmpty()) {
			e.replyWarning("Você não me deu nomes!");
			return;
		}
		String[] args = e.getArgs().split(" ");
		if (args.length == 1) {
			e.replyWarning("Você precisa me dar mais um nome!");
		} else {
			File imageOriginal = new File("assets/gato.jpg");
			File Saida = new File("assets/gatoedit.jpg");
			Member member;
			Member member2;
			if (e.getMessage().getMentionedUsers().isEmpty()) {
				try {
					member = e.getGuild().getMemberById(e.getArgs());
					member2 = e.getGuild().getMemberById(e.getArgs());
				} catch (Exception e2) {
					member2 = null;
					member = null;
				}
			} else
				member = e.getGuild().getMember(e.getMessage().getMentionedUsers().get(0));
			member2 = e.getGuild().getMember(e.getMessage().getMentionedUsers().get(1));
			if (member == null) {
				e.reply(e.getClient().getError() + " Não achei o player `" + e.getArgs() + " `!");
				return;
			}
			if (member2 == null) {
				e.reply(e.getClient().getError() + " Não achei o player `" + e.getArgs() + " `!");
				return;
			}
			try {
				FotoCommand.memegato(member, member2, "jpg", imageOriginal, Saida, 40);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.getChannel().sendFile(new File("assets/gatoedit.jpg")).queue();
		}
	}

}
