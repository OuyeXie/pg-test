package com.ouyexie.pg.exception;

import com.ouyexie.pg.utils.Constant;

public class BusinessException extends Exception {
    private static final long serialVersionUID = 638336348902251497L;
    private String m_errCode;
    private String m_errMsg;

    public BusinessException() {
        super();
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String errorCode, String errorMsg) {
        m_errCode = errorCode;
        m_errMsg = errorMsg;
    }

    public final String getMessage() {
        return String.format("{ \"%s\": \"%s\" }", Constant.Const.ERRCODE, m_errCode);
    }

    public final String getDetailedMessage() {
        return String.format("{ \"%s\": \"%s\", \"%s\": \"%s\" }", Constant.Const.ERRCODE, m_errCode, Constant.Const.ERRMSG, m_errMsg);
    }
}
