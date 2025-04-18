
import service from './axios';

//登陆
export const login = (data) =>{
  return service({
    url:'/login',
    method:'post',
    data
  });
}

//退出
export const loginout = () =>{
  return service({
    url:'/loginout',
    method:'get'
  });
}

