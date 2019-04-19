/**
 * 管理后台index服务层
 * Created by Wwl on 2019/4/17
 */
app.controller("indexController",function ($scope,loginService) {

    $scope.getLoginName = function () {
        loginService.getLoginName().then(res =>{
            $scope.loginName = res.data.loginName;
        })
        .catch( err => console.log(err) );
    }

});