package net.plaayzone.gaminganddrinks.Commands.Utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class FotoCommand extends ListenerAdapter {
	
	private static TextLayout textshadow;
	private static TextLayout textshadow2;

	// não mexer
	public static void setRenderingHints(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}
	// Não mexer
	
	
//	@Override
//	public void onMessageReceived(MessageReceivedEvent event) {
//		if (event.getAuthor().isBot())
//			return;
//
//		Message message = event.getMessage();
//		String content = message.getContentRaw();
//		MessageChannel channel = event.getChannel();
//
//		if (content.equals("!foto")) {
//
//			File imageOriginal = new File("gato.jpg");
//			File Saida = new File("gatoedit.jpg");
//			Member p1 = event.getMessage().getMentionedMembers().get(0);
//			Member p2 = event.getMessage().getMentionedMembers().get(1);
//			try {
//				memegato(p1,p2, "jpg", imageOriginal, Saida, 40);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			channel.sendFile(new File("gatoedit.jpg")).queue();
//
//		}
//	}

	public static void memegato(Member playername, Member playername2, String tipodoarquivo, File ondetaimg, File caminhoresultadofinal,
			Integer TamanhoFont) throws IOException {

		BufferedImage image = ImageIO.read(ondetaimg);
		String texto = playername.getEffectiveName();
		String texto2 = playername2.getEffectiveName();
		int imageType = "png".equalsIgnoreCase(tipodoarquivo) ? BufferedImage.TYPE_INT_ARGB
				: BufferedImage.TYPE_INT_RGB;
		BufferedImage imagem = new BufferedImage(image.getWidth(), image.getHeight(), imageType);
		Font font = new Font("Uni Sans Thin", Font.BOLD, TamanhoFont);
		Graphics2D w = (Graphics2D) imagem.getGraphics();
		Graphics2D w1 = imagem.createGraphics();
		w.drawImage(image, 0, 0, null);
		AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
		AlphaComposite alphaChannel2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f);
		w.setComposite(alphaChannel2);
		w1.setComposite(alphaChannel);
		w1.setColor(Color.BLACK);
		w.setColor(Color.WHITE);
		w.setFont(font);
		setRenderingHints(w1);
		textshadow = new TextLayout(texto, font, w1.getFontRenderContext());
		textshadow2 = new TextLayout(texto2, font, w1.getFontRenderContext());
		FontMetrics fontMetrics = w.getFontMetrics();
		Rectangle2D rect = fontMetrics.getStringBounds(texto, w);

		// Pega o centro da img
		int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
		int centerY = image.getHeight() / 2;

		// Adiciona o texto shadow e rotacionado
		textshadow.draw(w1, centerX + 150, centerY + 86);
		textshadow2.draw(w1, centerX - 180, centerY - 16);
		
		// Texto sem shadow
		w.drawString(texto, centerX + 150, centerY + 82); // arg[0]
		w.drawString(texto2, centerX - 180, centerY - 20); // arg[1]
		//Salva img
		ImageIO.write(imagem, tipodoarquivo, caminhoresultadofinal);
		w1.dispose();
		w.dispose();
	}

}
