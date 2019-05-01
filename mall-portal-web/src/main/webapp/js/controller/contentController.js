/**
 * 内容控制层
 * Created by Wwl 2019/4/24
 */
app.controller("contentController",function ($scope,contentService) {

    //内容列表
    $scope.contentList = [];

    $scope.keywords = '';

    //根据分类ID查询广告列表
    $scope.findByCategoryId = function (categoryId) {
        contentService.findByCategoryId(categoryId).then( (res) => {
            $scope.contentList[categoryId] = res.data;
        })
        .catch((err) => (console.log(err)));
    }

    //搜索
    $scope.search=function(){
        location.href="http://localhost:9104/search.html?keywords="+$scope.keywords;
    }


});
