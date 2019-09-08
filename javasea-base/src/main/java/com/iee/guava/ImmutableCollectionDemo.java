package com.iee.guava;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;

import java.awt.*;
import java.util.Set;

/**
 * 不可变集合是不可被修改的集合。集合的数据项是在创建的时候提供，并且在整个生命周期中都不可改变。
 *
 * 不可变集合是极其有益的一种存储数据的形式，请仔细思考你的程序，把一次创建多次读取的数据用不可变集合来承载。好处是：
 *
 * ü 不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率
 *
 * ü 不可变对象被多个线程调用时，不存在竞态条件问题
 *
 * ü 不可变对象因为有固定不变，可以作为常量来安全使用
 *
 * ü 创建对象的不可变拷贝是一项很好的防御性编程技巧
 *
 * 重要提示：Guava不可变集合的实现都不接受null值。
 *
 * 不可变的集合包括如下:
 * ImmutableList、ImmutableSet、ImmutableSortedSet、ImmutableMap、ImmutableSortedMap
 * 、ImmutableMultiset、ImmutableSortedMultiset、ImmutableMultimap、
 * ImmutableListMultimap、ImmutableSetMultimap、ImmutableBiMap、ImmutableTable、
 * ImmutableClassToInstanceMap、ImmutableCollection
 *
 * @author longxn
 *
 */
public class ImmutableCollectionDemo {

	// 3.创建方式三
	public static final ImmutableSet<Color> GOOGLE_COLORS = ImmutableSet.<Color> builder().add(Color.BLACK)
			.add(new Color(0, 191, 255)).build();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Set set = Sets.newHashSet();
		set.add("aa");
		set.add("bb");
		// 1.创建方式一
		ImmutableSet set2 = ImmutableSet.copyOf(set);
		System.out.println(set2);
		// 2.创建方式二
		ImmutableMap<String, Integer> of = ImmutableMap.of("a", 1, "b", 2);
		System.out.println(of);

		// 对有序不可变集合来说，排序是在构造集合的时候完成的, 会在构造时就把元素排序为a, b, c, d
		ImmutableSortedSet<String> of2 = ImmutableSortedSet.of("a", "b", "c");
		System.out.println(of2);
	}
}
