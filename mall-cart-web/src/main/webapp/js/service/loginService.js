/**
 * 登陆服务层
 * Created by Wwl on 2019/5/5
 */
app.service('loginService', function($http){

    //获取登陆用户
    this.getUser = () => $http.post('../login/getUser.do');

});