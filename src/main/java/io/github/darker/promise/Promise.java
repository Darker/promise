package io.github.darker.promise;

import java.util.List;

import io.github.darker.promise.lambda.PromiseFromCallback;
import io.github.darker.promise.multi.MultiPromiseThen2;
import io.github.darker.promise.multi.MultiResult2;
import io.github.darker.promise.multi.MultiResult3;
import io.github.darker.promise.multi.PromiseMultiPromise2;
import io.github.darker.promise.multi.PromiseMultiPromise3;

public interface Promise<ThenArgumentType> {
	public <T> Promise<T> then(PromiseCallbackThen<ThenArgumentType, T> cb);
	// shorthand to resolving nested promises
	public default <T> Promise<T> thenAsync(PromiseCallbackThen<ThenArgumentType, Promise<T>> cb) {
		final Promise<Promise<T>> nestedPromise = then(cb);
		return new PromiseWrapPromise<>(nestedPromise);
	}
	
	public default Promise<Void> then(PromiseCallbackThen.VoidReturn<ThenArgumentType> cb) {
		return then((PromiseCallbackThen<ThenArgumentType, Void>)cb);
	}
	
	/**
	 * Catch handlers must return the same value as the promise above them would.
	 * @param catchHandler
	 * @return
	 */
    public Promise<ThenArgumentType> catchException(PromiseCallbackCatch<ThenArgumentType> catchHandler);
    public default Promise<ThenArgumentType> catchException(PromiseCallbackCatch.IgnoreReturn<ThenArgumentType> catchHandler) {
    	return catchException((PromiseCallbackCatch<ThenArgumentType>)catchHandler);
    }
    public static void syntaxTest() {
    	final Promise<String> test = new PromiseFromCallback<>((res)-> {
    		
    	});
    	test.then((testStringRes)->{return null;});
    }
    
    
    
    /**
     * UTILITY METHODS 
     * */
    @SafeVarargs
	public static <T> Promise<List<T>> all(Promise<T>... promises) {
    	return new PromiseWrapPromises<>(promises);
    }
    
    public static <T1,T2> MultiPromiseThen2<T1,T2> multi(Promise<T1> p1, Promise<T2> p2) { 
    	return new PromiseMultiPromise2<>(p1, p2);
    }
    public static <T1,T2,T3> Promise<MultiResult3<T1,T2,T3>> multi(Promise<T1> p1, Promise<T2> p2, Promise<T3> p3) {
    	return new PromiseMultiPromise3<>(p1, p2, p3);
    }
}
