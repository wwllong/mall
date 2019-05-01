/**
 * 搜索控制层
 * Created by Wwl 2019/4/24
 */
app.config([ '$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode({
        //设置为html5Mode(模式)，当为false时为Hashbang模式
        enabled : true,
        //是否需要加入base标签，这里设置为false，设置为true时，需在html的head配置<base href="" />标签
        requireBase : false
    });
} ]);
app.controller("searchController",function ($scope,searchService,$location) {

    //搜索条件
    $scope.searchMap={ keywords:'',category:'',brand:'',spec:{},price:'',pageNumber:1,pageSize:20,sort:'',sortField:''};

    //搜索结果
    $scope.resultMap={totalPages:'',brandList:[]}

    $scope.priceList = [
        {value:'0-500',text:'0-500元'},
        {value:'500-1000',text:'1000-1500元'},
        {value:'1500-2000',text:'1500-2000元'},
        {value:'2000-3000',text:'2000-3000元'},
        {value:'3000-*',text:'3000元以上'}
    ];

    //搜索
    $scope.search =  function(){
        if($scope.searchMap.keywords===''){
            return;
        }
        searchService.search($scope.searchMap).then( (res) => {
            $scope.resultMap = res.data;
            buildPagination();
        }).catch((err) => (console.log(err)));
    }

    //添加搜索项
    $scope.addSearchItem = function (key,value) {
        //添加分类or品牌or价格
        if("category" === key || "brand" === key || "price" === key ){
            $scope.searchMap[key] = value;
        }else{
            //添加规格
            $scope.searchMap.spec[key] = value;
        }
        $scope.search();
    }

    //删除搜索项
    $scope.removeSearchItem = function (key) {
        //删除分类or品牌or价格
        if("category" === key || "brand" === key || "price" === key ){
            $scope.searchMap[key] = '';
        }else{
            //移除规格
            delete $scope.searchMap.spec[key];
        }
        $scope.search();
    }

    //构建分页工具条
    var buildPagination = function () {
        let maxPage = $scope.resultMap.totalPages;
        let firstPage = 1 ;
        let lastPage = maxPage;
        $scope.firstDotted = true;
        $scope.lastDotted = true;

        //构建页码标签
        $scope.pageNumLabel = [];
        //如果总页数大于5，显示部分页码
        if( maxPage > 5 ){
            let currentPage = $scope.searchMap.pageNumber;
            if( currentPage <= 3 ){
                lastPage = 5;
                $scope.firstDotted = false;
            }else if( currentPage >= maxPage-2 ){
                firstPage = maxPage - 5;
                lastPage = maxPage;
                $scope.lastDotted = false;
            }else{
                firstPage = currentPage - 2;
                lastPage = currentPage + 2;
            }
        }else{
            $scope.firstDotted = false;
            $scope.lastDotted = false;
        }

        for(let i=firstPage; i<=lastPage; ++i){
            $scope.pageNumLabel.push(i)
        }
    }

    //根据页码进行查询
    $scope.searchByPage = function (pageNumber) {
        if(pageNumber<1 || pageNumber>$scope.resultMap.totalPages){
            return;
        }
        $scope.searchMap.pageNumber = pageNumber;
        $scope.search();
    }

    //判断是否第一页
    $scope.isFirstPage = () => ($scope.searchMap.pageNumber==1?true : false);

    //判断是否最后一页
    $scope.isLastPage = () => ($scope.searchMap.pageNumber>=$scope.resultMap.totalPages?true : false);

    //判断是否当前页
    $scope.isCurrentPage = (pageNumber) => ($scope.searchMap.pageNumber==pageNumber?true : false);

    //设置排序规则
    $scope.sortSearch = function (sortField , sort) {
        $scope.searchMap.sortField = sortField;
        $scope.searchMap.sort = sort;
        $scope.sortStr = sortField + sort;
        $scope.search();
    }

    $scope.sortStr = '';
    //判断是否排序规则
    $scope.isSortRule = (sortStr) => ($scope.sortStr===sortStr?true : false);

    //判断关键字是不是品牌
    $scope.keywordsIsBrand = function () {
        for(let x=0,length=$scope.resultMap.brandList.length ; x<length; ++x){
            if($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[x].text)>=0){
                return true;
            }
        }
        return false;
    }

    //加载查询字符串
    $scope.loadKeywords=function(){
        $scope.searchMap.keywords=  $location.search()['keywords'];
        $scope.search();
    }


});
