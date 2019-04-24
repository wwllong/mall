/**
 * 内容/广告控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('contentController', function($scope,$controller,contentService,contentCategoryService,uploadService) {

    $controller('baseController',{$scope:$scope});//继承

    //数据验证
    $scope.validate = {
        rules: {},
        msg:{}
    }

    $scope.status=["禁用","启用"];

    $scope.getList = function() {
        contentService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询列表-分页
    $scope.getListPage = function(page, size) {
        contentService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //保存
    $scope.save = function(){
        let serviceObj;
        if($scope.content.id != null){
            serviceObj = contentService.update($scope.content);
        }else{
            serviceObj = contentService.add($scope.content);
        }
        serviceObj.then(function(res){
            layer.msg(res.data.message);
            if(res.data.success){
                $scope.reloadList();
            }
        })
        .catch(err => console.log(err));
    }

    //查找实体
    $scope.findOne = function(id){
        contentService.findOne(id).then(function(res){
            $scope.content = res.data;
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
                contentService.delete(selectIds).then(function(res){
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
        contentService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

    //上传图片
    $scope.uploadFile = function () {
        uploadService.uploadFile().then(function (res) {
            if(res.data.success){
                //文件地址
                $scope.content.pic = res.data.message;
            }else{
                layer("上传发生错误");
            }
        }).catch((err) => console.log(err));
    }

    //查询广告类目列表
    $scope.findContentCategoryList = function () {
        contentCategoryService.findList().then( (res) => {
            let data = res.data;
            $scope.contentCategoryList = data;
            $scope.categoryList = [];
            for(let i=0,length=data.length; i<length ; ++i ){
                $scope.categoryList[data[i].id] = data[i].name;
            }
        })
        .catch((err) => (console.log(err)));
    }

    let statusMsg = [
        {warn:"确定要禁用?",success:"禁用成功"},
        {warn:"确定启用?",success:"启用成功"}
    ]

    let updateStatusByIds = function(ids ,status ){
        layer.confirm(statusMsg[status].warn, {icon: 3, title:TIPS}, function(index){
            contentService.updateStatus(ids,status).then(function(res){
                layer.msg(statusMsg[status].success);
                if(res.data.success){
                    $scope.reloadList();
                }
                layer.close(index);
            }).catch(err => console.log(err));
        });
    }

    //更新广告状态
    $scope.updateStatus = function (status){
        let selectIds = $scope.getSelectId();
        if(selectIds.length===0){
            layer.msg("请选择要操作的数据!");
            return;
        }
        updateStatusByIds(selectIds,status);
    }


});
