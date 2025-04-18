package com.hhee.operation.core.http;

import lombok.Data;

@Data
public class HttpResult {
    private int code = 200;
    private String msg;
    private Object data;

    public static HttpResult error(String msg){
        return error(ErrorCode.Server_Error,msg);
    }

    public static HttpResult error(int code,String msg){
        HttpResult result = new HttpResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static HttpResult ok(){
        return ok(null);
    }

    public static HttpResult ok(Object data){
        HttpResult result = new HttpResult();
        result.setData(data);
        return result;
    }
}
