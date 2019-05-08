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
    this.delItems = (cartList) =>{
        let delItems = [];
        for(let x=0,length=cartList.length; x<length; ++x){
            let cart = cartList[x];
            for(let y=0,length2=cart.orderItemList.length; y<length2; ++y){
                let orderItem = cart.orderItemList[y];
                if(orderItem.isSelect){
                    delItems.push(orderItem);
                }
            }
        }
        return delItems;
    }
});