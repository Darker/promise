package io.github.darker.promise.lambda;

import io.github.darker.promise.PromiseChaining;

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
