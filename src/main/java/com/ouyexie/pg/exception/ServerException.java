package com.ouyexie.pg.exception;

import com.ouyexie.pg.utils.Constant.ErrCode;
import com.ouyexie.pg.utils.Constant.ErrMsg;

public class ServerException extends BusinessException {

    private static final long serialVersionUID = 620829451304160992L;

    public ServerException() {
        this(ErrCode.SERVICE_UNAVAILABLE, ErrMsg.SERVICE_UNAVAILABLE);
    }

    public ServerException(Throwable throwable) {
        super(throwable);
    }

    public ServerException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public ServerException(String errorMsg) {
        super(ErrCode.SERVICE_UNAVAILABLE, errorMsg);
    }

    public ServerException(Exception e) {
        this(e.getMessage());
    }
}
