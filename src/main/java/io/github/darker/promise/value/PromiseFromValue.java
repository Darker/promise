package io.github.darker.promise.value;

import io.github.darker.promise.PromiseChaining;
/**
 * This is shorthand to using PromiseFromCallback. Use to return value that is already available,
 * such as when you're implementing async method that doesn't need async behavior.
 * 
 * @author Jakub Mareda
 *
 * @param <ThenArgumentType>
 */
public class PromiseFromValue<ThenArgumentType> 
extends PromiseChaining<ThenArgumentType>
{
	public PromiseFromValue(ThenArgumentType value) {
		super();
		handleResult(value);
	}
}
