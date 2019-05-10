/**
 * 商品详细页-商品SKU控制层
 * Created by Wwl 2019/4/24
 */
app.controller("itemController",function ($scope,$http) {

    //商品数量
    $scope.num = 1;

    //用户选择的规格
    $scope.specification = {};

    //商品数量操作
    $scope.addNum = function(x){
        $scope.num += x ;
        if($scope.num<1){
            $scope.num = 1;
        }
    }

    //选择规格
    $scope.selectSpec = (attrName,attrValue) =>{
        $scope.specification[attrName] = attrValue;
        //匹配对应KSU
        searchSKU();
    }

    //判断是否选择规格
    $scope.isSelectSpec = (attrName,attrValue) =>($scope.specification[attrName]==attrValue?'true':false);

    //加载默认SKU
    $scope.loadSKU = function(){
        if(skuList.length>0){
            $scope.sku = skuList[0];
            $scope.specification = JSON.parse(JSON.stringify($scope.sku.spec));
        }
        
    }

    //查询用户选择规格对应的SKU
    searchSKU = () =>{
        for(let x=0,length=skuList.length; x<length; ++x){
            if(matchObje(skuList[x].spec, $scope.specification)){
                //对应的SKU信息
                $scope.sku = skuList[x];
                return;
            }
        }
        //没有匹配SKU
        $scope.sku = {id:0,title:'---暂时无货---',price:0};
    }
    
    //根据规格匹配对象
    matchObje = (map1,map2) => {
        for(let key in map1){
            if(map1[key]!=map2[key]){
                return false;
            }
        }
        for(let key in map2){
            if(map2[key]!=map1[key]){
                return false;
            }
        }
        return true;
    }

    //添加购物车
    $scope.add2Cart = () =>{
        //跨域操作cookie，需要携带凭证
        $http.get('http://localhost:9107/cart/addGoods2CarList.do?itemId='+
            $scope.sku.id+'&num='+$scope.num,{'withCredentials':true}).then( (res) =>{
                location.href='http://localhost:9107/cart.html';
        }).catch((err) => {console.log(err)});
    }

});
