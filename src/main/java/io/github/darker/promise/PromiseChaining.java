package io.github.darker.promise;

import java.util.ArrayList;
import java.util.List;

import io.github.darker.promise.exception.MultipleResolutionsException;
import io.github.darker.promise.exception.UnhandledRejectionException;

public class PromiseChaining<ThenArgumentType> implements Promise<ThenArgumentType> {
    protected List<PromiseWrapCallback<ThenArgumentType>> thens = new ArrayList<>();
    //protected List<PromiseCallbackCatch<?>> catches;
    
    protected boolean gotResult = false;
    protected boolean rejected = false;
    protected Throwable rejection = null;
    protected ThenArgumentType result = null;
    
    protected void handleResult(ThenArgumentType result) {
    	if(gotResult) {
    		throw new MultipleResolutionsException();
    	}
    	
    	this.result = result;
    	gotResult = true;
    	
    	for(PromiseWrapCallback<ThenArgumentType> promiseWrapper: thens) {
    		promiseWrapper.bubbleReturnValue(result);
    	}
    }
    protected void handleException(Throwable exception) {
    	if(gotResult) {
    		throw new MultipleResolutionsException();
    	}
    	
    	this.gotResult = true;
    	rejected = true;
    	rejection = exception;
    	for(PromiseWrapCallback<ThenArgumentType> promiseWrapper: thens) {
			try {
				promiseWrapper.bubbleException(rejection);
			}
			catch(Throwable e) {
				throw new UnhandledRejectionException(e);
			}
    	}
    }
    protected void passResolvedValue(PromiseChaining<ThenArgumentType> other) {
    	if(gotResult) {
    		if(rejected) {
    			other.handleException(rejection);
    		}
    		else {
    			other.handleResult(result);
    		}
    	}
    }
    protected <ThenReturnValue, WrapPromiseType extends PromiseChaining<ThenReturnValue>&PromiseWrapCallback<ThenArgumentType>> void addOrResolve(WrapPromiseType newPromise) {
		if(gotResult) {
    		resolveSubPromise(newPromise);
		}
		else {
			thens.add(newPromise);
		}
		
    }
    protected <ThenReturnValue> void resolveSubPromise(PromiseWrapCallback<ThenArgumentType> subPromise) {
		if(gotResult) {
    		if(rejected) {
    			try {
    				subPromise.bubbleException(rejection);
    			}
    			catch(Throwable e) {
    				throw new UnhandledRejectionException(e);
    			}
    		}
    		else {
    			subPromise.bubbleReturnValue(result);
    		}
		}
    }
    
	@Override
	public <T> Promise<T> then(PromiseCallbackThen<ThenArgumentType, T> cb) {
		final PromiseWrapCallbackThen<ThenArgumentType, T> newPromise = new PromiseWrapCallbackThen<>(cb);
		addOrResolve(newPromise);
		
		return newPromise;
	}
	@Override
	public Promise<ThenArgumentType> catchException(PromiseCallbackCatch<ThenArgumentType> catchHandler) {
		final PromiseWrapCallbackCatch<ThenArgumentType> newPromise = new PromiseWrapCallbackCatch<>(catchHandler);
		addOrResolve(newPromise);
		
		return newPromise;
	}
	@Override
	public <T> Promise<T> thenAsync(PromiseCallbackThen<ThenArgumentType, Promise<T>> cb) {
		final Promise<Promise<T>> nestedPromise = then(cb);
		return new PromiseWrapPromise<>(nestedPromise);
	}
	


}
