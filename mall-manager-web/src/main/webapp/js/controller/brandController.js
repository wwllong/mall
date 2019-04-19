/**
 * 品牌控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('brandController', function($scope,$controller,brandService) {

    $controller('baseController',{$scope:$scope});//继承

    //数据验证
    $scope.validate = {
        rules: {
            firstChar:/^[A-Za-z]$/
        },
        msg:{
            firstChar:"请填写一个大写或小写单词"
        }
    }

    $scope.getList = function() {
        brandService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询品牌列表-分页
    $scope.getListPage = function(page, size) {
        brandService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //保存
    $scope.save = function(){
        let serviceObj;
        if($scope.brand.id != null){
            serviceObj = brandService.update($scope.brand);
        }else{
            serviceObj = brandService.add($scope.brand);
        }
        serviceObj.then(function(res){
            layer.msg(res.data.message);
            if(res.data.success){
                $scope.reloadList();
            }
        })
        .catch(err => console.log(err));
        angular.element("#editModal").modal('hide');
    }

    //查找实体
    $scope.findOne = function(id){
        brandService.findOne(id).then(function(res){
            $scope.brand = res.data;
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
                brandService.delete(selectIds).then(function(res){
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
        brandService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

});
