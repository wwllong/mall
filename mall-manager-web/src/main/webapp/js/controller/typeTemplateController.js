/**
 * 类型模板控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('typeTemplateController', function($scope,$controller,brandService,specificationService,typeTemplateService) {

    $controller('baseController',{$scope:$scope});//继承

    //数据验证
    $scope.validate = {
        rules: { },
        msg:{ }
    }

    $scope.getList = function() {
        typeTemplateService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询品牌列表-分页
    $scope.getListPage = function(page, size) {
        typeTemplateService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //保存
    $scope.save = function(){
        let serviceObj;
        if($scope.typeTemplate.id != null){
            serviceObj = typeTemplateService.update($scope.typeTemplate);
        }else{
            serviceObj = typeTemplateService.add($scope.typeTemplate);
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
        typeTemplateService.findOne(id).then(function(res){
            $scope.typeTemplate = res.data;
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
            $scope.typeTemplate.specIds = JSON.parse($scope.typeTemplate.specIds);
            $scope.typeTemplate.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
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
                typeTemplateService.delete(selectIds).then(function(res){
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
        typeTemplateService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

    //品牌选项列表
    $scope.brandList = {data:[]};

    $scope.findBrandList = function(){
        brandService.findOptionList().then(function (res) {
            $scope.brandList.data = res.data;
        }).catch(err => console.log(err));
    }

    //规格选项列表
    $scope.specList = {data:[]};

    $scope.findSpecList = function(){
        specificationService.findSpecList().then(function (res) {
            $scope.specList.data = res.data;
        }).catch(err => console.log(err));
    }

    //增加扩展属性行
    $scope.addTableRow = function () {
        $scope.typeTemplate.customAttributeItems.push({});
    }

    //删除扩展属性行
    $scope.delTableRow = function (index) {
        $scope.typeTemplate.customAttributeItems.splice(index,1);
    }
});
