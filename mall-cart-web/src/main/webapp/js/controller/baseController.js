/**
 * 基础控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('baseController', function($scope) {

    $scope.requiredMsg = "必填选项";

    /*json对象字符串，按属性拼接字符串*/
    $scope.jsonToString = function (jsonString, joinAttr) {
        let json = JSON.parse(jsonString);
        let joinVal = "";
        for(let i=0,len=json.length; i<len; ++i){
            if(i>0) {
                joinVal += ",";
            }
            joinVal += json[i][joinAttr];
        }
        return joinVal;
    }

    //从集合中按照Key查询对象
    $scope.searchObjectByKey = function (list,key,keyValue) {
        for(let i=0,len=list.length; i<len; ++i){
            if(list[i][key] === keyValue){
                return list[i];
            }
        }
        return null;
    }

    /*监听页面加载完成
    $scope.$watch('$viewContentLoaded', function() {

    });*/
});

