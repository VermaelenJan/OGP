package hillbillies.part2.internal.map;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import ogp.framework.util.internal.ResourceUtils;

public class GameMapReader {

	public GameMap readFromFile(String filename) throws FileNotFoundException {
		return readFromReader(new FileReader(filename));
	}

	public GameMap readFromReader(Reader reader) {
		try {
			BufferedReader breader = new BufferedReader(reader);
			String line = breader.readLine();
			if (line != null) {
				String[] dims = line.split(" ");
				int nbX = Integer.parseInt(dims[0]);
				int nbY = Integer.parseInt(dims[1]);
				int nbZ = Integer.parseInt(dims[2]);
				GameMap map = new GameMap(nbX, nbY, nbZ);
				for (int z = nbZ - 1; z >= 0; z--) {
					line = breader.readLine();
					if (line == null) {
						throw new EOFException("Unexpected end of file; no data for z=" + z);
					}
					line = line.trim();
					if (line.length() != 0) {
						throw new IllegalArgumentException("Expected empty line");
					}
					for (int y = 0; y < nbY; y++) {
						line = breader.readLine();
						if (line == null) {
							throw new EOFException("Unexpected end of file; no data for z=" + z + " and y = " + y);
						}
						line = line.trim();
						String[] chars = line.split("");
						if (chars.length != nbX) {
							throw new EOFException("Unexpected end of line; for z=" + z + " and y = " + y
									+ ", only have " + chars.length + " characters.");
						}
						for (int x = 0; x < nbX; x++) {
							CubeType type;
							switch (chars[x]) {
							case ".":
								type = CubeType.EMPTY;
								break;
							case "R":
								type = CubeType.ROCKS;
								break;
							case "S":
								type = CubeType.TREES;
								break;
							case "W":
								type = CubeType.WORKSHOP;
								break;
							default:
								throw new IllegalArgumentException("Unknown type: " + chars[x]);
							}
							map.setTypeAt(x, y, z, type);
						}
					}
				}
				return map;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		GameMap map = new GameMapReader().readFromResource("resources/80x80x80.wrld");
		System.out.println(map);
	}

	public GameMap readFromResource(String resourceURI) throws IOException {
		return readFromReader(new InputStreamReader(ResourceUtils.openResource(resourceURI)));
	}
}
