package io.github.darker.promise.multi;

import io.github.darker.promise.Promise;
import io.github.darker.promise.PromiseChaining;

public class PromiseMultiPromise3<T1, T2, T3> extends PromiseChaining<MultiResult3<T1, T2, T3>> {
	
	protected MultiResult<T1, T2, T3,Void> result = MultiResult.create3();
	public PromiseMultiPromise3(Promise<T1> p1, Promise<T2> p2, Promise<T3> p3) {
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
		p3.then((result3)->{
			if(!rejected) {
				result.setRes3(result3);
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
