package com.hhee.operation.core.http;

public class ErrorCode {

    public final static int Server_Error = 500;//服务器执行异常。

    public final static int BAD_REQUEST = 400;//服务器无法理解请求。

    public final static int UNAUTHORIZED = 401;//用户未身份认证。

    public final static int FORBIDDEN = 403;//禁止访问。

    public final static int NOT_FOUND = 404 ;//请求的资源未找到。
}
