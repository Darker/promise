package cz.techsys.dialog.pla.shared.promise.lambda;

public interface ResolveRejectFunctions<TResolve> {
	public void resolve(TResolve returnValue);
	public void reject(Throwable e);
}