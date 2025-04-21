package com.yuning.learning.english.utils;


import com.yuning.learning.english.dto.Dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DictionaryUtils {
	private static Map<String, List<Dictionary>> fatherClassName2SubClass  = new HashMap<String, List<Dictionary>>();
	public static List<Dictionary> getDictionarySubClass(String fatherClass) {
		return fatherClassName2SubClass.get(fatherClass);
	}
}
