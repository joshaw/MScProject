/** Created: Wed 09 Jul 2014 12:50 PM
 * Modified: Wed 30 Jul 2014 12:20 PM
 * @author Josh Wainwright
 * File name : DrawSimpleGrid.java
 */
package utils;

import java.util.StringTokenizer;

/** A number of utility functions for manipulating Strings.
 */
public class Sutils {

	/** Returns the longer of s1 and s2.
	 * @return longer of s1 and s2
	 */
	public static String longest(String s1, String s2) {
		if (s1.length() >= s2.length()) {
			return s1;
		} else {
			return s2;
		}
	}

	/** Returns the shorter of s1 and s2.
	 * @return shorter of s1 and s2
	 */
	public static String shortest(String s1, String s2) {
		if (s1.length() <= s2.length()) {
			return s1;
		} else {
			return s2;
		}
	}

	/** Returns a new string which is the result of taking alternating
	 * characters from the two arguements. eg interleave(abc, efg) -> adbecf
	 * @param s1 first string to interleave
	 * @param s2 second string to interleave
	 * @return interleaved result of s1 and s2
	 */
	public static String interleave(String s1, String s2) {
		if (s1.length() == 0) {
			return s2;
		}
		if (s2.length() == 0) {
			return s1;
		}
		return "" + s1.charAt(0) + s2.charAt(0) +
			interleave(s1.substring(1), s2.substring(1));
	}

	/** Returns the maximum value from a arbitrary number of integers.
	 * @param args a list of integers to choose the maximum from
	 * @return the maximum value from args.
	 */
	public static int max(int...args) {
		int m = 0;
		for (int i : args) {
			if (i > m) {
				m = i;
			}
		}
		return m;
	}

	/** Splits a string into substring using the characters contained in the
	 * second argument as the delimiter set.
	 * *** Addapted from ImageJ source code. ***
	 */
	public static String[] split(String str, String delim) {

		StringTokenizer t = new StringTokenizer(str, delim);
		int tokens = t.countTokens();
		String[] strings;

		if (tokens>0) {
			strings = new String[tokens];
			for(int i=0; i<tokens; i++)
				strings[i] = t.nextToken();
		} else
			strings = new String[0];
		return strings;
	}

}
