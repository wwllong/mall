/**
 * 购物车服务层
 * Created by Wwl on 2019/3/4
 */
app.service('cartService', function($http){

    //查询购物车列表
    this.findCartList = () => $http.get('cart/findCartList.do');

    //添加商品到购物车
    this.addGoods2CarList = (itemId,num) =>  $http.get('cart/addGoods2CarList.do?itemId='+itemId+'&num='+num);

    //求总和
    this.sum = (cartList) =>{
        let totalValue = {totalNum:0,totalPrices:0.00};
        for(let x=0,length=cartList.length; x<length; ++x){
            let cart = cartList[x];
            for(let y=0,length2=cart.orderItemList.length; y<length2; ++y){
                let orderItem = cart.orderItemList[y];
                if(orderItem.isSelect){
                    totalValue.totalNum+=orderItem.num;
                    totalValue.totalPrices+=orderItem.totalFee;
                }
            }
        }
        return totalValue;
    }

    //求删除选中的商品对象
    this.getSelectItems = (cartList) =>{
        let selectItems = [];
        for(let x=0,length=cartList.length; x<length; ++x){
            let cart = cartList[x];
            for(let y=0,length2=cart.orderItemList.length; y<length2; ++y){
                let orderItem = cart.orderItemList[y];
                if(orderItem.isSelect){
                    selectItems.push(orderItem);
                }
            }
        }
        return selectItems;
    }

    /**====结算页相关====**/
    //获取登陆用户地址列表
    this.findAddressList = () =>  $http.get('address/findListByLoginUser.do');

    //增加
    this.add = (entity) => $http.post('address/add.do',entity);

    //修改
    this.update = (entity) => $http.post('address/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('address/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('address/findOne.do?id='+id);

    //获取结算购物车
    this.getOrderCartList = (entity) => $http.post('address/getOrderCartList.do',entity);

    //求结算购物车总和
    this.getPayCartListSum = (cartList) =>{
        let totalValue = {totalNum:0,totalPrices:0.00};
        for(let x=0,length=cartList.length; x<length; ++x){
            let cart = cartList[x];
            for(let y=0,length2=cart.orderItemList.length; y<length2; ++y){
                let orderItem = cart.orderItemList[y];
                totalValue.totalNum+=orderItem.num;
                totalValue.totalPrices+=orderItem.totalFee;
            }
        }
        return totalValue;
    }

    //保存订单
    this.submitOrder=function(pojo){
        return $http.post('order/add.do',pojo);
    }

});