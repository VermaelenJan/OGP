package ogp.framework.game;

public interface IGameController {
	
	public IGameView getView();

	public void updateGame(double inGameTime);

	public void exit();

	public <T> T getNextObject(Class<T> type);

}
