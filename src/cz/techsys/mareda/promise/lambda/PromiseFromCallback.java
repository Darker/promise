package cz.techsys.dialog.pla.shared.promise.lambda;

import cz.techsys.dialog.pla.shared.promise.Promise;
import cz.techsys.dialog.pla.shared.promise.PromiseCallbackCatch;
import cz.techsys.dialog.pla.shared.promise.PromiseCallbackThen;

public class PromiseFromCallback<ThenArgumentType> 
implements Promise<ThenArgumentType>, ResolveRejectFunctions<ThenArgumentType> 
{
    protected boolean gotResult = false;
    protected boolean rejected = false;
    protected Throwable rejection = null;
    protected ThenArgumentType result = null;
	
    public PromiseFromCallback(ResolveRejectCallback<ThenArgumentType> cb) {
		
	}

	@Override
	public void resolve(ThenArgumentType returnValue) {
		result = returnValue;
		gotResult = true;
	}

	@Override
	public void reject(Throwable e) {
		rejection = e;
		rejected = true;
		gotResult = true;		
	}


	@Override
	public <T> Promise<T> then(PromiseCallbackThen<ThenArgumentType, T> cb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Promise<T> catchException(PromiseCallbackCatch<T> catchHandler) {
		// TODO Auto-generated method stub
		return null;
	}

}
