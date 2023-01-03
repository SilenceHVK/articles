package me.hvkcoder.java_basic.java8;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收集器 是一种通用的、从流生成复杂值得结构。只要将它传给 collect 方法，所有的流就都可以使用它。
 *
 * @author h-vk
 * @since 2020/8/16
 */
public class CollectorsUsage {

	public static void main(String[] args) {
		List<Singer> singers =
			Arrays.asList(
				new Singer("周杰伦", true, Arrays.asList("Jay", "范特西", "八度空间")),
				new Singer("许嵩", true, Arrays.asList("自定义", "寻雾启示")),
				new Singer("五月天", false, List.of("诺亚方舟")),
				new Singer("苏打绿", false, List.of("小情歌")),
				new Singer("汪苏泷", true, List.of("克制凶猛")));
		/**
		 * Collectors.partitioningBy 收集器，它接受一个流，并将其分成两部分
		 *
		 * <p>它使用 Predicate 对象判断一个元素应该属于哪个部分，并根据布尔值返回一个 Map 到列表
		 */
		Map<Boolean, List<Singer>> partition =
			singers.stream().collect(Collectors.partitioningBy(Singer::isSolo));
		partition.get(true).forEach(singer -> System.out.println(singer.getName()));

		/**
		 * Collectors.groupingBy 收集器，它接受一个分类函数，用来对数据分组，
		 *
		 * <p>它与 partitioningBy 用法类似，但是可以自定义 Map key 的类型
		 */

		/** Collectors.joining 收集器，可以从一个流得到一个字符串，允许用户提供 分隔符、前缀、后缀 */
		String singerNames =
			singers.stream().map(Singer::getName).collect(Collectors.joining(",", "[", "]"));
		System.out.println(singerNames);

		/** 下游收集器，在一个收集器中使用的第二个收集器，用以收集最终结果的一个子集 */
		Map<String, List<String>> collect =
			singers.stream()
				.collect(
					Collectors.groupingBy(
						Singer::getName, Collectors.mapping(Singer::getName, Collectors.toList())));
		System.out.println(collect);

		/**
		 * Collectors.flatMapping 可以认为是将多个 List 进行合并展开
		 */
		Map<Boolean, List<String>> alubums = singers.stream().collect(
			Collectors.groupingBy(Singer::isSolo, Collectors.flatMapping(values -> values.albums.stream(), Collectors.toList()))
		);
		System.out.println(alubums);
	}

	@Data
	@AllArgsConstructor
	public static class Singer {
		private String name;
		private boolean isSolo;
		private List<String> albums;
	}
}
