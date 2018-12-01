package io.github.darker.promise;
public interface PromiseCallbackCatch<TReturnFromCatch> {
	TReturnFromCatch catchException(Throwable e) throws Throwable;
	
    public static interface VoidReturn extends PromiseCallbackCatch<java.lang.Void> {
    	public default java.lang.Void catchException(Throwable e) throws Throwable {
    		catchExceptionVoid(e);
    		return null;
    	}
    	void catchExceptionVoid(Throwable e) throws Throwable;
    }
    public static interface IgnoreReturn<TReturnFromCatch> extends PromiseCallbackCatch<TReturnFromCatch> {
    	public default TReturnFromCatch catchException(Throwable e) throws Throwable {
    		catchExceptionVoid(e);
    		return null;
    	}
    	void catchExceptionVoid(Throwable e) throws Throwable;
    }
}
//public interface PromiseCallbackCatch<TException extends Throwable> {
//    void catchException(TException e);
//    default void canCatchException(Throwable e) {
//    	return e instanceof TException;
//    }
//}
