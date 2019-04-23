/**
 * 商品SPU控制层
 * Created by Wwl on 2019/3/4
 */
app.config([ '$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode({
        //设置为html5Mode(模式)，当为false时为Hashbang模式
        enabled : true,
        //是否需要加入base标签，这里设置为false，设置为true时，需在html的head配置<base href="" />标签
        requireBase : false
    });
} ]);
app.controller('goodsController', function($scope,$controller,goodsService,itemCatalogService,brandService,$location) {

    $controller('baseController',{$scope:$scope});//继承

    //商品状态
    $scope.status=['未审核','已审核','审核未通过','已关闭'];

    //数据验证
    $scope.validate = {
        rules: {},
        msg:{}
    }

    $scope.getList = function() {
        goodsService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询列表-分页
    $scope.getListPage = function(page, size) {
        goodsService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //查找实体
    $scope.findOne = function(){
        //获取参数值
        let id = $location.search()['id'];
        if(id===undefined || id===""){
            return ;
        }
        //查询，回显数据
        goodsService.findOne(id).then(function(res){
            $scope.goodsGroup = res.data;
            let goodsDesc = $scope.goodsGroup.goodsDesc;
            //富文本-商品介绍
            editor.html(goodsDesc.introduction);
            //图片列表
            goodsDesc.itemImages = JSON.parse(goodsDesc.itemImages);
            //扩展属性,注意此处会受监听器的影响，会被覆盖
            goodsDesc.customAttributeItems = JSON.parse(goodsDesc.customAttributeItems);
            //规格属性
            goodsDesc.specificationItems = JSON.parse(goodsDesc.specificationItems);
            //SKU信息
            let itemList = $scope.goodsGroup.itemList;
            for(let x=0,len=itemList.length; x<len; ++x){
                itemList[x].spec = JSON.parse( itemList[x].spec );
            }
        })
        .catch(err => console.log(err));
    }

    //删除
    $scope.delete = function () {
        let selectIds = $scope.getSelectId();
        if(selectIds.length==0){
            layer.msg(DEL_WARN_TIPS);
            return;
        }else{
            layer.confirm(DEL_CONFIRM_TIPS, {icon: 3, title:TIPS}, function(index){
                goodsService.delete(selectIds).then(function(res){
                    layer.msg(res.data.message);
                    if(res.data.success){
                        $scope.reloadList();
                    }
                    layer.close(index);
                })
                .catch(err => console.log(err));
            });
        }
    }

    //搜索对象
    $scope.searchEntity={ };

    //条件查询
    $scope.search = function (page,size) {
        /* angularJS 1.6+ */
        goodsService.search(page,size,$scope.searchEntity).then( function(res){
            $scope.paginationConf.totalItems = res.data.total; //总记录数
            $scope.list = res.data.rows;//当前页数据
        })
        .then(() => {
            $scope.watchCheck();
        })
        .catch(err => console.log(err));
    }

    $scope.itemCatalogList = [];

    //查询商品所有分类
    $scope.findItemCatalogList = function () {
        itemCatalogService.findList().then((res)=>{
            //对查询结果进行处理,封装成分类数组，[{id:"",name:""}]
            let data = res.data;
            for (let x=0,len=data.length; x<len; ++x){
                $scope.itemCatalogList[data[x].id]=data[x].name;
            }
        }).catch((err)=>(console.log(err)));
    }

    $scope.brandList = [];

    //查询所有品牌
    $scope.findBrandList = function () {
        brandService.findList().then((res)=>{
            //对查询结果进行处理,封装成分类数组，[{id:"",name:""}]
            let data = res.data;
            for (let x=0,len=data.length; x<len; ++x){
                $scope.brandList[data[x].id]=data[x].name;
            }
        }).catch((err)=>(console.log(err)));
    }

    let statusMsg = [
        {warn:"确定要提交审核?",success:"提交成功，请耐心等待审核。"},
        {warn:"确定通过审核?",success:"商品审核成功。"},
        {warn:"确定驳回所选商品审核?",success:"商品已经被驳回。"},
        {warn:"确定关闭所选商品?",success:"商品已经被关闭。"}
    ]

    let updateStatusByIds = function(ids ,status ){
        layer.confirm(statusMsg[status].warn, {icon: 3, title:TIPS}, function(index){
            goodsService.updateStatus(ids,status).then(function(res){
                layer.msg(statusMsg[status].success);
                if(res.data.success){
                    $scope.reloadList();
                }
                layer.close(index);
            }).catch(err => console.log(err));
        });
    }

    //更新商品状态
    $scope.updateStatus = function (status){
        let selectIds = $scope.getSelectId();
        if(selectIds.length===0){
            layer.msg("请选择要操作的数据!");
            return;
        }
        updateStatusByIds(selectIds,status);
    }

    $scope.view = function (id) {
        layer.open({
            type: 2,//iframe
            title:"商品详情",
            area: ['1010px', '800px'],
            shadeClose: true,
            fixed: false, //不固定
            maxmin: true,
            content: 'goods_view.html?id='+id,
            btn: ['审核通过', '驳回审核', '关闭商品','关闭'],
            yes: (index, layero)=>{
                updateStatusByIds(id,1);
                layer.close(index);
            },
            btn2: (index, layero)=>updateStatusByIds(id,2),
            btn3: (index, layero)=>updateStatusByIds(id,3),
            btn4: function(index, layero){

            }
        });
    }

});
