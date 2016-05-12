package org.blueo.db.config.raw;


public class DbTableConfigRawData {
	private String traceable;
	private String traceType;
	private String hasId;
	private String idType;
	private String traceTimeType;
	private String activeable;
	private String activeableType;

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbTableConfigRawData [traceable=");
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

	public String getActiveable() {
		return activeable;
	}

	public void setActiveable(String activeable) {
		this.activeable = activeable;
	}

	public String getActiveableType() {
		return activeableType;
	}

	public void setActiveableType(String activeableType) {
		this.activeableType = activeableType;
	}

	
}
