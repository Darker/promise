package io.github.darker.promise.multi;

import io.github.darker.promise.Promise;

public interface MultiPromiseThen2<T1, T2> extends Promise<MultiResult2<T1, T2>> {
	public static interface MultiCallback2<T1, T2, ReturnType> {
	    ReturnType processValue(T1 value, T2 value2);
	}
	
	public default <ReturnType> Promise<ReturnType> then(MultiCallback2<T1, T2, ReturnType> cb) {
		return this.then((result)->{return cb.processValue(result.getRes1(), result.getRes2());});
	}
}
