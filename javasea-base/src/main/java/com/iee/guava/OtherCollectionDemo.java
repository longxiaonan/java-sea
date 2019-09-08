package com.iee.guava;

import com.google.common.collect.*;
import com.google.common.collect.Sets.SetView;

import java.util.*;

public class OtherCollectionDemo {
	public static void main(String[] args) {
		multiSetDemo();
		multiMapDemo();
		bitMapDemo();
		tableDemo();

		/** Set并集,交集,差集 */
		HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
		HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);
		SetView<Integer> union = Sets.union(setA, setB);
		System.out.println("union:");
		for (Integer integer : union)
			System.out.println(integer); // union:12345867
		SetView<Integer> difference = Sets.difference(setA, setB);
		System.out.println("difference:");
		for (Integer integer : difference)
			System.out.println(integer); // difference:123
		SetView<Integer> intersection = Sets.intersection(setA, setB);
		System.out.println("intersection:");
		for (Integer integer : intersection)
			System.out.println(integer); // intersection:45

		/** map并集,交集,差集 */
		Map<String,String> mapA = Maps.newHashMap();
		Map<String,String> mapB = Maps.newHashMap();
		mapA.put("aa", "123");
		mapA.put("ab", "442");
		mapA.put("ac", "1212313");
		mapA.put("ad", "11123");

//		mapB.put("ac", "1212313");//和mapA的"ac"相等,注意differences的区别
		mapB.put("ac", "23sgw");//和mapA的"ac"不相等,注意differences的区别
		mapB.put("ad", "fiow");
		mapB.put("ae", "ffweq");
		mapB.put("af", "232");
		mapB.put("ag", "herw");

		// mapB和mapA的"ac"相等时, differenceMap为not equal: only on left={aa=123, ab=442}: only on right={ae=ffweq, af=232, ag=herw}: value differences={ad=(11123, fiow)}
		// mapB和mapA的"ac"不相等时, differenceMap为not equal: not equal: only on left={aa=123, ab=442}: only on right={ae=ffweq, af=232, ag=herw}: value differences={ac=(1212313, 23sgw), ad=(11123, fiow)}
		MapDifference<String,String> differenceMap = Maps.difference(mapA, mapB);
		differenceMap.areEqual();
		Map entriesDiffering = differenceMap.entriesDiffering();
		Map entriesOnlyOnLeft = differenceMap.entriesOnlyOnLeft();
		Map entriesOnlyOnRight = differenceMap.entriesOnlyOnRight();
		Map entriesInCommon = differenceMap.entriesInCommon();
		System.out.println("werwe");
	}

	private static void tableDemo() {
		Table<String, String, Double> table = HashBasedTable.create();
		table.put("a", "b", 4.0);// 第a行, b列的值为4.0
		table.put("a", "c", 20.0);// 第a行, c列的值为20.0
		table.put("b", "c", 5.0);// 第b行, c列的值为5.0
		System.out.println(table.row("a")); // {b=4.0, c=20.0}
		System.out.println(table.column("c")); // {a=20.0, b=5.0}

	}

	/** BiMap（键值双向映射的Map,键和值都能重复） */
	private static void bitMapDemo() {
		// 1.传统方式
		Map<String, Integer> nameToId = Maps.newHashMap();
		Map<Integer, String> idToName = Maps.newHashMap();
		nameToId.put("Bob", 42);
		idToName.put(42, "Bob");
		// 为了反向取值，要使用第2个Map。如果代码有几百行，谁还能记住这个Map
		idToName.get(42);

		// 2.bitMap
		BiMap<String, Integer> user_id = HashBiMap.create();
		user_id.put("Bob", 42);
		// 反向取值，仍然使用这个Map就可以
		String userName = user_id.inverse().get(42);
		BiMap<Integer, String> id_user = user_id.inverse();
		id_user.put(58, "alex");// 原user_id这个map同步生效
	}

	/**
	 * 在日常的开发工作中，我们有的时候需要构造像Map<K,List<V>>或者Map<K, Set
	 * <V>>这样比较复杂的集合类型的数据结构，以便做相应的业务逻辑处理。这种代码在编写、阅读、修改等各个方面都是极其痛苦的。
	 *
	 * 比如，我们想在Map里面以姓名为KEY，把学生的每门课的成绩保存起来，代码会写成这样：
	 *
	 * Map<String, List<StudentScore>> StudentScoreMap = new HashMap<String,
	 * List<StudentScore>>();
	 *
	 * List<StudentScore> listScore = new ArrayList< StudentScore>();
	 *
	 * listScore.add(StudentScore1);
	 *
	 * listScore.add(StudentScore2);
	 *
	 * StudentScoreMap.put("peter", listScore);
	 */
	private static void multiMapDemo() {
		Multimap<String, StudentScore> scoreMultimap = ArrayListMultimap.create();
		scoreMultimap.put("peter", new StudentScore(20, 80, 77));
		scoreMultimap.put("peter", new StudentScore(99, 88, 77));
		System.out.println(scoreMultimap);// {peter=[guava.StudentScore@cac736f,
											// guava.StudentScore@5e265ba4]}
		Collection<StudentScore> collection = scoreMultimap.get("peter");// 返回的是List
		System.out.println(collection);

		Multimap<String, StudentScore> scoreMultimap2 = HashMultimap.create();
		scoreMultimap2.put("peter", new StudentScore(20, 80, 77));
		scoreMultimap2.put("peter", new StudentScore(99, 88, 77));
		System.out.println(scoreMultimap2);// {peter=[guava.StudentScore@cac736f,
											// guava.StudentScore@5e265ba4]}
		Collection<StudentScore> collection2 = scoreMultimap2.get("peter");// 返回的是Set
	}

	private static void multiSetDemo() {
		Map<String, Integer> counts = new HashMap<String, Integer>();
		String[] wordsArr = { "a", "b", "a", "c", "a", "b", "c", "c" };
		ArrayList<String> words = Lists.newArrayList(wordsArr);
		/** 统计某个词出现的次数: 方式一 */
		for (String word : words) {
			Integer count = counts.get(word);
			if (count == null) {
				counts.put(word, 1);
			} else {
				counts.put(word, count + 1);
			}
		}
		System.out.println(counts);// {a=3, b=2, c=3}

		/** 统计某个词出现的次数: 方式二 */
		Multiset<String> wordsMultiset = HashMultiset.create();
		wordsMultiset.addAll(words);
		System.out.println(wordsMultiset);// [a x 3, b x 2, c x 3]
	}
}

class StudentScore {
	Integer yuwen = 90;
	Integer shuxue = 80;
	Integer wuli = 88;

	public StudentScore(Integer yuwen, Integer shuxue, Integer wuli) {
		super();
		this.yuwen = yuwen;
		this.shuxue = shuxue;
		this.wuli = wuli;
	}
}
