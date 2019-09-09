package com.iee.guava;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.iee.common.entity.User;

/**排序器[Ordering]是Guava流畅风格比较器[Comparator]的实现，它可以用来为构建复杂的比较器，以完成集合排序的功能。
 *
	natural()	对可排序类型做自然排序，如数字按大小，日期按先后排序
	usingToString()	按对象的字符串形式做字典排序[lexicographical ordering]
	from(Comparator)	把给定的Comparator转化为排序器
	reverse()	获取语义相反的排序器
	nullsFirst()	使用当前排序器，但额外把null值排到最前面。
	nullsLast()	使用当前排序器，但额外把null值排到最后面。
	compound(Comparator)	合成另一个比较器，以处理当前排序器中的相等情况。
	lexicographical()	基于处理类型T的排序器，返回该类型的可迭代对象Iterable<T>的排序器。
	onResultOf(Function)	对集合中元素调用Function，再按返回值用当前排序器排序。
 * */
public class OrderingDemo {
	public static void main(String[] args) {

		User person = new User("aa", 14); // String name ,Integer age
		User ps = new User("bb", 13);
		Ordering<User> byOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<User, String>() {
			public String apply(User person) {
				return person.getAge().toString();
			}
		});
		byOrdering.compare(person, ps);
		System.out.println(byOrdering.compare(person, ps)); // 1 person的年龄比ps大
															// 所以输出1
	}
}
