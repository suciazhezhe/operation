
import service from './axios';

export const testStart = (data) =>{
  return service({
    url:'/test/testStart',
    method:'get'
  });
}

