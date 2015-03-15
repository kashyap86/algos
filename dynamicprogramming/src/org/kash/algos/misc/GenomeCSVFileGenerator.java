package org.kash.algos.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenomeCSVFileGenerator {

	public static final String filename = "csvInput2.csv";
	public static void main(String[] args) throws IOException {
		Integer chromosome;
		Integer start;
		Integer end;
		String annotation;
		char[] characterArr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		String characters = "abcdefghijklmnopqrstuvwxyz";
		int numberOfLines = 1000000;
		int startEndRange = 1000000;
		int stringLength = 20;
		
		File file = new File(filename);
		if(!file.exists())
			file.createNewFile();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		Random r = new Random();
		for(int i= 1; i<=numberOfLines; i++) {
			chromosome = r.nextInt(22) +1;
			start = r.nextInt(startEndRange) + 1;
			if(start == startEndRange)
				end = startEndRange;
			else
				end = start + r.nextInt(startEndRange - start) + 1;
			assert start <= end;
			annotation = generateString(r, characters, r.nextInt(stringLength) + 1);
			//System.out.println(annotation);
			String line = chromosome.toString() + "," + start.toString() + "," + end.toString() + "," + annotation;
			bw.write(line);
			bw.write("\n");
			//System.out.println(line);
		}
		bw.close();
		
	}
	
	public static String generateString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
}
