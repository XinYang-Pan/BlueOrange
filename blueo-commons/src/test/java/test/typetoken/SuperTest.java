package test.typetoken;


public class SuperTest {
	
	public static <T> void main(String[] args) {
		Child child = new Child();
		System.out.println(child.getParameterizedClass());
//		Super<T> s = new Super<T>();
//		System.out.println(s.getParameterizedClass());
	}
	
}
