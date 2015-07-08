package org.adorsys.adcore.exceptions;

import java.io.Serializable;
/**
 * 
 * @author guymoyo
 *
 */
public class AdException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
    public AdException() {
        super();
    }
    public AdException(String msg)   {
        super(msg);
    }
    public AdException(String msg, Exception e)  {
        super(msg, e);
    }
}
