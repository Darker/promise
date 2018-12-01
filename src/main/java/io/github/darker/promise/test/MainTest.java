package io.github.darker.promise.test;

import java.util.Date;

import io.github.darker.promise.Promise;
import io.github.darker.promise.lambda.PromiseFromCallback;

public class MainTest {

	public static final boolean TRUE_VALUE = true;
	public static void main(String[] args) {
		
		Promise<String> test = new PromiseFromCallback<>((resolver)->{
			resolver.resolve("test");
		});
		test.then((result)->{
			System.out.println("Result 1: "+result);
			return 5;
		})
        .then((result2)->{
        	System.out.println("Result 2: "+result2);
        })
        .then((result3)->{
        	if(TRUE_VALUE) {
				throw new RuntimeException("Test exception");
			} else {
				return false;
			}
        })
        .catchException((e)->{
        	System.out.println("Exception message: "+e.getMessage());
        })
        .thenAsync((unused)->{
        	return new PromiseFromCallback<Date>((resolver)->{
        		resolver.resolve(new Date());
        	});
        	
        })
        .then((resultDate)->{
        	System.out.println("Nested promise should return date: "+resultDate);
        })
        .then((result4)->{
        	System.out.println("Done");
        });
	}

}
