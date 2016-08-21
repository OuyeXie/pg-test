package com.ouyexie.pg.exception;

import static com.ouyexie.pg.utils.Constant.ErrCode;
import static com.ouyexie.pg.utils.Constant.ErrMsg;

public class HeaderException extends BusinessException {
    private static final long serialVersionUID = 638336348902251497L;
    private String m_errCode;
    private String m_errMsg;

    public HeaderException() {
        super(ErrCode.BAD_PARAM, ErrMsg.BAD_PARAM);
    }
}
