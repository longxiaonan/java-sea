package com.iee.sort;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SortDemo {
	public static void main(String[] args) {
		/** TreeSet通过实现compareable接口, 重写compareTo方法实现 */
		Girl1 g1 = new Girl1("aaa", 120);
		Girl1 g2 = new Girl1("aa", 130);
		Girl1 g3 = new Girl1("aaff", 140);
		int compareTo = g2.compareTo(g1);
		System.out.println(compareTo);
		TreeSet<Girl1> ts1 = new TreeSet<>();
		ts1.add(g1);
		ts1.add(g3);
		ts1.add(g2);
		System.out.println(ts1);

		/** TreeSet通过Comparetor比较器实现 */
		Girl2 g11 = new Girl2("aa", 120);
		Girl2 g22 = new Girl2("ab", 130);
		Girl2 g33 = new Girl2("ac", 140);
		final Comparator<String> strLengthComparator = new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				// return str1.compareTo(str2);//正序
				return str2.compareTo(str1);// 倒序
			}
		};
		TreeSet<Girl2> ts2 = new TreeSet<>(new Comparator<Girl2>() {
			@Override
			public int compare(Girl2 g1, Girl2 g2) {
				// 谷歌ComparisonChain实现多项属性比较
				return ComparisonChain.start() // 链式比较,在第一个非0处返回
						.compare(g1.getName(), g2.getName(), Ordering.from(strLengthComparator))
						// .compare(girl.getHeight(),this.height)//倒序
						.compare(g1.getHeight(), g2.getHeight())// 正序
						.result();
			}
		});
		ts2.add(g11);
		ts2.add(g33);
		ts2.add(g22);
		System.out.println(ts2);

		/** list实现排序*/
		ArrayList<Girl2> list = Lists.newArrayList(g11, g33, g22);
		list.sort(new Comparator<Girl2>() {
			@Override
			public int compare(Girl2 g1, Girl2 g2) {
				// TODO Auto-generated method stub
				return ComparisonChain.start() // 链式比较,在第一个非0处返回
						.compare(g1.getName(), g2.getName(), Ordering.from(strLengthComparator))
						// .compare(girl.getHeight(),this.height)//倒序
						.compare(g1.getHeight(), g2.getHeight())// 正序
						.result();
			}
		});

		/** lambda实现list排序*/
		Comparator<Girl2> c = (o, p) -> o.getName().compareTo(p.getName());
		List<Girl2> collect = list.stream().sorted((o, p) -> o.getName().compareTo(p.getName()))
				.collect(Collectors.toList());
		System.out.println("collect>>>"+collect);

		List<Girl2> collect2 = list.stream().sorted((o, p) -> {
			return ComparisonChain.start() // 通过ComparisonChain来链式比较,在第一个非0处返回
					.compare(o.getName(), p.getName(), Ordering.from(strLengthComparator))
					// .compare(girl.getHeight(),this.height)//倒序
					.compare(o.getHeight(), p.getHeight())// 正序
					.result();
		}).collect(Collectors.toList());
		System.out.println("collect2>>>"+collect2);
	}
}
