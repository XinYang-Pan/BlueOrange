package org.blueo.commons;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

public abstract class BlueoUtils {
	private static int MAX_SQL_LENGTH = 10000;

	/* Sample
	@SuppressWarnings("serial")
	private final Class<T> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
	*/
	@SuppressWarnings({ "unchecked"})
	public static <T> Class<T> getParameterizedClass(TypeToken<T> typeToken) {
		Type type = typeToken.getType();
		//
		Preconditions.checkArgument(type instanceof Class<?>);
		Class<T> clazz = (Class<T>) type;
		return clazz;
	}
	
	@Deprecated // NOT TESTED
	public static <T, P> List<T> queryInClause(String sql, List<P> inParams, RowMapper<T> rowMapper, JdbcTemplate jdbcTemplate) {
	    Assert.notNull(sql);
	    Assert.isTrue(sql.length() < MAX_SQL_LENGTH);
	    Assert.notNull(rowMapper);
	    Assert.notNull(jdbcTemplate);
	    if (CollectionUtils.isEmpty(inParams)) {
	        return Lists.newArrayList();
	    }
	    // 
	    List<T> result = Lists.newArrayList();
	    String sqlToRun = getInClauseSql(sql, inParams);
	    if (sqlToRun.length() > MAX_SQL_LENGTH) {
	        for (List<P> pList : Lists.partition(inParams, 2)) {
	            result.addAll(queryInClause(sqlToRun, pList, rowMapper, jdbcTemplate));
	        }
	    } else {
	        result.addAll(jdbcTemplate.query(sqlToRun, rowMapper));
	    }
	    return result;
	}

	@Deprecated // NOT TESTED
	public static String getInClauseSql(String sql, List<?> inParams) {
	    Assert.notEmpty(inParams);
	    if (Number.class.isAssignableFrom(inParams.get(0).getClass())) {
	        return String.format(sql, StringUtils.join(inParams, ","));
	    } else {
	        String inClausePart = String.format("'%s'", StringUtils.join(inParams, "','"));
	        return String.format(sql, inClausePart);
	    }
	}
	
	public static boolean isSetMethod(Method method) {
		String methodName = method.getName();
		Class<?>[] parameters = method.getParameterTypes();
		return methodName.startsWith("set") 
				&& ArrayUtils.getLength(parameters) == 1 
				&& (void.class == method.getReturnType()||Void.class == method.getReturnType());
	}

	public static boolean isGetMethod(Method method) {
		String methodName = method.getName();
		Class<?>[] parameters = method.getParameterTypes();
		if (ArrayUtils.getLength(parameters) != 0) {
			return false;
		}
		if (void.class == method.getReturnType() || Void.class == method.getReturnType()) {
			return false;
		}
		if (methodName.startsWith("get")) {
			return true;
		} else if (methodName.startsWith("is") && boolean.class == method.getReturnType()) {
			return true;
		}
		return false;
	}
	
	public static <T> T oneOrNull(Iterable<T> iterable) {
		if (iterable == null) {
			return null;
		}
		// empty
		if (!iterable.iterator().hasNext()) {
			return null;
		}
		return oneNoNull(iterable);
	}
	
	public static <T> T oneNoNull(Iterable<T> iterable) {
		// validate not null
		Assert.notNull(iterable);
		// validate not empty
		Iterator<T> iterator = iterable.iterator();
		Assert.isTrue(iterator.hasNext());
		// Get value
		T t = iterator.next();
		// validate has more than 1 value
		Assert.isTrue(!iterator.hasNext());
		return t;
	}
	
	public static RuntimeException internalError(String message) {
		return new RuntimeException(message);
	}
	
	public static IllegalArgumentException illegalArgument(String message) {
		return new IllegalArgumentException(message);
	}
	
}
