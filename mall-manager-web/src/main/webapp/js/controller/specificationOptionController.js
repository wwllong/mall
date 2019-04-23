/**
 * 规格选项控制层
 * Created by Wwl on 2019/3/14
 */
app.controller('specificationOptionController', function($scope,$controller,specificationOptionService) {

    $controller('baseController',{$scope:$scope});//继承

    //数据验证
    $scope.validate = {
        rules: { },
        msg:{ }
    }

    $scope.getList = function() {
        specificationOptionService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询列表-分页
    $scope.getListPage = function(page, size) {
        specificationOptionService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //保存
    $scope.save = function(){
        var serviceObj;
        if($scope.specificationOption.id != null){
            serviceObj = specificationOptionService.update($scope.specificationOption);
        }else{
            serviceObj = specificationOptionService.add($scope.specificationOption);
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
        specificationOptionService.findOne(id).then(function(res){
            $scope.specificationOption = res.data;
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
                specificationOptionService.delete(selectIds).then(function(res){
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
        specificationOptionService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

});
