package io.github.darker.promise;

import java.util.List;

import io.github.darker.promise.multi.MultiPromiseThen2;
import io.github.darker.promise.multi.MultiResult3;
import io.github.darker.promise.multi.PromiseMultiPromise2;
import io.github.darker.promise.multi.PromiseMultiPromise3;

public interface Promise<ThenArgumentType> {
	/**
	 * Takes a callback, which will be called once the promise's value has been resolved.
	 * 
	 * Returns another promise, which will resolve once the given callback was called and has returned a value.
	 * 
	 * If callback returns another promise, that promise will be returned as any other value would. For
	 * nesting promises, see {@link Promise#thenAsync}.
	 * 
	 * <h1>Multithreading</h1>
	 * In multithreaded context make sure of using synchronization properly. 
	 * <ul>
	 *     <li>If the promise evaluation runs in the same thread as the <tt>then()</tt> call,
	 *         then the callback SHOULD be executed in the same thread also.
	 *     </li>
	 *     <li>
	 *         If not, it is NOT DEFINED which thread will execute the callback. Whenever threads are involved,
	 *         you SHOULD use your own synchronization.
	 *     </li>
	 * </ul>
	 * @param cb
	 * @return
	 */
	public <T> Promise<T> then(PromiseCallbackThen<ThenArgumentType, T> cb);
	/**
	 * If you need to nest promises, this method will make it easier, by resolving a promise you return internally.
	 * 
	 * This is useful to avoid indent hell with many nested promises, since the value that resolves from 
	 * the promise you return from callback will appear at the same level as where the callback was assigned.
	 * 
	 * <h1>Example</h1>
     * <pre>
     * {@code
     *  Promise<String> test = (new PromiseFromCallback<>((resolver)->{
     *  	resolver.resolve("A");
     *  }))
     *  .thenAsync((textA)->{
     *  	return (new PromiseFromCallback<String>((resolver)->{
     *  		resolver.resolve(textA+"B");
     *  	}));
     *  })
     *  .then((textAB)->{
     *      System.out.println(textAB); // Prints "AB"
     *  });
     * }
     * </pre>
	 * @param cb
	 * @return
	 */
	public default <T> Promise<T> thenAsync(PromiseCallbackThen<ThenArgumentType, Promise<T>> cb) {
		final Promise<Promise<T>> nestedPromise = then(cb);
		return new PromiseWrapPromise<>(nestedPromise);
	}
	
	/**
	 * Convenience method for callbacks that return Void.
	 * For complete documentation see {@link Promise#then}.
	 * @param cb
	 * @return
	 */
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
    
    /**
     * UTILITY METHODS 
     * */
    /**
     * Maps all given promises to one promise that resolves with all values once they are available.
     * 
     * If any sub-promise rejects, the mass promise is also rejected and any further values or exceptions
     * are ignored.
     * @param promises
     * @return
     */
    @SafeVarargs
	public static <T> Promise<List<T>> all(Promise<T>... promises) {
    	return new PromiseWrapPromises<>(promises);
    }
    /**
     * Utility method to merge two return values into one. Much like {@link #all(Promise...) Promise.all}
     * this promise rejects if any of the values rejects.
     * @param p1
     * @param p2
     * @return
     */
    public static <T1,T2> MultiPromiseThen2<T1,T2> multi(Promise<T1> p1, Promise<T2> p2) { 
    	return new PromiseMultiPromise2<>(p1, p2);
    }
    public static <T1,T2,T3> Promise<MultiResult3<T1,T2,T3>> multi(Promise<T1> p1, Promise<T2> p2, Promise<T3> p3) {
    	return new PromiseMultiPromise3<>(p1, p2, p3);
    }
}
