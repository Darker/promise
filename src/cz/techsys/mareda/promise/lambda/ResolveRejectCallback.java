package cz.techsys.dialog.pla.shared.promise.lambda;

public interface ResolveRejectCallback<ReturnType> {
	public void initPromise(ResolveRejectFunctions<ReturnType> resolutionObject);
}