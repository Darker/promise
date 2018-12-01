package io.github.darker.promise;

public class PromiseWrapPromise<ThenArgumentType, ThenReturnValue> 
extends PromiseChaining<ThenReturnValue> 
{
	public PromiseWrapPromise(Promise<Promise<ThenReturnValue>> nestedPromise) {
		super();
		nestedPromise.then((subPromise)->{
			subPromise.then((value)->{
				PromiseWrapPromise.this.handleResult(value);
			})
            .catchException((exc)->{
            	PromiseWrapPromise.this.handleException(exc);
            });
			return null;
		})
        .catchException((exc)->{
        	PromiseWrapPromise.this.handleException(exc);
        });
	}

}
