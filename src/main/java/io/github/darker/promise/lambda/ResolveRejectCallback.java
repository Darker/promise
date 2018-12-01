package io.github.darker.promise.lambda;

public interface ResolveRejectCallback<ReturnType> {
	public void initPromise(ResolveRejectFunctions<ReturnType> resolutionObject);
}