package org.blueo.pojogen.bo.wrapper.clazz;

import java.util.List;

import org.blueo.commons.text.BlueoStrs;
import org.javatuples.Triplet;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;


public class ClassWrapperParser {
	
	public static ClassWrapper parse(String classNameWithParameterizedTypes) {
		Triplet<String, String, String> texts = BlueoStrs.parse(classNameWithParameterizedTypes, '<', '>');
		Iterable<String> parameterizedTypes = Splitter.on(',').trimResults().split(texts.getValue0());
		// 
		List<ClassWrapper> classWrappers = Lists.newArrayList();
		for (String parameterizedType : parameterizedTypes) {
			classWrappers.add(parse(parameterizedType));
		}
		return new ClassWrapper(texts.getValue1(), classWrappers);
	}
	
}
