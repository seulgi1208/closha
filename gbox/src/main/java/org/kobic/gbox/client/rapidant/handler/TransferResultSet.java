package org.kobic.gbox.client.rapidant.handler;

import rapidant.common.error.RapidantErrorCode;

public class TransferResultSet {

	private final boolean successful;
	private final RapidantErrorCode code;
	private final String reason;

	public TransferResultSet(boolean successful, RapidantErrorCode code,
			String reason) {
		this.successful = successful;
		this.code = code;
		this.reason = reason;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public RapidantErrorCode getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + (successful ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferResultSet other = (TransferResultSet) obj;
		if (code != other.code)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (successful != other.successful)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{successful=").append(successful).append(", code=")
				.append(code).append(", reason=").append(reason).append("}");
		return builder.toString();
	}

}