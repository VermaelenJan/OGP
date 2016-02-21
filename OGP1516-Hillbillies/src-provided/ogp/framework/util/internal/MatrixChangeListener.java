package ogp.framework.util.internal;

public interface MatrixChangeListener<T> {

	public void onMatrixChanged(int row, int column, T oldValue, T newValue);
}
