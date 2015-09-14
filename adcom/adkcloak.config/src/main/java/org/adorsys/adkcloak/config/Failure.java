package org.adorsys.adkcloak.config;

public class Failure extends Exception {
	private static final long serialVersionUID = 3380941819474750037L;
	private int status;

    public Failure(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
