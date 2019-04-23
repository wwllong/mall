/**
 * 卖家控制层
 * Created by Wwl on 2019/4/18
 */
app.controller('sellerController', function($scope,$controller,sellerService,$timeout) {

    $controller('baseController',{$scope:$scope});//继承

    $scope.isAgree = true;

    //数据验证
    $scope.validate = {
        rules: {
            password:/^[a-zA-Z]\w{5,17}$/
        },
        msg:{
            password:"以字母开头，长度在5~17 之间，只能包含字符、数字和下划线"
        }
    }

    $scope.getList = function() {
        sellerService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询列表-分页
    $scope.getListPage = function(page, size) {
        sellerService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //新增-申请入驻
    $scope.add = function(){
        if(!$scope.isAgree){
            layer.msg("请勾选同意协议");
            return;
        }
        let serviceObj=sellerService.add($scope.seller);
        serviceObj.then(function(res){
            if(res.data.success){
                layer.msg("申请成功，请等待运营商审核结果。</br>5秒后将跳转到登陆页。");
                $timeout(function () {
                    location.href = "shoplogin.html";
                },5000);
            }else{
                layer.msg(res.data.message);
            }

        })
        .catch(err => console.log(err));
    }

    //查找实体
    $scope.findOne = function(id){
        sellerService.findOne(id).then(function(res){
            $scope.seller = res.data;
        })
        .catch(err => console.log(err));
    }

    //删除
    $scope.delete = function () {
        let selectIds = $scope.getSelectId();
        if(selectIds.length==0){
            layer.msg(DEL_WARN_TIPS);
            return;
        }else{
            layer.confirm(DEL_CONFIRM_TIPS, {icon: 3, title:TIPS}, function(index){
                sellerService.delete(selectIds).then(function(res){
                    layer.msg(res.data.message);
                    if(res.data.success){
                        $scope.reloadList();
                    }
                    layer.close(index);
                })
                .catch(err => console.log(err));
            });
        }
    }

    //搜索对象
    $scope.searchEntity={ };

    //条件查询
    $scope.search = function (page,size) {
        /* angularJS 1.6+ */
        sellerService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

    //更新状态
    $scope.updateStatus = function (sellerId, status) {
        sellerService.updateStatus(sellerId,status).then( res =>{
            layer.msg(res.data.message);
            if(res.data.success){
                $scope.reloadList();
            }
        })
        .catch(err => console.log(err));
    }


});
