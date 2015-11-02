package HelperClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {
	/*
	 * Read graph from the given file path. filePath is relative path. 
	 * Assume all testing files go in the same directory with FileReader
	 * 
	 * Return 2D adjacency matrix 
	 */
	public int[][] readGraph(String filePath) throws IOException{
		InputStream is = FileReader.class.getResourceAsStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		ArrayList<String> input = new ArrayList<>();
		String s;
		while((s = br.readLine())!= null) 
			input.add(s);
		br.close();
		int graphSize = input.size();
		int[][] adjMatrix = new int[graphSize][graphSize];
		for(int i = 0; i < graphSize; i++){
			String temp = input.get(i);
			for(int j = 0; j < graphSize; j++){
				if(temp.charAt(j)!= ' ') {
					adjMatrix[i][j] = Character.getNumericValue(temp.charAt(j));
				}
			}
		}
		return adjMatrix;
	}
}

