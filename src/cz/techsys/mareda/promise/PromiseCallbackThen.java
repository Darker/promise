package cz.techsys.dialog.pla.shared.promise;

public interface PromiseCallbackThen<ArgumentType, ReturnType> {
    ReturnType processValue(ArgumentType value);
}
