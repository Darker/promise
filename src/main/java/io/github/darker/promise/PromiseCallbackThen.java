package io.github.darker.promise;

public interface PromiseCallbackThen<ArgumentType, ReturnType> {
    ReturnType processValue(ArgumentType value) throws Throwable;
    
    public static interface VoidReturn<ArgumentType> extends PromiseCallbackThen<ArgumentType, java.lang.Void> {
    	public default java.lang.Void processValue(ArgumentType value) throws Throwable {
    		processValueVoid(value);
    		return null;
    	}
    	void processValueVoid(ArgumentType value) throws Throwable;
    }
    public static interface VoidArgument<ReturnType> extends PromiseCallbackThen<java.lang.Void, ReturnType> {
    	public default ReturnType processValue(java.lang.Void value) throws Throwable {
    		return processValueVoid();
    	}
    	ReturnType processValueVoid() throws Throwable;
    }
}
