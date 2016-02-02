package org.blueo.commons;

import java.util.Formatter;

import org.apache.commons.lang3.StringUtils;

public class FormatterNb {
	private final Formatter formatter;
	private final String prefix;
	
	public FormatterNb() {
		this("\t");
	}
	
	public FormatterNb(String prefix) {
		this(new Formatter(), prefix);
	}
	
	public FormatterNb(Formatter formatter, String prefix) {
		super();
		this.formatter = formatter;
		this.prefix = prefix;
	}

    public FormatterNb format(String format, Object ... args) {
    	return this.format(0, format, args);
    }

    public FormatterNb format(int prefixRepeat, String format, Object ... args) {
    	if (prefixRepeat > 0) {
        	formatter.format("%s", StringUtils.repeat(prefix, prefixRepeat));
    	}
    	formatter.format(format, args);
    	return this;
    }

    public FormatterNb formatln() {
    	return this.formatln(0, "");
    }

    public FormatterNb formatln(String format, Object ... args) {
    	return this.formatln(0, format, args);
    }

    public FormatterNb formatln(int prefixRepeat, String format, Object ... args) {
    	if (prefixRepeat > 0) {
        	formatter.format("%s", StringUtils.repeat(prefix, prefixRepeat));
    	}
    	if (StringUtils.isNotEmpty(format)) {
        	formatter.format(format, args);
    	}
    	formatter.format("%s", System.lineSeparator());
    	return this;
    }
	
    @Override
    public String toString() {
    	return formatter.toString();
    }
}
