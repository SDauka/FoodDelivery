package com.epam.sultangazy.webapp.action;

public class ActionResult {
    private String result;
    private boolean redirected;

    public ActionResult(String result, boolean redirect) {
        this.result = result;
        this.redirected = redirect;
    }

    public ActionResult(String view) {
        this(view, false);
    }

    public ActionResult() {
        this("");
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isRedirected() {
        return redirected;
    }

    public void setRedirected(boolean redirected) {
        this.redirected = redirected;
    }

    @Override
    public String toString() {
        return ActionResult.class.toString();
    }
}
