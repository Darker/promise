package cz.techsys.dialog.pla.shared.promise;

class PromiseWrapCallbackThen<ThenArgumentType, ThenReturnValue> 
extends PromiseChaining<ThenReturnValue> 
implements PromiseWrapCallback<ThenArgumentType> 
{
    protected PromiseCallbackThen<ThenArgumentType,ThenReturnValue> thenCallback;
    
	public PromiseWrapCallbackThen(PromiseCallbackThen<ThenArgumentType, ThenReturnValue> thenCallback) {
		super();
		this.thenCallback = thenCallback;
	}

	@Override
	public void bubbleReturnValue(ThenArgumentType result) {
		try {
			final ThenReturnValue processedResult = thenCallback.processValue(result);
			this.handleResult(processedResult);
		}
		catch(Throwable e) {
			this.handleException(e);
		}
	}

	@Override
	public <TExc extends Throwable> void bubbleException(TExc e) throws TExc {
		this.handleException(e);
	}
}
