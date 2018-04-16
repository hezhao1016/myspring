package test;

import com.test.myspring.BeanFactory;

public class Test1 {

	/**≤‚ ‘myspring
	 * @param args
	 */
	public static void main(String[] args) {
		BeanFactory context = new BeanFactory("myspring.xml,myspring-service.xml");
		//Teacher teacher = (Teacher) context.getBean("teacher");
		//Teacher teacher1 = (Teacher) context.getBean("teacher1");
		Student stu = (Student) context.getBean("student");
		System.out.println(stu.getName());
		System.out.println(stu.getAge());
		System.out.println(stu.getScore());
		System.out.println(stu.getTeacher().getName());
		System.out.println(stu.getTeacher().getBook().getName());
	}

}
