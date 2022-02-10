package me.hvkcoder.java_basic.jvm.reflect;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * 反射：将类的各个组成部分封装为其他对象，这就是反射机制
 *
 * <p>优点： 1. 可以在程序运行中，操作这些对象 2. 可以解耦，提高程序的扩展性
 *
 * <p>同一个字节码文件（*.class）在一次程序运行过程中，只会被加载一次，无论通过哪种方式获取的 Class 对象都是同一个
 *
 * @author h-vk
 * @since 2020/8/23
 */
public class ReflectSample {
	public static void main(String[] args)
		throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
		InvocationTargetException, InstantiationException, NoSuchFieldException {
		/**  加载字节码文件对象，多用于配置文件，将类名定义在配置文件中进行加载 */
		Class<?> aClass = Class.forName("me.hvkcoder.java_basic.jvm.reflect.Robot");

		/**  获取 Class 对象，多用于参数的传递 */
		//    Class<Robot> robotClass = Robot.class;

		/**  获取 Class 对象，多用于对象的获取字节码的方式 */
		//    Robot robot1 = new Robot();
		//    Class<? extends Class> aClass1 = robot1.getClass().getClass();

		// 通过类加载器获取 Class 对象
//		ClassLoader classLoader = ReflectSample.class.getClassLoader();
//		classLoader.loadClass("me.hvkcoder.java_basic.jvm.reflect.Robot");


		/**  获取字节码文件对象的构造器对象 */
		Constructor<?>[] constructors = aClass.getDeclaredConstructors();
		System.out.println(constructors.length);

		/**  获取反射对象实例 */
		Robot robot = (Robot) aClass.getDeclaredConstructor().newInstance();

		/**  获取反射对象除 继承与接口的所有方法 */
		Method sayHello = aClass.getDeclaredMethod("sayHello", String.class);
		sayHello.setAccessible(true);
		Object privateMsg = sayHello.invoke(robot, "hvkcoder");
		System.out.println(privateMsg);

		/**  获取反射除私有的所有方法 */
		Method hi = aClass.getMethod("hi");
		hi.setAccessible(true);
		Object publicMsg = hi.invoke(robot);
		System.out.println(publicMsg);

		/**  获取反射对象 除继承的字段 */
		Field msg = aClass.getDeclaredField("msg");
		msg.setAccessible(true);
		msg.set(robot, "Silence H_VK");
		System.out.println(hi.invoke(robot));

		/**  获取泛型参数类型 */
		Method test = aClass.getMethod("test", Map.class, List.class);
		Type[] genericParameterTypes = test.getGenericParameterTypes();
		for (Type type : genericParameterTypes) {
			if (type instanceof ParameterizedType) {
				// 获取真实数据类型
				Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
				for (Type actualType : actualTypeArguments) {
					System.out.println(actualType);
				}
			}
		}

		/**  获取返回值类型 */
		Type genericReturnType = test.getGenericReturnType();
		System.out.println(genericReturnType);
	}
}
