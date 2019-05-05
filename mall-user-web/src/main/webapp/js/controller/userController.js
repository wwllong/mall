/**
 * 用户控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('userController', function($scope,userService,$timeout) {

    $scope.isAgree = true;
    $scope.rePassword = "";
    $scope.user = {phone:""}

    //数据验证
    $scope.validate = {
        rules: {
            password:/^[a-zA-Z]\w{5,17}$/,
            phone:/^1(3|4|5|7|8)\d{9}$/,
            coed:/^\d{6}$/
        },
        msg:{
            password:"以字母开头，长度在5~17 之间，只能包含字符、数字和下划线",
            phone:"请输入正确的手机号码",
            coed:"请输入正确6位验证码",
            requiredMsg:"必填选项"
        }
    }

    //新增-注册新用户
    $scope.register = function(){
        if(!$scope.isAgree){
            layer.msg("请勾选同意协议");
            return;
        }
        if($scope.rePassword!=$scope.user.password){
            layer.msg("两次输入密码不一致，请重新输入");
            $scope.user.password="";
            $scope.rePassword="";
            return ;
        }
        userService.add($scope.user,$scope.code).then(function(res){
            if(res.data.success){
                layer.msg("注册成功</br>5秒后将跳转到首页。");
                $timeout(function () {
                    location.href = "http://localhost:9103/index.html";
                },5000);
            }else{
                layer.msg(res.data.message);
            }
        }).catch(err => console.log(err));
    }

    //发送验证码
    $scope.sendCode = function () {
        if($scope.user.phone=="" || $scope.user.phone===undefined){
            layer.msg("请输入正确的手机号码!");
            return;
        }
        if($scope.timing == 60){
            userService.sendCode($scope.user.phone);
            $scope.countDown($scope.timing);
        }else{
            layer.msg("请勿频繁获取验证码");
        }

    }

    //倒计时
    $scope.timing = 60;
    $scope.sendCodeTips = "获取短信验证码";
    $scope.countDown = function (second) {
        if($scope.timing===0){
            $scope.sendCodeTips = "获取短信验证码";
            $scope.timing = 60;
        }else{
            $scope.sendCodeTips = "重新发送(" + $scope.timing + ")";
            $scope.timing--;
            $timeout(function () {
                $scope.countDown(second);
            },1000);
        }
    }



});
