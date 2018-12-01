package io.github.darker.promise;

public interface PromiseWrapCallback<ThenArgumentType>  {
	// pass return value to the callback and then resolve with a 
	// result of that
    void bubbleReturnValue(ThenArgumentType result);
    // Bubble exception 
    // throw it if there is nowhere to send it further down the line
    <TExc extends Throwable> void bubbleException(TExc e) throws TExc;
}
