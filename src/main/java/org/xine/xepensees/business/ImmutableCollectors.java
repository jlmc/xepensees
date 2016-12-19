package org.xine.xepensees.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ImmutableCollectors {
	
	public static <T> Collector<T, List<T>, List<T>> toImmutableList() {
		return toImmutableList(ArrayList::new);
	}

	public static <T, A extends List<T>> Collector<T, A, List<T>> toImmutableList(Supplier<A> collectionFactory) {
		return Collector.of(collectionFactory, List::add, (left, right) -> {
			left.addAll(right);
			return left;
		}, Collections::unmodifiableList);
	}

	public static <T> Collector<T, Set<T>, Set<T>> toImmutableSet() {
		return toImmutableSet(HashSet::new);
	}

	public static <T, A extends Set<T>> Collector<T, A, Set<T>> toImmutableSet(Supplier<A> collectionFactory) {
		return Collector.of(collectionFactory, Set::add, (left, right) -> {
			left.addAll(right);
			return left;
		}, Collections::unmodifiableSet);
	}

	public static <T, K, U> Collector<T, ?, Map<K, U>> toImmutableMap(Function<? super T, ? extends K> keyMapper,
			Function<? super T, ? extends U> valueMapper) {
		return Collectors.collectingAndThen(Collectors.toMap(keyMapper, valueMapper),
				values -> Collections.unmodifiableMap(values));
	}
}
