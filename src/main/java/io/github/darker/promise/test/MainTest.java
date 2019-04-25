package io.github.darker.promise.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {
	public static final Pattern REGEX_MATCH_ID_FROM_FILE = Pattern.compile(".*?([\\\\\\/]|^)([0-9]+)\\.[a-zA-Z0-9]+$");
	public static final boolean TRUE_VALUE = true;
	public static void main(String[] args) {
		printMatches(REGEX_MATCH_ID_FROM_FILE, "666.ddd");
		printMatches(REGEX_MATCH_ID_FROM_FILE, "asa/dsad/666.ddd");
		printMatches(REGEX_MATCH_ID_FROM_FILE, "1111\\1111\\666.ddd");
		printMatches(REGEX_MATCH_ID_FROM_FILE, "ss666.png");
		//System.out.println(escapeRegexSymbols("${VAL}"));
		
		//System.out.println("${VAL}".replaceAll(escapeRegexSymbols("${VAL}"),"1"));
	}
	public static final void printMatches(Pattern regex, String input) {
		final Matcher m = regex.matcher(input);
		System.out.println("Matching: "+input);
		if(m==null || !m.matches()) {
			System.out.println("  - No match");
		}
		else {
			for(int i=0;i<=m.groupCount(); ++i) {
				System.out.println("  ("+i+"): "+m.group(i));
			}
		}
	}
    public static String escapeRegexSymbols(String notARegex) {
    	if(notARegex==null || notARegex.length()==0) {
    		return "";
    	}
    	return notARegex
    			.replaceAll("\\\\", "\\\\")
    			.replaceAll("\\[", "\\\\[")
    			.replaceAll("\\]", "\\\\]")
    			.replaceAll("\\(", "\\\\(")
    			.replaceAll("\\)", "\\\\)")
    			.replaceAll("\\.", "\\\\.")
    			.replaceAll("\\?", "\\\\?")
    			.replaceAll("\\+", "\\\\+")
    			.replaceAll("\\{", "\\\\{")
    			.replaceAll("\\}", "\\\\}")
    			.replaceAll("\\$", "\\\\\\$")
    			;
    }

}
