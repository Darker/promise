package cz.techsys.dialog.pla.shared.promise;

public class PromiseWrapCallbackCatch<ThenArgumentType> 
extends PromiseChaining<ThenArgumentType> 
implements PromiseWrapCallback<ThenArgumentType> 
{
    protected PromiseCallbackCatch<ThenArgumentType> catchCallback;
    
	public PromiseWrapCallbackCatch(PromiseCallbackCatch<ThenArgumentType> catchCallback) {
		super();
		this.catchCallback = catchCallback;
	}

	@Override
	public void bubbleReturnValue(ThenArgumentType result) {
		handleResult(result);
	}

	@Override
	public <TExc extends Throwable> void bubbleException(TExc e) throws TExc {
		if(catchCallback == null)
			throw e;
		
		try {
			final ThenArgumentType catchReturn = catchCallback.catchException(e);
			handleResult(catchReturn);
		}
		catch(Throwable rethrownException) {
			handleException(rethrownException);
		}
	}
}
