package ogp.framework.game;

public interface IGameController<V extends IGameView> {
	
	public V getView();

	public void updateGame(double inGameTime);

	public void exit();

}
