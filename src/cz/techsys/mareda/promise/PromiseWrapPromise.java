package cz.techsys.dialog.pla.shared.promise;

public class PromiseWrapPromise<ThenArgumentType, ThenReturnValue> 
extends PromiseChaining<ThenReturnValue> 
{
    protected Promise<ThenReturnValue> thenCallback;
    
	public PromiseWrapPromise(Promise<Promise<ThenReturnValue>> nestedPromise) {
		super();
		nestedPromise.then((subPromise)->{
			subPromise.then((value)->{
				PromiseWrapPromise.this.handleResult(value);
				//PromiseWrapPromise.this;
				return null;
			})
            .catchException((exc)->{
            	PromiseWrapPromise.this.handleException(exc);
            	return null;
            });
			return null;
		});
	}

}
