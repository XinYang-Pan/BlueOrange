package org.blueo.commons.persistent.dao.po.traceable;

import java.util.Date;
import java.util.List;

import org.blueo.commons.persistent.dao.impl.EntityDaoAdaptor;
import org.springframework.beans.factory.annotation.Autowired;

public class TraceablePoEntityDao<T extends TraceablePo<U>, K, U> extends EntityDaoAdaptor<T, K> {
	
	@Autowired
	private UserIdAndTimeGetter<U> userIdAndTimeGetter;
	
	public K save(T t) {
		if (t == null) {
			return null;
		}
		Date currentTime = this.currentTime();
		if (t.getCreateTime() == null) {
			t.setCreateTime(currentTime);
		}
		if (t.getUpdateTime() == null) {
			t.setUpdateTime(currentTime);
		}
		if (t.getCreateId() == null) {
			t.setCreateId(this.getUserId());
		}
		if (t.getUpdateId() == null) {
			t.setUpdateId(this.getUserId());
		}
		return super.save(t);
	}

	public void update(T t) {
		if (t == null) {
			return;
		}
		t.setUpdateTime(this.currentTime());
		t.setUpdateId(this.getUserId());
		entityDao.update(t);
	}


	public void saveAll(List<T> list) {
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
			if (t.getCreateId() == null) {
				t.setCreateId(userId);
			}
			if (t.getUpdateId() == null) {
				t.setUpdateId(userId);
			}
		}
		entityDao.saveAll(list);
	}

	public void updateAll(List<T> list) {
		if (list == null) {
			return;
		}
		Date currentTime = this.currentTime();
		U userId = this.getUserId();
		for (T t : list) {
			t.setUpdateTime(currentTime);
			t.setUpdateId(userId);
		}
		entityDao.updateAll(list);
	}
	
	private U getUserId() {
		return userIdAndTimeGetter.getUserId();
	}

	private Date currentTime() {
		return userIdAndTimeGetter.currentTime();
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public UserIdAndTimeGetter<U> getUserIdAndTimeGetter() {
		return userIdAndTimeGetter;
	}

	public void setUserIdAndTimeGetter(UserIdAndTimeGetter<U> userIdAndTimeGetter) {
		this.userIdAndTimeGetter = userIdAndTimeGetter;
	}
}
