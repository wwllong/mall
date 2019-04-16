/**
 * 规格控制层
 * Created by Wwl on 2019/3/14
 */
app.controller('specificationController', function($scope,$controller,specificationService) {

    $controller('baseController',{$scope:$scope});//继承

    //数据验证
    $scope.validate = {
        rules: {
        },
        msg:{
        }
    }

    $scope.getList = function() {
        specificationService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询品牌列表-分页
    $scope.getListPage = function(page, size) {
        specificationService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //保存
    $scope.save = function(){
        var serviceObj;
        if($scope.specificationGroup.id != null){
            serviceObj = specificationService.update($scope.specificationGroup);
        }else{
            serviceObj = specificationService.add($scope.specificationGroup);
        }
        serviceObj.then(function(res){
            layer.msg(res.data.message);
            if(res.data.success){
                $scope.reloadList();
            }
        })
        .catch(err => console.log(err));
        // angular.element("#editModal").modal('hide');
    }

    //查找实体
    $scope.findOne = function(id){
        specificationService.findOne(id).then(function(res){
            $scope.specification = res.data;
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
                specificationService.delete(selectIds).then(function(res){
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
        specificationService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            if(!$scope.isWatchCheck){
                $scope.watchCheckALL();
                $scope.watchCheckList();
                $scope.isWatchCheck = true;
            }
        })
        .catch(err => console.log(err));
    }

    $scope.specificationGroup = {specificationOptionList:[]};

    //增加规格选项行
    $scope.addTableRow = function () {
        $scope.specificationGroup.specificationOptionList.push({});
    }

    //删除规格选项行
    $scope.delTableRow = function (index) {
        $scope.specificationGroup.specificationOptionList.splice(index,1);
    }

});
