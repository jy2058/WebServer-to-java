package kr.letech.webServer.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseVO extends BaseObj implements Serializable {
	private static final long serialVersionUID = -4826648503787712580L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
