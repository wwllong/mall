/**
 * 购物车服务控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('cartController', function($scope,$controller,cartService) {

    $controller('baseController',{$scope:$scope});//继承

    $scope.isSelectAll = false;
    $scope.cartList = [];
    $scope.oldCartList = [];

    //查询购物车列表
    $scope.findCartList = function () {
        cartService.findCartList().then( (res)=>{
            $scope.oldCartList = $scope.cartList;
            $scope.cartList = res.data;
        }).then( (res)=>{
            getBackCheckValues($scope.cartList,$scope.oldCartList);
            for(let x=0,len=$scope.cartList.length; x<len; ++x){
                watchShop(x);
                watchShopList(x);
            }
        }).catch( (err)=>console.log(err) );
    }

    //添加商品到购物车
    $scope.addGoods2CarList = function (itemId,num) {
        cartService.addGoods2CarList(itemId,num).then( (res)=>{
            if(res.data.success){
                //刷新列表
                $scope.findCartList();
            }else{
                layer.msg(res.data.message);
            }
        }).catch( (err)=>console.log(err) );
    }

    //监控购物车全选
    $scope.$watch("isSelectAll",function (newValue,oldValue) {
       if(newValue){
            //全选
            $scope.cartList.forEach(cart => {
                cart.isSelectAll = true;
            });
        }else{
           //判断是否被列表checkbox控制
           let isSelectAll = true;
           $scope.cartList.forEach(cart => isSelectAll = cart.isSelectAll && isSelectAll);
           //列表全为ture，全选按钮就反选
           if (isSelectAll) {
               $scope.cartList.forEach(cart => {
                   cart.isSelectAll = false;
               });
           }
        }
        //求总和
        $scope.totalValue=cartService.sum($scope.cartList);
    },true);

    //监控整个购物车列表列表
    $scope.$watch("cartList",function (newValue,oldValue) {
        //监听所有商家的购物车列表
        let isSelectAll = true;
        $scope.cartList.forEach(cart => isSelectAll = cart.isSelectAll && isSelectAll);
        $scope.isSelectAll = isSelectAll;
        //求总和
        $scope.totalValue=cartService.sum($scope.cartList);
    },true);

    //监控单个商家的购物车-全选按钮
    watchShop = function (index) {
        //全选
        $scope.$watch("cartList["+index+"].isSelectAll",function (newValue,oldValue) {
            if(newValue===undefined) return;
            if(newValue){
                //全选
                $scope.cartList[index].orderItemList.forEach(item => {
                    item.isSelect = true;
                });
            }else{
                let isSelectAll = true;
                //列表全为ture，全选按钮就反选
                if (isSelectAll) {
                    $scope.cartList[index].orderItemList.forEach(item => {
                        item.isSelect = false;
                    });
                }
            }
            //求总和
            $scope.totalValue=cartService.sum($scope.cartList);
        },true);
    }

    //监控单个商家的购物车列表
    watchShopList = function (index) {
        $scope.$watch("cartList["+index+"]",function(newVal, oldVal){
            if(newVal===undefined)return;
            let isSelectAll = true;
            $scope.cartList[index].orderItemList.forEach( item => isSelectAll = item.isSelect && isSelectAll);
            $scope.cartList[index].isSelectAll = isSelectAll;
            //求总和
            $scope.totalValue=cartService.sum($scope.cartList);
        },true);
    }

    //取回checkbox的值
    getBackCheckValues = function (newList,oldList) {
        //遍历旧的列表
        for(let x = 0, oldLen = oldList.length; x<oldLen ; ++x){
            let oldCart = oldList[x];
            //查找新列表对象
            let cartObj = $scope.searchObjectByKey(newList,"sellerId",oldCart.sellerId);
            //如果存在对象，更新checkBox的值
            if(cartObj!=null){
                //取回全选的值
                cartObj.isSelectAll = oldCart.isSelectAll;
                //查找是否有购物车项-item
                let oldCartItemList = oldCart.orderItemList;
                for(let y = 0, oldItemListLen = oldCartItemList.length; y<oldItemListLen ; ++y){
                    let orderItemObj = $scope.searchObjectByKey(cartObj.orderItemList,"itemId",oldCartItemList[y].itemId);
                    if(orderItemObj!=null){
                        orderItemObj.isSelect = oldCartItemList[y].isSelect;
                    }
                }
            }
        }

    }

    //删除选中的商品
    $scope.deletes = function () {
        //获取要删除的itemd对象
        let delItemList = cartService.delItems($scope.cartList);
        if(delItemList.length==0){
            layer.msg("请选择宝贝");
            return;
        }
        layer.confirm("你确定要删除这些宝贝吗？", {icon: 3, title:"提示信息"}, function(index){
            delItemList.forEach((item)=>{
                $scope.addGoods2CarList(item.itemId,-item.num);
            });
            layer.close(index);
        });

    }


});
