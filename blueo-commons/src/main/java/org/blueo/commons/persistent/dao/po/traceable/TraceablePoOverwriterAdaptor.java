package org.blueo.commons.persistent.dao.po.traceable;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

public abstract class TraceablePoOverwriterAdaptor<T extends TraceablePo<U>, U> implements TraceablePoOverwriter<T, U> {

	@Override
	public <K extends T> K getOverwrite(K t, DelFlagType type) {
		if (t == null) {
			return t;
		}
		switch (type) {
		case All:
			return t;
		case Active:
			if (BooleanUtils.isNotTrue(t.getDelFlag())) {
				return t;
			}
			break;
		case Del:
			if (BooleanUtils.isTrue(t.getDelFlag())) {
				return t;
			}
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public void saveOverwrite(T t) {
		Date currentTime = this.currentTime();
		if (t.getCreateTime() == null) {
			t.setCreateTime(currentTime);
		}
		if (t.getUpdateTime() == null) {
			t.setUpdateTime(currentTime);
		}
		if (t.getDelFlag() == null) {
			t.setDelFlag(false);
		}
		if (t.getCreateId() == null) {
			t.setCreateId(this.getUserId());
		}
		if (t.getUpdateId() == null) {
			t.setUpdateId(this.getUserId());
		}
	}

	@Override
	public void updateOverwrite(T t) {
		t.setUpdateTime(this.currentTime());
		t.setUpdateId(this.getUserId());
	}

	@Override
	public void deleteOverwrite(T t) {
		t.setDelFlag(true);
	}

	@Override
	public void saveAllOverwrite(List<T> list) {
		if (list == null) {
			return;
		}
		Date currentTime = this.currentTime();
		U userId = this.getUserId();
		for (T t : list) {
			if (t.getCreateTime() == null) {
				t.setCreateTime(currentTime);
			}
			if (t.getUpdateTime() == null) {
				t.setUpdateTime(currentTime);
			}
			if (t.getDelFlag() == null) {
				t.setDelFlag(false);
			}
			if (t.getCreateId() == null) {
				t.setCreateId(userId);
			}
			if (t.getUpdateId() == null) {
				t.setUpdateId(userId);
			}
		}
	}

	@Override
	public void updateAllOverwrite(List<T> list) {
		if (list == null) {
			return;
		}
		Date currentTime = this.currentTime();
		U userId = this.getUserId();
		for (T t : list) {
			t.setUpdateTime(currentTime);
			t.setUpdateId(userId);
		}
	}

	@Override
	public void deleteAllOverwrite(List<T> list) {
		if (list == null) {
			return;
		}
		for (T t : list) {
			t.setDelFlag(true);
		}
	}

	@Override
	public void findByExampleOverwrite(T t) {
		if (t.getDelFlag() == null) {
			t.setDelFlag(false);
		}
	}

	@Override
	public Date currentTime() {
		return new Date();
	}

}
