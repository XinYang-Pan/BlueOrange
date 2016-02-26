package incubation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;

public class For<T> {
	enum Action{Break, Continue}
	enum Type{first, last, all, other}
//	private Iterable<T> iterable;
	private T[] array;
	private Map<Type, List<Function<Integer, Action>>> map = new HashMap<>();
	
//	public static<T> For<T> each(Iterable<T> iterable) {
//		For<T> f = new For<T>();
//		f.iterable = iterable;
//		return f;
//	}
	
	public static<T> For<T> each(T[] array) {
		For<T> f = new For<T>();
		f.array = array;
		return f;
	}
	
	public For<T> firstItem(Function<Integer, Action> function) {
		addFunction(function, Type.first);
		return this;
	}

	public For<T> addFunction(Function<Integer, Action> function, Type type) {
		List<Function<Integer, Action>> list= map.get(type);
		if (list == null) {
			list = new ArrayList<>();
			map.put(type, list);
		}
		list.add(function);
		return this;
	}

	public For<T> addFunction(Function<Integer, Action> function, Type ... types) {
		if (array == null || array.length == 0) {
			return this;
		}
		for (Type type : types) {
			this.addFunction(function, type);
		}
		return this;
	}
	
//	private void executeIt() {
//		if (array == null || array.length == 0) {
//			return;
//		}
//		forLoop:
//		for (int i = 0; i < array.length; i++) {
//			Action action = execute(i, array.length);
//			if (action != null) {
//				switch (action) {
//				case Break:
//					break forLoop;
//				case Continue:
//					continue forLoop;
//				default:
//					break;
//				}
//			}
//		}
//	}
//	
//	private void executeArray() {
//		if (array == null || array.length == 0) {
//			return;
//		}
//		forLoop:
//		for (int i = 0; i < array.length; i++) {
//			Action action = execute(i, array.length);
//			if (action != null) {
//				switch (action) {
//				case Break:
//					break forLoop;
//				case Continue:
//					continue forLoop;
//				default:
//					break;
//				}
//			}
//		}
//	}

	public Action execute(int index, int length) {
		List<Function<Integer, Action>> list= map.get(Type.first);
		if (index == 0) {
			Action execute = execute(list, index);
			if (execute != null) {
				return execute;
			}
		}
		list= map.get(Type.last);
		if (index == array.length - 1) {
			Action execute = execute(list, index);
			if (execute != null) {
				return execute;
			}
		} else {
			Action execute = execute(list, index);
			if (execute != null) {
				return execute;
			}
		}
		return null;
	}

	public Action execute(List<Function<Integer, Action>> list, int index) {
		if (list == null || list.size() == 0) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			Function<Integer, Action> f = list.get(i);
			Action action = f.apply(index);
			switch (action) {
			case Break:
			case Continue:
				return action;
			default:
				break;
			}
		}
		return null;
	}
}
