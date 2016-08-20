package com.ouyexie.pg.exception;

import static com.ouyexie.pg.utils.Constant.ErrCode;
import static com.ouyexie.pg.utils.Constant.ErrMsg;

public class NotImplementedException extends BusinessException {
    private static final long serialVersionUID = 638336348902251497L;
    private String m_errCode;
    private String m_errMsg;

    public NotImplementedException() {
        super(ErrCode.NOT_IMPLEMENTED, ErrMsg.NOT_IMPLEMENTED);
    }
}
