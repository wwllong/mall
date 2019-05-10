/**
 * 购物车服务控制层
 * Created by Wwl on 2019/3/4
 */
app.controller('cartController', function($scope,$controller,cartService,$cookies,$timeout) {

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
        let delItemList = cartService.getSelectItems($scope.cartList);
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

    //结算，生成订单信息
    $scope.creatOrderInfo = function(){
        //获取要购买的购物车项
        let payItemList = cartService.getSelectItems($scope.cartList);
        if(payItemList.length==0){
            layer.msg("你还没有选择宝贝");
            return;
        }
        // 将要购买的购物车项存放在cookie
        let curTime = new Date();
        $cookies.put("payCartList",JSON.stringify(payItemList),{
            //设置过期时间-10分钟
            expires:  new Date(curTime.setMinutes(curTime.getMinutes() + 10))
        });
        location.href="getOrderInfo.html";
    }

    /**====结算页相关开始====**/
    //获取用户收货地址
    $scope.findAddressList = function () {
        cartService.findAddressList().then( (res)=>{
            $scope.addressList = res.data;
            $scope.addressList.forEach( address => {
                if(address.isDefault==='1'){
                    $scope.address = address;
                    return;
                }
            })
        }).catch( (err)=>console.log(err) );
    }

    //选中地址
    $scope.selectAddress = function (address) {
        $scope.address = address;
    }

    //判断是否当前选中的地址
    $scope.isSelectAddress = function (address) {
        return (address === $scope.address) ? true : false;
    }

    //编辑的收货地址实体
    $scope.entity = {};

    //保存收货地址
    $scope.save = function(){
        if($scope.entity.contact==null || $scope.entity.address==null || $scope.entity.address==null || $scope.entity.mobile==null){
            layer.msg("请填写必要的信息");
            return;
        }
        let serviceObj;
        if($scope.entity.id != null){
            serviceObj = cartService.update($scope.entity);
        }else{
            serviceObj = cartService.add($scope.entity);
        }
        serviceObj.then(function(res){
            angular.element("#editDialog").modal('hide');
            layer.msg(res.data.message);
            if(res.data.success){
                $scope.findAddressList();
            }
        }).catch(err => console.log(err));
    }

    //设置别名
    $scope.setAlias = function (alias) {
        $scope.entity.alias = alias;
    }

    //查找地址
    $scope.findAddress = function (id) {
        cartService.findOne(id).then( (res)=>{
            $scope.entity = res.data;
        }).catch( (err)=>console.log(err) );
    }

    //删除地址
    $scope.deleteAddress = function (id) {
        layer.confirm("你确定要删除该地址吗？", {icon: 3, title:"提示信息"}, function(index){
            cartService.delete(id).then( (res)=>{
                layer.msg(res.data.message);
                if(res.data.success){
                    $scope.findAddressList();
                }
            }).catch( (err)=>console.log(err) );
            layer.close(index);
        });
    }

    //订单对象
    $scope.order={paymentType:'1'};

    //选择支付方式
    $scope.selectPayType=function(type){
        $scope.order.paymentType= type;
    }

    //获取支付购物车信息
    $scope.getOrderInfo = function(){
        let payCartListStr = $cookies.get("payCartList");
        if(payCartListStr==null){
            layer.msg("订单失效,即将跳转购物车");
            $timeout(function () {
                window.location.href="http://localhost:9107/cart.html";
            },3000);
            return;
        }
        cartService.getOrderCartList(JSON.parse(payCartListStr)).then( (res) =>{
            $scope.payCartList = res.data;
        }).then((res)=>{
            $scope.payTotalValue = cartService.getPayCartListSum($scope.payCartList);
        }).catch( (err)=>console.log(err) );
    }

    //提交订单
    $scope.submitOrder = function () {
        if($scope.payCartList==null){
            layer.msg("订单失效,请重新提交订单");
            return;
        }
        $scope.order.receiverAreaName=$scope.address.address;//地址
        $scope.order.receiverMobile=$scope.address.mobile;//手机
        $scope.order.receiver=$scope.address.contact;//联系人
        cartService.submitOrder({order:$scope.order,payCartList:$scope.payCartList}).then( (res) =>{
            if(res.data.success){
                //页面跳转
                if($scope.order.paymentType=='1'){
                    //微信支付
                    location.href="pay.html";
                    //清除订单Cookie缓存
                    $cookies.remove("payCartList");
                }else{
                    //货到付款，跳转到提示页面
                    location.href="paysuccess.html";
                }
            }else{
                layer.msg(res.data.message);
            }
        }).catch( (err)=>console.log(err) );
    }

    /**====结算页相关结束====**/



});
