package net.plaayzone.gaminganddrinks.Commands.Utils;

import java.awt.AlphaComposite;
//import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
//import java.awt.Stroke;
import java.awt.font.TextLayout;
//import java.awt.geom.Area;
//import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.plaayzone.ImageManager;

public class BVCommand implements EventListener {
	private TextLayout textshadow;

	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		Message message = event.getMessage();
		String content = message.getContentRaw();
		MessageChannel channel = event.getChannel();
		if (content.equals("!s")) {
			User user = event.getMember().getUser();
			File imageOriginal = new File("assets/bv.png");
			File Saida = new File("assets/bvv-" + user.getName().replaceAll("[^a-zA-Z0-9]", "") + ".png");
			try {
				profile(user, "png", imageOriginal, Saida, 40, user, 10);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Role pascoa = event.getGuild().getRolesByName("pascoa 2018", true).get(0);
			channel.sendMessage("Seja bem vindo ao servidor <@" + event.getMember().getUser().getId()
					+ "> COELHOS AVANTES <@&" + pascoa.getId() + ">").queue();
			channel.sendFile(new File("assets/bvv-" + user.getName().replaceAll("[^a-zA-Z0-9]", "") + ".png")).queue();
			Saida.delete();
		}
		return;
	}

	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		User user = e.getMember().getUser();
		File imageOriginal = new File("assets/bv.png");
		File Saida = new File("assets/bvv-" + user.getName().replaceAll("[^a-zA-Z0-9]", "") + ".png");
		try {
			profile(user, "png", imageOriginal, Saida, 55, user, 20);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		MessageChannel channel = e.getGuild().getTextChannelById("427099848211431448");
		Role pascoa = e.getGuild().getRolesByName("pascoa 2018", true).get(0);
		channel.sendMessage("Seja bem vindo ao servidor <@" + e.getMember().getUser().getId() + "> COELHOS AVANTES <@&"
				+ pascoa.getId() + ">").queue();
		channel.sendFile(new File("assets/bvv-" + user.getName().replaceAll("[^a-zA-Z0-9]", "") + ".png")).queue();
		Saida.delete();
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof GuildMemberJoinEvent)
			onGuildMemberJoin((GuildMemberJoinEvent) event);
		else if (event instanceof MessageReceivedEvent)
			onMessageReceived((MessageReceivedEvent) event);

	}

	public void profile(User player, String tipodoarquivo, File ondetaimg, File caminhoresultadofinal,
			Integer TamanhoFont, User user, int cornerRadius) throws IOException {

		BufferedImage image = ImageIO.read(ondetaimg);

		String texto = player.getName().replaceAll("[^a-zA-Z0-9]", "");

		int imageType = "png".equalsIgnoreCase(tipodoarquivo) ? BufferedImage.TYPE_INT_ARGB
				: BufferedImage.TYPE_INT_RGB;

		BufferedImage imagem = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

		// Pega o avatar da internet
		Image avatar = ImageManager.downloadImage(user.getEffectiveAvatarUrl()).getScaledInstance(200, 188,
				BufferedImage.SCALE_SMOOTH);
//		BufferedImage avar = GamingUtils.toBufferedImage(avtr);
//		BufferedImage avatar = GamingUtils.makeRoundedCorner(avar, 50);
		// Lê a fonte
		Font font = new Font("Uni Sans Thin", Font.BOLD, TamanhoFont);

		// Graphics
		Graphics2D g2 = (Graphics2D) avatar.getGraphics();
		Graphics2D w = (Graphics2D) imagem.getGraphics();
		Graphics2D w1 = imagem.createGraphics();
		// Graphics2D w2 = imagem.createGraphics();

		// Qualidade
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);

		// imagem de fundo
		w.drawImage(image, 0, 0, null);

		// Ajustar a transparencia
		AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f);
		// AlphaComposite alphaChannel3 =
		// AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.88f);

		// Coloca transparencia
		w1.setComposite(alphaChannel);
		// w2.setComposite(alphaChannel3);

		// seta as cores
		w.setColor(Color.WHITE);
		w1.setColor(Color.BLACK);
		// w2.setColor(Color.WHITE);
		int width = 200;
		int height = 200;
		g2.setClip(new RoundRectangle2D.Double(0, 0, width, height, width / 4, height / 4));
		// w.drawImage(avatar, 50, 45, null); // Avatar do usuario
		g2.drawImage(avatar, 50, 45, null);
		w.setFont(font);

		// melhorar a img
		setRenderingHints(w1);
		// setRenderingHints(w2);

		// Cria o retangulo ao redor do user avatar
		// RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(50, 45, 200,
		// 188, 10, 10);

		// Nome do usuario
		textshadow = new TextLayout(texto, font, w1.getFontRenderContext());
		textshadow.draw(w1, 350, 175);
		w.drawString(texto, 350, 170); // Nome do usuario

		// w2.draw(roundedRectangle); // Retangulo da borda

		// Joga na memoria
		w1.dispose();
		w.dispose();
		g2.dispose();

		// Salva img
		ImageIO.write(imagem, tipodoarquivo, caminhoresultadofinal);

	}
	// não mexer
	private void setRenderingHints(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}
	// Não mexer

}
