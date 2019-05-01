//定义模块-基础模块
let app=angular.module('mall',[]);

//过滤器
app.filter('trustAsHtml',['$sce',function ($sce) {
    return (data)=>($sce.trustAsHtml(data));
}]);