package org.blueo.db.config;

import org.blueo.db.vo.DbType;

public class DbTableConfig {
	private boolean traceable;
	private DbType traceType;
	private boolean hasId;
	private DbType idType;
	private DbType traceTimeType;
	private boolean activeable;
	private DbType activeableType;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbTableConfig [traceable=");
		builder.append(traceable);
		builder.append(", traceType=");
		builder.append(traceType);
		builder.append(", hasId=");
		builder.append(hasId);
		builder.append(", idType=");
		builder.append(idType);
		builder.append(", traceTimeType=");
		builder.append(traceTimeType);
		builder.append(", activeable=");
		builder.append(activeable);
		builder.append(", activeableType=");
		builder.append(activeableType);
		builder.append("]");
		return builder.toString();
	}

	public boolean isTraceable() {
		return traceable;
	}

	public void setTraceable(boolean traceable) {
		this.traceable = traceable;
	}

	public DbType getTraceType() {
		return traceType;
	}

	public void setTraceType(DbType traceType) {
		this.traceType = traceType;
	}

	public boolean isHasId() {
		return hasId;
	}

	public void setHasId(boolean hasId) {
		this.hasId = hasId;
	}

	public DbType getIdType() {
		return idType;
	}

	public void setIdType(DbType idType) {
		this.idType = idType;
	}

	public DbType getTraceTimeType() {
		return traceTimeType;
	}

	public void setTraceTimeType(DbType traceTimeType) {
		this.traceTimeType = traceTimeType;
	}

	public boolean isActiveable() {
		return activeable;
	}

	public void setActiveable(boolean activeable) {
		this.activeable = activeable;
	}

	public DbType getActiveableType() {
		return activeableType;
	}

	public void setActiveableType(DbType activeableType) {
		this.activeableType = activeableType;
	}

}
