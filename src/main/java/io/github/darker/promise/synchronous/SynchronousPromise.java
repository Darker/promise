package io.github.darker.promise.synchronous;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.github.darker.promise.Promise;
import io.github.darker.promise.PromiseCallbackCatch;
import io.github.darker.promise.PromiseCallbackThen;

public class SynchronousPromise<ThenArgumentType> implements Promise<ThenArgumentType>, Future<ThenArgumentType> {

	protected final Promise<ThenArgumentType> nestedPromise;
	protected final Object mutex = new Object();
	protected boolean done = false;
	protected ThenArgumentType result;
	protected Throwable rejection;
	protected boolean rejected = false;
	public SynchronousPromise(Promise<ThenArgumentType> nestedPromise)
	throws NullPointerException
	{
		super();
		if(nestedPromise == null) {
			throw new NullPointerException("Nested promise cannot be null!");
		}
		this.nestedPromise = nestedPromise;
		this.nestedPromise.then((result)->{
			synchronized(mutex) {
				SynchronousPromise.this.result = result;
				done = true;			
				mutex.notifyAll();
			}
		})
		.catchException((exception)->{
			synchronized(mutex) {
			    rejection = exception;
			    rejected = true;
			    done = true;	
			    mutex.notifyAll();
			}
		});
	}
    public static <T> T resolvePromiseSync(Promise<T> promise) throws InterruptedException, ExecutionException {
    	final SynchronousPromise<T> syncPromise = new SynchronousPromise<>(promise);
    	return syncPromise.get();
    }
	@Override
	public <T> Promise<T> then(PromiseCallbackThen<ThenArgumentType, T> cb) {
		return nestedPromise.then(cb);
	}

	@Override
	public Promise<ThenArgumentType> catchException(PromiseCallbackCatch<ThenArgumentType> catchHandler) {
		return nestedPromise.catchException(catchHandler);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public ThenArgumentType get() throws InterruptedException, ExecutionException {
		synchronized(mutex) {
			while(!done) {
				mutex.wait();
			}
		}
		if(rejected) {
			throw new ExecutionException(rejection);
		}
		else {
			return result;
		}
	}

	@Override
	public ThenArgumentType get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		final long timeoutMs = unit.toMillis(timeout);
		
		synchronized(mutex) {
			while(!done) {
				mutex.wait(timeoutMs);
			}
		}

		return result;
	}

	
}
