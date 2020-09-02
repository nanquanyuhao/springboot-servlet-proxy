package net.nanquanyuhao.proxy.conf;

public class TargetObjectResult {

    private TargetObject targetObject;

    private boolean contains;

    public TargetObject getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
    }

    public boolean isContains() {

        if (targetObject == null) {
            return false;
        }
        return true;
    }
}
