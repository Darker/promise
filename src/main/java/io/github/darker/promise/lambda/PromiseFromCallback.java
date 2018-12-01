package io.github.darker.promise.lambda;

import io.github.darker.promise.PromiseChaining;
/**
 * This is the most typical Promise type. It will resolve or reject based on what your callback does.
 * 
 * Remember that callback of this promise is called in the same thread as the constructor.
 * @author mareda
 *
 * @param <ThenArgumentType>
 */
public class PromiseFromCallback<ThenArgumentType> 
extends PromiseChaining<ThenArgumentType>
implements ResolveRejectFunctions<ThenArgumentType> 
{
	public PromiseFromCallback(ResolveRejectCallback<ThenArgumentType> cb) {
		super();
		try {
			cb.initPromise(this);
		}
		catch(Throwable e) {
			handleException(e);
		}
	}

	@Override
	public void resolve(ThenArgumentType returnValue) {
		handleResult(returnValue);
	}

	@Override
	public void reject(Throwable e) {
		handleException(e);
	}


}
