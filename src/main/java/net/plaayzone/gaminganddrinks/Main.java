package net.plaayzone.gaminganddrinks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.mashape.unirest.http.Unirest;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.plaayzone.Conexao;
import net.plaayzone.gaminganddrinks.Commands.CookieClicker.CollectCookie;
import net.plaayzone.gaminganddrinks.Commands.CookieClicker.CookieStart;
import net.plaayzone.gaminganddrinks.Commands.Utils.BVCommand;
import net.plaayzone.gaminganddrinks.Commands.Utils.PLZ;
import net.plaayzone.gaminganddrinks.Commands.Utils.PNCCommand;
import net.plaayzone.gaminganddrinks.Commands.admin.AnnoucementCommand;
import net.plaayzone.gaminganddrinks.Commands.admin.DropTable;
import net.plaayzone.gaminganddrinks.Commands.admin.EvalCmd;
import net.plaayzone.gaminganddrinks.Commands.admin.PruneCommand;
import net.plaayzone.gaminganddrinks.Commands.admin.Server;
import net.plaayzone.gaminganddrinks.Commands.admin.SetGameCommand;
import net.plaayzone.gaminganddrinks.Commands.admin.SetOnlineStateCommand;
import net.plaayzone.gaminganddrinks.Commands.profile.Profile;
import net.plaayzone.gaminganddrinks.Commands.profile.SetBF;

public class Main extends ListenerAdapter{
	private static JDA jda;

	public static void main(String[] args)
			throws IOException, LoginException, IllegalArgumentException, RateLimitedException {

		List<String> list = Files.readAllLines(Paths.get("config.txt"));

		String token = list.get(0);
		String ownerID = list.get(1);
		EventWaiter waiter = new EventWaiter();

		CommandClientBuilder client = new CommandClientBuilder();
		client.useDefaultGame();

		client.setOwnerId(ownerID);

		client.setEmojis("\u2714", "\uD83D\uDE2E", "\u274c");

		client.setPrefix("!");

		client.addCommands(new PNCCommand(), new EvalCmd(), new Server(), new ShutdownCmd(),
				new SetOnlineStateCommand(), new SetGameCommand(), new AnnoucementCommand(), new PruneCommand(),
				new Profile(), new CookieStart(), new SetBF(), new DropTable());
		client.setGame(Game.playing("e testando"));
		jda = new JDABuilder(AccountType.BOT).setToken(token).setStatus(OnlineStatus.DO_NOT_DISTURB)
				.addEventListener(waiter).addEventListener(client.build()).addEventListener(new BVCommand())
				.addEventListener(new CollectCookie()).addEventListener(new PLZ()).buildAsync();

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Conexao.open("jdbc:mysql://127.0.0.1/gaminganddrinks?useSSL=false", "root", "");
		Conexao.criarTable();

	}

	public synchronized static void shutdown() throws IOException {
		System.out.println("Bot desligado!");
		getJda().shutdown();
		Unirest.shutdown();
		Conexao.close();
	}

	public static JDA getJda() {
		return jda;
	}

}
