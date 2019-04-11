/**
 * 基础控制层
 * Created by Wwl on 2019/3/4 0004.
 */
app.controller('baseController', function($scope) {

    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 10, //总记录数
        itemsPerPage: 10, //每页记录数
        pagesLength: 6,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function() {
            $scope.reloadList();
        }
    };

    //刷新列表
    $scope.reloadList = function() {
        $scope.search( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    /*监听全选checkbox*/
    $scope.watchCheckALL = function(){
        $scope.$watch("isSelectAll",function(newVal, oldVal){
            if(newVal===undefined)return;
            if(newVal){
                //全选
                $scope.list.forEach(value => value.isSelect = true);
            }else{
                //判断是否被列表checkbox控制
                let isSelectAll = true;
                $scope.list.forEach(value => isSelectAll = value.isSelect && isSelectAll);
                //列表全为ture，反选
                if (isSelectAll) {
                    $scope.list.forEach(value => value.isSelect = false);
                }
            }
        },true);
    }

    /*监听列表checkbox*/
    $scope.watchCheckList = function(){
        $scope.$watch("list",function(newVal, oldVal){
            if(newVal===undefined)return;
            let isAll = true;
            $scope.list.forEach(value => isAll = value.isSelect && isAll);
            $scope.isSelectAll = isAll;
        },true);
    }

    $scope.watchCheckALL();
    $scope.watchCheckList();

    /*自定义H5表单验证信息*/
    $scope.verify = function ($this, tip) {
        if($this.validity.patternMismatch === true){
            $this.setCustomValidity(tip);
        }else{
            $this.setCustomValidity("");
        }
    }

    /*监听页面加载完成
    $scope.$watch('$viewContentLoaded', function() {

    });*/
});

function verify($this, tip) {
    if($this.validity.patternMismatch === true){
        $this.setCustomValidity(tip);
    }else{
        $this.setCustomValidity("");
    }
}
