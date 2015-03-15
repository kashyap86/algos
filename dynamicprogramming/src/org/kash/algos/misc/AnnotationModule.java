package org.kash.algos.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnotationModule {
	
	
	public static void main(String[] args) {
		if(args.length != 3){
			System.out.println("Insufficent arguments provided. Required number of arguments : " + args.length);
			System.out.println("Usage: " + AnnotationModule.class.getName() + " <inputFile1> <inputFile2> <outputFile>");
			System.exit(0);
		}
		System.out.println("Input file 1: " + args[0]);
		System.out.println("Input file 2: " + args[1]);
		System.out.println("Output file: " + args[2]);
		
		String input1 = args[0];
		String input2 = args[1];
		String output = args[2];
		
		List<Triple> keyList = new ArrayList<Triple>();
		
		Map<Integer, Set<Triple>> map1 = buildMapFromFile(input1, true, false, keyList);
		Map<Integer, Set<Triple>> map2 = buildMapFromFile(input2, false, true, keyList);
		
		ExecutorService executor = Executors.newFixedThreadPool(map1.size());
		CountDownLatch latch = new CountDownLatch(map1.size());
		Map<Triple, List<String>> finalResultMap = new ConcurrentHashMap<Triple, List<String>>();
		for(Triple t: keyList) {
			finalResultMap.put(t, new ArrayList<String>());
		}
		for(Integer key: map1.keySet()) {
			OverlapResolver o = new OverlapResolver(map1.get(key), map2.get(key), finalResultMap, latch);
			executor.execute(o);
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executor.shutdown();
		writeOutputToFile(finalResultMap, keyList, output);
	}

	/**
	 * Writes the content of the resultMap into the output file in csv format
	 * @param resultMap - mapping of triple to list of annotations associated with the triple.
	 * @param keyList - keyList, which is used to get the original order of triples in inputFile1.
	 * @param output - output file name.
	 */
	private static void writeOutputToFile(Map<Triple, List<String>> resultMap, List<Triple> keyList, String output) {
		System.out.println("Writing to output file: " + output + " ...");
		StringBuilder sb = new StringBuilder();
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)))) {
			for(Triple t : keyList) {
				sb.append(t.getChromosome());
				sb.append(",");
				sb.append(t.getStart());
				sb.append(",");
				sb.append(t.getEnd());
				sb.append(",");
				List<String> values = resultMap.get(t);
				for(int i=0; i<values.size(); i++) {
					sb.append(values.get(i));
					if(i != values.size() - 1)
						sb.append(" ");
				}
				bw.write(sb.toString());
				bw.write("\n");
				sb = new StringBuilder();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("... Finished writing to output file: " + output);
	}

	/**
	 * Builds a map of Integer to Set of triples, with the key being the chromosome value 
	 * and the value being the set of triples that have the same chromosome values.
	 * This method also initializes the final resultMap.
	 * @param input - Input file name
	 * @param preserveTripleOrder - boolean to indicate whether to preserve triple order from inputFile1.
	 * @param hasAnnotations - boolean to indicate whether to read annotations from the file or not.
	 * @param keyList - List to maintain the triples in the same order as inputFile1.
	 * @return map - which has triples grouped by chromosome value.
	 */
	private static Map<Integer, Set<Triple>> buildMapFromFile(String input, boolean preserveTripleOrder, boolean hasAnnotations, List<Triple> keyList) {
		Map<Integer, Set<Triple>> map = new LinkedHashMap<Integer, Set<Triple>>();
		String line = "";
		System.out.println("Building map from file: " + input + "...");
		try(BufferedReader br = new BufferedReader(new FileReader(new File(input)))) {
			while((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				Integer chromosome = Integer.parseInt(tokens[0]);
				Integer start = Integer.parseInt(tokens[1]);
				Integer end = Integer.parseInt(tokens[2]);
				
				Triple triple = new Triple(chromosome, start, end);
				if(preserveTripleOrder) {
					keyList.add(triple);
				}
				if(hasAnnotations) {
					triple.setAnnotation(tokens[3]);
				}
				
				if(!map.containsKey(chromosome)) {
					Set<Triple> tripleSet = new LinkedHashSet<Triple>();
					tripleSet.add(triple);
					map.put(chromosome, tripleSet);
				} else {
					map.get(chromosome).add(triple);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("... Finished building map from file: " + input);
		return map;
	}
	
	/**
	 * Finds all the overlapping triples in set2 for each triple in set2,
	 * Associates the annotation value of the triple in set2 with the overlapping triples in set1
	 * Updates the resultMap with the list of annotations associated with triples in set1.
	 * @author kashyap_ivaturi
	 *
	 */
	private static class OverlapResolver implements Runnable {

		Set<Triple> set1;
		Set<Triple> set2;
		Map<Triple, List<String>> resultMap;
		CountDownLatch latch;
		public OverlapResolver(Set<Triple> set1, Set<Triple> set2, Map<Triple, List<String>> resultMap, CountDownLatch latch) {
			this.set1 = set1;
			this.set2 = set2;
			this.resultMap = resultMap;
			this.latch = latch;
		}
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + ". Start...");
			for(Triple t2 : set2) {
				for(Triple t1 : set1) {
					if(t2.isOverlap(t1)) {
							resultMap.get(t1).add(t2.getAnnotation());
					}
				}
			}
			System.out.println("...End. " + Thread.currentThread().getName());
			latch.countDown();
		}
		
	}
	
	private static class Triple {
		private Integer chromosome;
		private Integer start;
		private Integer end;
		private String annotation;
		
		public Triple(Integer chromosome, Integer start, Integer end) {
			this.chromosome = chromosome;
			this.start = start;
			this.end = end;
			this.annotation = "";
		}
		
		public boolean isOverlap(Triple t) {
			if((start <= t.start && t.start <= end)
					|| (t.start <= start && start <= t.end)) {
					return true;
			}
			return false;
		}

		public Integer getChromosome() {
			return chromosome;
		}

		public void setChromosome(Integer chromosome) {
			this.chromosome = chromosome;
		}

		public Integer getStart() {
			return start;
		}

		public void setStart(Integer start) {
			this.start = start;
		}

		public Integer getEnd() {
			return end;
		}

		public void setEnd(Integer end) {
			this.end = end;
		}

		public String getAnnotation() {
			return annotation;
		}
		public void setAnnotation(String annotation) {
			this.annotation = annotation;
		}
		
		public String toString() {
			return "[" + chromosome + ", " + start + ", " + end + ", " + annotation + "]";
		}
	}

}
