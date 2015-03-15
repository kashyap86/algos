package org.kash.algos.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderedLicensePlate {

	public static void main(String[] args) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println("Enter input filename:");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
		String fileName = br1.readLine();
		br1.close();
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String line = "";
		Pattern pattern = Pattern.compile("[1-9][A-Z][A-Z][A-Z]\\d\\d\\d");
		while((line = br.readLine()) != null) {
			line = line.replaceAll("\\s+", " ");
			line = line.trim();
			line = line.toUpperCase();
			String[] record = line.split("\\s+");
			String src = record[0];
			String dest = record[1];
			
			Matcher mSrc = pattern.matcher(src);
			Matcher mDest = pattern.matcher(dest);
			
			if(mSrc.matches() && mDest.matches()) {
				if(!map.containsKey(src)) {
						map.put(src, dest);
				} else {
					map.put(src, map.get(src));
				}
			}
		}
		br.close();
		
		Set<String> set1 = new HashSet<String>();
		set1.addAll(map.keySet());
		Set<String> set2 = new HashSet<String>();
		set2.addAll(map.values());
		set1.removeAll(set2);
		String start = set1.toArray(new String[set1.size()])[0];
		
		set1 = new HashSet<String>();
		set1.addAll(map.keySet());
		set2 = new HashSet<String>();
		set2.addAll(map.values());
		set2.removeAll(set1);
		String end = set2.toArray(new String[set2.size()])[0];
		
		String curSource = start;
		while(!curSource.equals(end)) {
			System.out.println(curSource);
			curSource = map.get(curSource);
		}
		System.out.println(curSource);
	}
}
