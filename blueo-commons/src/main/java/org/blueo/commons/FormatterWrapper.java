package org.blueo.commons;

import java.io.Closeable;
import java.io.Flushable;
import java.util.Formatter;

import org.apache.commons.lang3.StringUtils;

public class FormatterWrapper  implements Closeable, Flushable {
	private static final String DEFAULT_PREFIX = "\t";
	private final Formatter formatter;
	private final String prefix;
	private final int defaultPrefixRepeat;
	
	public FormatterWrapper() {
		this(DEFAULT_PREFIX);
	}
	
	public FormatterWrapper(String prefix) {
		this(new Formatter(), prefix);
	}
	public FormatterWrapper(Formatter formatter) {
		this(formatter, DEFAULT_PREFIX, 0);
	}
	
	public FormatterWrapper(Formatter formatter, String prefix) {
		this(formatter, prefix, 0);
	}
	
	public FormatterWrapper(Formatter formatter, int defaultPrefixRepeat) {
		this(formatter, DEFAULT_PREFIX, defaultPrefixRepeat);
	}
	
	public FormatterWrapper(Formatter formatter, String prefix, int defaultPrefixRepeat) {
		super();
		this.formatter = formatter;
		this.prefix = prefix;
		this.defaultPrefixRepeat = defaultPrefixRepeat;
	}

    public FormatterWrapper format(String format, Object ... args) {
    	return this.format(0, format, args);
    }
    
    // -------------------------------------
    public FormatterWrapper format(int prefixRepeat, String format, Object ... args) {
    	prefixRepeat = prefixRepeat + defaultPrefixRepeat;
    	if (prefixRepeat > 0) {
        	formatter.format("%s", StringUtils.repeat(prefix, prefixRepeat));
    	}
    	formatter.format(format, args);
    	return this;
    }

    public FormatterWrapper formatln() {
    	return this.formatln(- defaultPrefixRepeat, "");
    }

    public FormatterWrapper formatln(String format, Object ... args) {
    	return this.formatln(0, format, args);
    }

    public FormatterWrapper formatln(int prefixRepeat, String format, Object ... args) {
    	prefixRepeat = prefixRepeat + defaultPrefixRepeat;
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
    public void close() {
    	formatter.close();
    }
    
    @Override
    public void flush() {
    	formatter.flush();
    }
    
    @Override
    public String toString() {
    	return formatter.toString();
    }
}
