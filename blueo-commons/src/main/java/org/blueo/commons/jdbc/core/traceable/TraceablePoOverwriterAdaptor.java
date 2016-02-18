package org.blueo.commons.jdbc.core.traceable;

import java.util.Date;

import org.apache.commons.lang3.BooleanUtils;
import org.blueo.commons.jdbc.core.DelFlagType;

public abstract class TraceablePoOverwriterAdaptor<U> implements TraceablePoOverwriter<U> {
	
	@Override
	public <T> T getOverwrite(T t, DelFlagType type) {
		if (t == null) {
			return t;
		}
		if (!(t instanceof TraceablePo<?>)) {
			return t;
		}
		// None null traceable PO
		TraceablePo<?> traceablePo = (TraceablePo<?>) t;
		switch (type) {
		case All:
			return t;
		case Active:
			if (BooleanUtils.isNotTrue(traceablePo.getDelFlag())) {
				return t;
			}
			break;
		case Del:
			if (BooleanUtils.isTrue(traceablePo.getDelFlag())) {
				return t;
			}
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void saveOverwrite(Object t) {
		if (t instanceof TraceablePo<?>) {
	    	TraceablePo<U> traceablePo = (TraceablePo<U>) t;
	    	if (traceablePo.getCreateTime() == null) {
				traceablePo.setCreateTime(this.currentTime());
	    	}
	    	if (traceablePo.getUpdateTime() == null) {
	    		traceablePo.setUpdateTime(this.currentTime());
	    	}
	    	if (traceablePo.getDelFlag() == null) {
	    		traceablePo.setDelFlag(false);
	    	}
	    	if (traceablePo.getCreateId() == null) {
	    		traceablePo.setCreateId(this.getUserId());
	    	}
	    	if (traceablePo.getUpdateId() == null) {
	    		traceablePo.setUpdateId(this.getUserId());
	    	}
	    }
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateOverwrite(Object t) {
	    if (t instanceof TraceablePo<?>) {
	    	TraceablePo<U> traceablePo = (TraceablePo<U>) t;
    		traceablePo.setUpdateTime(this.currentTime());
    		traceablePo.setUpdateId(this.getUserId());
        }
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean deleteOverwrite(Object t) {
		if (t instanceof TraceablePo<?>) {
	    	TraceablePo<U> traceablePo = (TraceablePo<U>) t;
	    	traceablePo.setDelFlag(true);
	    	return true;
        } else {
        	return false;
        }
	}
	
	@Override
	public Date currentTime() {
		return new Date();
	}
	
}
