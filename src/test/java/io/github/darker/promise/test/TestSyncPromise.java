package io.github.darker.promise.test;

import static io.github.darker.promise.concurrent.SynchronousPromise.resolvePromiseSync;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import io.github.darker.promise.Promise;
import io.github.darker.promise.lambda.PromiseFromCallback;
import io.github.darker.promise.test.util.RuntimeTestException;
import io.github.darker.promise.value.PromiseFromValue;

class TestSyncPromise {
    public static <T extends Throwable> Executable unwrapException(Executable throwingCode) {
    	return ()->{
    		try {
    			throwingCode.execute();
    		}
    		catch(ExecutionException e) {
    			throw e.getCause();
    		}
    	};
    }
    public static <T> T resolveOrUnwrapException(Promise<T> promise) throws Throwable {
    	try {
    		return resolvePromiseSync(promise);
    	}
		catch(ExecutionException e) {
			throw e.getCause();
		}
    }
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test if chained promise passes values")
	void testThen() throws InterruptedException, ExecutionException {
		Promise<String> test = new PromiseFromCallback<>((resolver)->{
			resolver.resolve("test");
		});
		Promise<Integer> test2 = test.then((result)->{
			return result.length();
		});
		assertEquals((Integer)4, resolvePromiseSync(test2));
	}

	@Test
	@DisplayName("Test if exceptions pop out of promises at the end")
	void testResolveWithException() {
		Promise<String> test = new PromiseFromCallback<>((resolver)->{
			resolver.reject(new RuntimeTestException());
		});
		assertThrows(RuntimeTestException.class, ()->{resolveOrUnwrapException(test);});
	}
	@Test
	@DisplayName("Test if exceptions jump over nested promises")
	void testResolveWithExceptionNested() {
		Promise<String> test = new PromiseFromCallback<>((resolver)->{
			resolver.reject(new RuntimeTestException());
		});
		Promise<?> test2 = test.thenAsync((unused)->{
			return (new PromiseFromCallback<String>((resolver)->{
				resolver.resolve(":)");
			}))
			.catchException((Throwable e)->{
				System.out.println("Failed - caught an exception "+e.getClass().getName()+" but no was thrown in this branch.");
			});
		});
		
		assertThrows(RuntimeTestException.class, ()->{resolveOrUnwrapException(test2);});
	}
	@Test
	@DisplayName("Test if inner exceptions can be caught in the inner branch")
	void testResolveWithCaughtNested() throws InterruptedException, ExecutionException {
		Promise<String> test = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("A");
		}))
		.thenAsync((messagePrefix)->{
			return (new PromiseFromCallback<String>((resolver)->{
				resolver.reject(new RuntimeTestException(messagePrefix+"B"));
			}))
			.catchException((Throwable e)->{
				return e.getMessage()+"C";
			});
		});
		
		assertEquals("ABC", resolvePromiseSync(test));
	}
	@Test
	@DisplayName("Test if inner exceptions can be caught in the outer branch")
	void testResolveWithCaughtNestedOuter() throws InterruptedException, ExecutionException {
		Promise<String> test = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("A");
		}))
		.thenAsync((messagePrefix)->{
			return (new PromiseFromCallback<String>((resolver)->{
				resolver.reject(new RuntimeTestException(messagePrefix+"B"));
			}));
		})			
		.catchException((Throwable e)->{
			return e.getMessage()+"C";
		});
		
		assertEquals("ABC", resolvePromiseSync(test));
	}
	@Test
	@DisplayName("Test if inner exceptions get thrown outside")
	void testThrowNestedOuter() throws InterruptedException, ExecutionException {
		Promise<String> test = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("A");
		}))
		.thenAsync((messagePrefix)->{
			return (new PromiseFromCallback<String>((resolver)->{
				resolver.reject(new RuntimeTestException(messagePrefix+"B"));
			}));
		});			

		assertThrows(RuntimeTestException.class, ()->{resolveOrUnwrapException(test);});
	}
	
	@Test
	@DisplayName("Test Promise.all")
	void testpromiseall() throws Throwable {
		Promise<String> testA = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("A");
		}));
		Promise<String> testB = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("B");
		}));
		Promise<String> testC = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("C");
		}));
		final Promise<String> result = Promise.all(testA,testB,testC)
				.then((strings)->{
					return strings.stream().collect(Collectors.joining(""));
				});
		assertEquals("ABC", resolveOrUnwrapException(result));
	}
	@Test
	@DisplayName("Test Promise.all exception")
	void testpromiseallerr() throws Throwable {
		Promise<String> testA = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("A");
		}));
		Promise<String> testB = (new PromiseFromCallback<>((resolver)->{
			resolver.reject(new RuntimeTestException("REJECT"));
		}));

		assertThrows(RuntimeTestException.class, ()->{resolveOrUnwrapException(Promise.all(testA,testB));});
	}
	
	@Test
	@DisplayName("Test Promise.multi (2 args)")
	void testmultipromise() throws Throwable {
		Promise<String> testA = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("A");
		}));
		Promise<Integer> testB = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve(1);
		}));
		
		assertEquals("A1", 
				resolveOrUnwrapException(
						   Promise.multi(testA, testB).then((result)->{return result.getRes1()+result.getRes2();})
						)
				);
	}
	@Test
	@DisplayName("Test Promise.multi with multiarg callback (2 args)")
	void testmultipromiseargs() throws Throwable {
		Promise<String> testA = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve("A");
		}));
		Promise<Integer> testB = (new PromiseFromCallback<>((resolver)->{
			resolver.resolve(1);
		}));

		assertEquals("A1", 
				resolveOrUnwrapException(
						   Promise.multi(testA, testB).then((str, integer)->{return str+integer;})
						)
				);
	}
	@Test
	@DisplayName("Test const value promises")
	void testconstvalpromise() throws Throwable {
		Promise<String> testA = new PromiseFromValue<>("A");
		Promise<Integer> testB = new PromiseFromValue<>(1);

		assertEquals("A1", 
				resolveOrUnwrapException(
						   Promise.multi(testA, testB).then((str, integer)->{return str+integer;})
						)
				);
	}
	@Test
	@DisplayName("Test const value promise chaining")
	void testconstvalpromisechain() throws Throwable {
		Promise<String> abc = new PromiseFromValue<>("A")
				.then((letter)->{return letter+"B";})
				.then((letter)->{return letter+"C";})
				;

		assertEquals("ABC", 
				resolveOrUnwrapException(
						   abc
						)
				);
	}
}
