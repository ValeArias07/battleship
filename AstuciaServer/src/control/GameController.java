package control;

import comm.Receptor.OnMessageListener;

import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import model.Message;
import view.GameWindow;

public class GameController implements OnMessageListener {

	private GameWindow view;
	private TCPConnection connection;

	public GameController(GameWindow view) {
		this.view = view;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);

		int fil = (int) (3 * Math.random());
		int col = (int) (3 * Math.random());
		view.drawWeakPointInRadar(fil, col);
		
		view.getSendNameBtn().setOnAction(
				event ->{
					Gson gson = new Gson();
					String json = gson.toJson(new Message("Hola mundo"));
					TCPConnection.getInstance().getEmisor().sendMessage(json);
				});

	}

	@Override
	public void OnMessage(String msg) {
		Platform.runLater(
				() -> {
					Gson gson = new Gson();
					Message msjObj = gson.fromJson(msg, Message.class);
					System.out.println(msjObj.getBody());
				}
		);

	}

}
