package ogp.framework.util.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Matrix<T> {

	private final int nbRows;
	private final int nbCols;

	private List<T> values;

	public Matrix(int nbRows, int nbCols, T defaultValue) {
		this.nbRows = nbRows;
		this.nbCols = nbCols;
		this.values = new ArrayList<>(nbRows * nbCols);
		for (int i = 0; i < nbRows * nbCols; i++) {
			values.add(defaultValue);
		}
	}

	private final Set<MatrixChangeListener<T>> listeners = new HashSet<>();

	public void addChangeListener(MatrixChangeListener<T> listener) {
		listeners.add(listener);
	}

	public void removeChangeListener(MatrixChangeListener<T> listener) {
		listeners.remove(listener);
	}

	public int getNbRows() {
		return nbRows;
	}

	public int getNbCols() {
		return nbCols;
	}

	protected void checkIndices(int row, int col) {
		if (!isValid(row, col))
			throw new IndexOutOfBoundsException();
	}

	protected int getIndex(int row, int col) {
		checkIndices(row, col);
		return row * getNbCols() + col;
	}

	protected int getRow(int index) {
		return index / getNbCols();
	}

	protected int getCol(int index) {
		return index % getNbCols();
	}

	public boolean isValid(int row, int col) {
		return row >= 0 && row < getNbRows() && col >= 0 && col < getNbCols();
	}

	public T getValueAt(int row, int col) {
		return values.get(getIndex(row, col));
	}

	public boolean setValueAt(int row, int col, T value) {
		T oldValue = getValueAt(row, col);
		if (oldValue == value) {
			return false;
		}
		if (oldValue == null || !oldValue.equals(value)) {
			values.set(getIndex(row, col), value);
			notifyListenersChange(row, col, oldValue, value);
		}
		return true;
	}

	protected void notifyListenersChange(int row, int col, T oldValue, T newValue) {
		for (MatrixChangeListener<T> listener : new HashSet<>(listeners)) {
			listener.onMatrixChanged(row, col, oldValue, newValue);
		}
	}

	public static <T> Matrix<T> fromNestedArrayRowColumn(T[][] values) {
		int nbRows = values.length;
		int nbCols = 0;
		if (nbRows > 0) {
			nbCols = values[0].length;
			for (int i = 1; i < nbRows; i++) {
				if (values[i].length != nbCols) {
					throw new IllegalArgumentException("All rows must have the same number of elements");
				}
			}
		}
		Matrix<T> result = new Matrix<>(nbRows, nbCols, null);
		for (int r = 0; r < nbRows; r++) {
			for (int c = 0; c < nbCols; c++) {
				result.setValueAt(r, c, values[r][c]);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int row = nbRows-1; row >= 0; row--) {
			for (int col = 0; col < nbCols; col++) {
				if (col > 0) {
					result.append(",");
				}
				result.append(getValueAt(row, col));
			}
			result.append("\n");
		}
		return result.toString();
	}
}
