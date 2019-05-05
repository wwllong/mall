/**
 * 首页控制层
 * Created by Wwl on 2019/5/5
 */
app.controller('indexController', function($scope,loginService) {

    $scope.getLoginUser = function(){
        $scope.loginUser = loginService.getUser().then( (res)=>{
            $scope.loginUser = res.data;
        });
    }

});
