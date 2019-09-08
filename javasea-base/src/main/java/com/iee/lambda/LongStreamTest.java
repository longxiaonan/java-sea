package com.iee.lambda;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LongStreamTest {
	public static void main(String[] args) {
		String prefix = "E";
		long baseNum = 201802260011L;
		List<String> collect = LongStream.iterate(baseNum, e -> (long)(e + 1))
			.limit(30)
			.mapToObj(e -> prefix + e)
			.collect(Collectors.toList());
		System.out.println(collect);
	}
}
