package org.blueo.commons.test;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.Lists;

public class LogTester {

	private static String str = "[Person [name=Person [name=a name, sex=male, age=1], sex=male, age=1], Person [name=b name, sex=female, age=2]]";

	public static Triple<String, Integer, Integer> getBracketContent(String str, char start, char end) {
		char[] charArray = str.toCharArray();
		Integer startIdx = null;
		Integer endIdx = null;
		int startCount = 0;
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (start == c) {
				if (startIdx == null) {
					startIdx = i;
				}
				startCount++;
			} else if (end == c) {
				if (startIdx != null) {
					startCount--;
					if (startCount == 0) {
						endIdx = i;
						break;
					}
				}
			} else {
				// do nothing
			}
		}
		if (startIdx != null && endIdx != null) {
			String content = str.substring(startIdx + 1, endIdx - startIdx);
			return new ImmutableTriple<String, Integer, Integer>(content, startIdx, endIdx);
		} else {
			return null;
		}
	}

	public static Triple<String, Integer, Integer> getBracketContent(String str) {
		return getBracketContent(str, '[', ']');
	}

	public static int levelCounts(String str, char start, char end) {
		char[] charArray = str.toCharArray();
		Integer startIdx = null;
		Integer endIdx = null;
		int startCount = 0;
		int max = 0;
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (start == c) {
				if (startIdx == null) {
					startIdx = i;
				}
				startCount++;
				max = Math.max(max, startCount);
			} else if (end == c) {
				if (startIdx != null) {
					startCount--;
					if (startCount == 0) {
						endIdx = i;
						break;
					}
				}
			} else {
				// do nothing
			}
		}
		if (startIdx != null && endIdx != null) {
			return max;
		} else {
			throw new IllegalArgumentException("Input has bad format.");
		}
	}

	public static List<Obj> a(String str, int level) {
		List<Character> chars = toList(str);
		List<Obj> objs = Lists.newArrayList();
		int currentLevel = level;
		boolean lowestLevelEnding = false;
		for (int i = 0; i < chars.size(); i++) {
			char c = chars.get(i);
			switch (c) {
			case '[':
				currentLevel--;
				if (currentLevel != 0) {
					objs.add(new Obj(i, currentLevel));
				}
				break;
			case ']':
				currentLevel++;
				if (currentLevel == 1) {
					lowestLevelEnding = true;
				} else {
					objs.add(new Obj(i - 1, currentLevel));
				}
				break;
			default:
				if (lowestLevelEnding && !Character.isWhitespace(c) && c != ',') {
					objs.add(new Obj(i - 1, currentLevel));
					lowestLevelEnding = false;
				}
				break;
			}
		}
		return objs;
	}
	

	public static List<Character> toList(String str){
		List<Character> list = Lists.newArrayList();
		for (char c : str.toCharArray()) {
			list.add(c);
		}
		return list;
	}
	
	public static String toString(List<Character> characters){
		char[] chars = new char[characters.size()];
		return new String(chars);
	}
	
	public static void main(String[] args) {
		System.out.println(str);
		int levelCounts = levelCounts(str, '[', ']');
		List<Obj> objs = a(str, levelCounts);
		System.out.println(objs);

		List<Character> chars = toList(str);
		objs.sort((a, b) ->  b.getIndex() - a.getIndex());
		for (Obj obj : objs) {
			int index = obj.getIndex();
			int level = obj.getLevel();
			chars.addAll(index+1, toList("\n"+StringUtils.repeat("\t", levelCounts-level)));
		}
		for (Character c : chars) {
			System.out.print(c);
		}
		System.out.println();
		System.out.println("**************");
		System.out.println(chars);
		System.out.println(toString(chars));
	}

}
