package io.github.darker.promise;

import java.util.ArrayList;
import java.util.List;

public class PromiseWrapPromises<ThenReturnValue> 
extends PromiseChaining<List<ThenReturnValue>> 
{
	//protected final Promise<ThenReturnValue>[] nestedPromises;
    int resultCount = 0;
    protected final List<ThenReturnValue> resultArray;


	@SafeVarargs
	public PromiseWrapPromises(Promise<ThenReturnValue>... nestedPromises) {
		super();
		//this.nestedPromises = nestedPromises;
		resultArray = new ArrayList<ThenReturnValue>(nestedPromises.length);
		final int l = nestedPromises.length;
		for(int i=0; i<l; ++i) {
			final Promise<ThenReturnValue> nestedPromise = nestedPromises[i];
			final int resultIndex = i;
			resultArray.add(null);
			
			nestedPromise.then((result)->{
				if(!rejected) {
					resultArray.set(resultIndex, result);
					++resultCount;
					if(resultCount>=l) {
						handleResult(resultArray);
					}
				}
			})
	        .catchException((exc)->{
				if(!rejected) {
					this.handleException(exc);
					resultArray.clear();
				}
	        });
		}

	}

}
