package com.qjx.repeat.zk.bean.login;

import java.io.Serializable;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/22
 * @author <others>
 */
public class ZkLoginRequest implements Serializable {
    private Integer protocolVersion;
    private Long lastZxidSeen;
    private int timeout;
    private Long sessionId;
    private String password;
    private boolean readOnly = true;

    public Integer getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(Integer protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Long getLastZxidSeen() {
        return lastZxidSeen;
    }

    public void setLastZxidSeen(Long lastZxidSeen) {
        this.lastZxidSeen = lastZxidSeen;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
