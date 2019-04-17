/**
 * 登陆服务层
 * Created by Wwl on 2019/4/17
 */
app.service("loginService",function($http){

    //获取登陆用户名称
    this.getLoginName = () => $http.get('../login/name.do');
    
});
