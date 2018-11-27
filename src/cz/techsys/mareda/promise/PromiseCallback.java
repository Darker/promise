package cz.techsys.dialog.pla.shared.promise;

public interface PromiseCallback<ReturnType> {
    Promise<?, ?> done(ReturnType result);
}
