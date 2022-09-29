package com.company.micro_service_1.handler;

public enum Exceptions {

    //权限角色
    NOT_FIND_ROLE(1000001L, "找不到相关角色!"),

    //学生模块异常
    STUDENT_NO_TEXIST(2000001L, "学生不存在"),
    STUDENT_IS_TEXIST(2000002L, "学生存在"),
    STUDENT_IMPORT_IS_TEXIST(2000003L, "导入的数据学生存在");

    Long errCode;
    String errMsg;

    Exceptions(Long errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ServiceException exception() {
        return new ServiceException().setErrCode(this.errCode).setErrMsg(this.errMsg);
    }

    public ServiceException exception(Object errData) {
        return new ServiceException().setErrCode(this.errCode).setErrorData(errData).setErrMsg(this.errMsg);
    }

    public ServiceException exception(Object errData, Throwable e) {
        return new ServiceException(this.errCode, this.errMsg, errData, e);
    }
}