/**
 * 品牌控制层
 * Created by Wwl on 2019/3/4 0004.
 */
app.controller('brandController', function($scope,$controller,brandService) {

    $controller('baseController',{$scope:$scope});//继承

    $scope.patterns = {
        name: /^[A-Za-z]$/,
        firstChar:/^[A-Za-z]$/
    }

    $scope.getList = function() {
        brandService.findList().success(
            function(response) {
                $scope.list = response;
            }
        );
    }

    //查询品牌列表-分页
    $scope.getListPage = function(page, size) {
        brandService.findPage.success(function(response) {
            $scope.list = response.rows; //当前页数据
            $scope.paginationConf.totalItems = response.total; //总记录数
        });
    }

    //保存
    $scope.save = function(){
        var serviceObj;
        console.log($scope.brand);
        // if($scope.brand.id != null){
        //     serviceObj = brandService.update($scope.brand);
        // }else{
        //     serviceObj = brandService.add($scope.brand);
        // }
        // serviceObj.success(function(response){
        //     layer.msg(response.message);
        //     if(response.success){
        //         $scope.reloadList();
        //     }
        // });
        angular.element("#editModal").modal('hide');
    }

    //查找实体
    $scope.findOne = function(id){
        brandService.findOne(id).success(function(response){
            $scope.brand = response;
        });
    }

    //删除
    $scope.delete = function () {
        let selectIds = [];
        $scope.list.forEach( (value)=>{
            if(value.isSelect){
                selectIds.push(value.id);
            }
        });
        if(selectIds.length==0){
            layer.msg(DEL_WARN_TIPS);
            return;
        }else{
            layer.confirm(DEL_CONFIRM_TIPS, {icon: 3, title:TIPS}, function(index){
                brandService.delete(selectIds).success(function(response){
                    layer.msg(response.message);
                    if(response.success){
                        $scope.reloadList();
                    }
                });
                layer.close(index);
            });
        }
    }

    //搜索对象
    $scope.searchEntity={ };

    //条件查询
    $scope.search = function (page,size) {
        brandService.search(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total; //总记录数
                $scope.list = response.rows;//当前页数据
            }
        );
    }

});
