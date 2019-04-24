/**
 * 搜索控制层
 * Created by Wwl 2019/4/24
 */
app.controller("searchController",function ($scope,searchService) {

    //搜索
    $scope.search =  function(){
        searchService.search($scope.searchMap ).then( (res) => {
            $scope.resultMap = res.data;
        }).catch((err) => (console.log(err)));
    }

});
