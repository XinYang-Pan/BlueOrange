package org.blueo.db.config.raw;

import org.apache.commons.lang3.BooleanUtils;

public class DbTableConfigRawData {
	private String traceable;
	private String traceType;
	private String hasId;
	private String idType;
	private String traceTimeType;
	private String traceDelFlagType;

	public boolean isTraceableInBoolean() {
		return BooleanUtils.isTrue(BooleanUtils.toBooleanObject(traceable));
	}

	public boolean isHasIdInBoolean() {
		return BooleanUtils.isTrue(BooleanUtils.toBooleanObject(hasId));
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public String getTraceable() {
		return traceable;
	}

	public void setTraceable(String traceable) {
		this.traceable = traceable;
	}

	public String getTraceType() {
		return traceType;
	}

	public void setTraceType(String traceType) {
		this.traceType = traceType;
	}

	public String getHasId() {
		return hasId;
	}

	public void setHasId(String hasId) {
		this.hasId = hasId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getTraceTimeType() {
		return traceTimeType;
	}

	public void setTraceTimeType(String traceTimeType) {
		this.traceTimeType = traceTimeType;
	}

	public String getTraceDelFlagType() {
		return traceDelFlagType;
	}

	public void setTraceDelFlagType(String traceDelFlagType) {
		this.traceDelFlagType = traceDelFlagType;
	}

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
		builder.append(", traceDelFlagType=");
		builder.append(traceDelFlagType);
		builder.append("]");
		return builder.toString();
	}
	
}
