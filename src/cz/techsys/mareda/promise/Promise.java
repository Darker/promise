package cz.techsys.dialog.pla.shared.promise;

import cz.techsys.dialog.pla.shared.promise.lambda.PromiseFromCallback;

public interface Promise<ThenArgumentType> {
	public <T> Promise<T> then(PromiseCallbackThen<ThenArgumentType, T> cb);
	// shorthand to resolving nested promises
	public <T> Promise<T> thenAsync(PromiseCallbackThen<ThenArgumentType, Promise<T>> cb);
	/**
	 * Catch handlers must return the same value as the promise above them would.
	 * @param catchHandler
	 * @return
	 */
    public Promise<ThenArgumentType> catchException(PromiseCallbackCatch<ThenArgumentType> catchHandler);
    
    public static void syntaxTest() {
    	final Promise<String> test = new PromiseFromCallback<>((res)-> {
    		
    	});
    	test.then((testStringRes)->{return null;});
    }
}
