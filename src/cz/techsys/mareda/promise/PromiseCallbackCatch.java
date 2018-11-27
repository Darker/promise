package cz.techsys.dialog.pla.shared.promise;
public interface PromiseCallbackCatch<TReturnFromCatch> {
	TReturnFromCatch catchException(Throwable e);
}
//public interface PromiseCallbackCatch<TException extends Throwable> {
//    void catchException(TException e);
//    default void canCatchException(Throwable e) {
//    	return e instanceof TException;
//    }
//}
