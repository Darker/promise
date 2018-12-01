package io.github.darker.promise.multi;

public class MultiResult<T1, T2, T3, T4> implements MultiResult4<T1, T2, T3, T4> {

	protected T1 res1 = null;
	protected T2 res2 = null;
	protected T3 res3 = null;
	protected T4 res4 = null;
	
	protected enum ValueStatus {
		UNSET,
		UNUSED,
		SET
	}
	
	protected ValueStatus[] status = new ValueStatus[] {
			ValueStatus.UNSET,
			ValueStatus.UNSET,
			ValueStatus.UNSET,
			ValueStatus.UNSET
	};
	
	
	public MultiResult(T1 res1, T2 res2, T3 res3, T4 res4) {
		super();
		this.res1 = res1;
		status[0] = ValueStatus.SET;
		this.res2 = res2;
		status[1] = ValueStatus.SET;
		this.res3 = res3;
		status[2] = ValueStatus.SET;
		this.res4 = res4;
		status[3] = ValueStatus.SET;
	}
	public MultiResult(T1 res1, T2 res2, T3 res3) {
		super();
		this.res1 = res1;
		status[0] = ValueStatus.SET;
		this.res2 = res2;
		status[1] = ValueStatus.SET;
		this.res3 = res3;
		status[2] = ValueStatus.SET;
		this.res3 = null;
		status[2] = ValueStatus.UNUSED;
	}
	
	public MultiResult(int length) {
		super();
		setLength(length);
	}
	public MultiResult() {
		super();
	}
	public void setLength(int usedValues) {
		if(usedValues<2 || usedValues>4) {
			throw new IllegalArgumentException("Length must be in range 1 to 4, was "+usedValues);
		}
		for(int i=0; i<4;++i) {
			if(i>=usedValues) {
				status[i] = ValueStatus.UNUSED;
			}
			else {
				status[i] = ValueStatus.UNSET;
			}
		}
	}
	
	public static <T1,T2,T3> MultiResult<T1,T2,T3,Void> create3() {
		return new MultiResult<T1,T2,T3,Void>(3);
	}
	public static <T1,T2> MultiResult<T1,T2,Void,Void> create2() {
		return new MultiResult<T1,T2,Void,Void>(2);
	}


	public void setRes1(T1 res1) {
		this.res1 = res1;
		status[0] = ValueStatus.SET;
	}
	public void setRes2(T2 res2) {
		this.res2 = res2;
		status[1] = ValueStatus.SET;
	}
	public void setRes3(T3 res3) {
		this.res3 = res3;
		status[2] = ValueStatus.SET;
	}
	public void setRes4(T4 res4) {
		this.res4 = res4;
		status[3] = ValueStatus.SET;
	}
	
	public boolean isSet1() {
		return status[0] == ValueStatus.SET;
	}
	public boolean isSet2() {
		return status[1] == ValueStatus.SET;
	}
	public boolean isSet3() {
		return status[2] == ValueStatus.SET;
	}
	public boolean isSet4() {
		return status[3] == ValueStatus.SET;
	}
	
	public boolean allSet() {
		for(ValueStatus s:status) {
			if(s == ValueStatus.UNSET) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public T3 getRes3() {
		return res3;
	}

	@Override
	public T1 getRes1() {
		return res1;
	}

	@Override
	public T2 getRes2() {
		return res2;
	}

	@Override
	public T4 getRes4() {
		return res4;
	}
    
}
