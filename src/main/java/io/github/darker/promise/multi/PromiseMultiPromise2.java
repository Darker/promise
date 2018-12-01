package io.github.darker.promise.multi;

import io.github.darker.promise.Promise;
import io.github.darker.promise.PromiseChaining;

public class PromiseMultiPromise2<T1,T2> extends PromiseChaining<MultiResult2<T1, T2>> implements MultiPromiseThen2<T1, T2> {
	
	protected MultiResult<T1,T2,Void,Void> result = MultiResult.create2();
	public PromiseMultiPromise2(Promise<T1> p1, Promise<T2> p2) {
		super();
		p1.then((result1)->{
			if(!rejected) {
				result.setRes1(result1);
				if(result.allSet()) {
					handleResult(result);
				}
			}
		})
		.catchException((exc)->{
			handleException(exc);
		});
		
		p2.then((result2)->{
			if(!rejected) {
				result.setRes2(result2);
				if(result.allSet()) {
					handleResult(result);
				}
			}
		})
		.catchException((exc)->{
			handleException(exc);
		});
	}
	
}
