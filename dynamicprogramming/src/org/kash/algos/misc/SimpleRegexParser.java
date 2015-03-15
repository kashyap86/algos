package org.kash.algos.misc;

public class SimpleRegexParser {

	public static void main(String[] args) {
		SimpleRegexParser parser = new SimpleRegexParser();
		String pattern = "a*c*.";
		String target = "";
		System.out.println(parser.match(pattern, target));
	}

	private boolean match(String pattern, String target) {
		if(pattern.length() == 0)
			return false;
		if(pattern.charAt(0) == '*') {
			if(pattern.length() == 1)
				return true;
			else 
				return matchStar(pattern.substring(1), target);
		} else if(target.length() == 0){
			return false;
		} else if(pattern.charAt(0) == '.' || (pattern.charAt(0) == target.charAt(0))) {
			if(pattern.length() == 1 && target.length() == 1)
				return true;
			return match(pattern.substring(1), target.substring(1));
		}
		
		return false;
	}

	private boolean matchStar(String pattern, String target) {
		
		for(int i = 0; i<target.length(); i++) {
			if(match(pattern, target.substring(i)))
				return true;
			else
				continue;
		}
		return false;
	}
}
