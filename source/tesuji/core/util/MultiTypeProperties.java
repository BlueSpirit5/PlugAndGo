/**
 * Project: Tesuji Go Framework.<br>
 * <br>
 * <font color="#CC6600"><font size=-1> Copyright (c) 1985-2006 Mark Boon<br>
 * All rights reserved.<br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * provided that the above copyright notice(s) and this permission notice appear
 * in all copies of the Software and that both the above copyright notice(s) and
 * this permission notice appear in supporting documentation.<br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.<br>
 * <br>
 * <font color="#00000"><font size=+1>
 * 
 */

package tesuji.core.util;

import java.util.Properties;

/**
 * Simple convenience class that can store and retrieve properties of types other than String as well.
 */
public class MultiTypeProperties
	extends Properties
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5703602162799219412L;

	public int getIntProperty(String key)
	{
		String property = getProperty(key);
		if (property==null)
			return Integer.MIN_VALUE;
		return Integer.parseInt(property);
	}

	public double getDoubleProperty(String key)
	{
		String property = getProperty(key);
		if (property==null)
			return Double.NEGATIVE_INFINITY;
		return Double.parseDouble(getProperty(key));
	}
	
	public boolean getBooleanProperty(String key)
	{
		String property = getProperty(key);
		if (property==null)
			return false;
		return Boolean.parseBoolean(property);
	}

	public void setIntProperty(String key, int value)
	{
		setProperty(key, Integer.toString(value));
	}
	
	public void setDoubleProperty(String key, double value)
	{
		setProperty(key, Double.toString(value));
	}
	
	public void setBooleanProperty(String key, boolean value)
	{
		setProperty(key, Boolean.toString(value));
	}
	
}
