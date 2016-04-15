package hillbillies.part3.programs;

import be.kuleuven.cs.som.annotate.Value;

/**
 * A source location represents a position in a source file,
 * denoted by the line and column (position in the line) of 
 * a certain character in the file.
 * 
 * This class is a value class.
 */
@Value
public final class SourceLocation {

	private final int line;
	private final int column;
	
	public SourceLocation(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + line;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceLocation other = (SourceLocation) obj;
		if (column != other.column)
			return false;
		if (line != other.line)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("@%d,%d", line, column);
	}
	
}
