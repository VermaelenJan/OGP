package ogp.framework.util.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.stream.Stream;

import javafx.scene.image.Image;

public class ResourceUtils {

	public static Image loadImage(String filename) {
		try {
			InputStream inputStream = openResource(filename);
			Image result = new Image(inputStream);
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Could not read file '" + filename + "'", e);
		}
	}

	public static InputStream openResource(String filename) throws IOException {
		URL url = toURL(filename);
		return openResource(url);
	}

	public static InputStream openResource(URL url) throws IOException {
		InputStream result;

		URLConnection conn = url.openConnection();
		result = conn.getInputStream();

		return result;
	}

	public static URL toURL(String filename) throws FileNotFoundException {
		URL url = ResourceUtils.class.getResource("/" + filename);
		if (url == null) {
			try {
				File file = new File(filename);
				if (file.exists()) {
					url = new File(filename).toURI().toURL();
				} else {
					throw new FileNotFoundException("File not found: " + filename);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return url;
	}

	public static Stream<String> listFileNames(String folder) throws FileNotFoundException {
		InputStream res = ResourceUtils.class.getResourceAsStream("/" + folder);
		if (res != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(res));
			return br.lines();
		} else {
			File file = new File(folder);
			if (file.exists() && file.isDirectory()) {
				return Arrays.stream(file.list());
			} else {
				throw new FileNotFoundException("File not found: " + folder);
			}
		}
	}
}
