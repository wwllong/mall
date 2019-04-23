/**
 * 商品分类控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('itemCatalogController', function($scope,$controller,itemCatalogService,typeTemplateService) {

    $controller('baseController',{$scope:$scope});//继承

    //数据验证
    $scope.validate = {
        rules: {},
        msg:{}
    }

    $scope.getList = function() {
        itemCatalogService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询列表-分页
    $scope.getListPage = function(page, size) {
        itemCatalogService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //保存
    $scope.save = function(){
        let serviceObj;
        if($scope.itemCatalog.id != null){
            serviceObj = itemCatalogService.update($scope.itemCatalog);
        }else{
            $scope.itemCatalog.parentId = $scope.parentId;
            serviceObj = itemCatalogService.add($scope.itemCatalog);
        }
        serviceObj.then(function(res){
            layer.msg(res.data.message);
            if(res.data.success){
                //刷新表格数据
                $scope.findByParentId($scope.parentId);
            }
        })
        .catch(err => console.log(err));
        // angular.element("#editModal").modal('hide'); 没使用验证，注释掉
    }

    //查找实体
    $scope.findOne = function(id){
        itemCatalogService.findOne(id).then(function(res){
            $scope.itemCatalog = res.data;
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
                itemCatalogService.delete(selectIds).then(function(res){
                    layer.msg(res.data.message);
                    if(res.data.success){
                        $scope.findByParentId($scope.parentId);
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
        itemCatalogService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

    //上级Id
    $scope.parentId = 0;

    //根据pId获得列表
    $scope.findByParentId = function (parentdId) {
        $scope.parentId = parentdId;
        itemCatalogService.findByParentId(parentdId).then(function(res) {
            $scope.list = res.data;
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

    //面包屑导航-默认为顶级
    $scope.grade = 1;

    $scope.setGrade = function (grade) {
        $scope.grade = grade;
    }

    //导航调用的函数
    $scope.selectList = function (entity) {
        switch ($scope.grade) {
            case 1 :
                $scope.entity_1 = null;
                $scope.entity_2 = null;
                break;
            case 2:
                $scope.entity_1 = entity;
                $scope.entity_2 = null;
                break;
            case 3:
                $scope.entity_2 = entity;
                break;
        }
        $scope.findByParentId(entity.id);
    }

    $scope.getTypeTemplateOptionList = function() {
        typeTemplateService.findOptionList().then(function(res) {
            $scope.typeTemplateOptionList = res.data;
        });
    }

});
