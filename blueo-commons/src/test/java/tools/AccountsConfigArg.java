package tools;

public class AccountsConfigArg {

	private short brokerId;
	private byte companyId;
	private long accountId;
	private boolean deleted;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountsConfigArg [brokerId=");
		builder.append(brokerId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", accountId=");
		builder.append(accountId);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append("]");
		return builder.toString();
	}

	public short getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(short brokerId) {
		this.brokerId = brokerId;
	}

	public byte getCompanyId() {
		return companyId;
	}

	public void setCompanyId(byte companyId) {
		this.companyId = companyId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
