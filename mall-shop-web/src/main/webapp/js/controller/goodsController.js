/**
 * 商品控制层
 * Created by Wwl on 2019/4/18
 */
app.config([ '$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode({
        //设置为html5Mode(模式)，当为false时为Hashbang模式
        enabled : true,
        //是否需要加入base标签，这里设置为false，设置为true时，需在html的head配置<base href="" />标签
        requireBase : false
    });
} ]);
app.controller('goodsController', function($scope,$controller,goodsService,uploadService,itemCatalogService,typeTemplateService,$location,$timeout) {

    $controller('baseController',{$scope:$scope});//继承

    //页面实体
    $scope.goodsGroup = {goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};

    //商品状态
    $scope.status=['未审核','已审核','审核未通过','关闭'];

    //商品上下架
    $scope.marketable=['下架','上架'];

    //数据验证
    $scope.validate = {
        rules: { },
        msg:{ }
    }

    $scope.getList = function() {
        goodsService.findList().then(function(res) {
                $scope.list = res.data;
        });
    }

    //查询品牌列表-分页
    $scope.getListPage = function(page, size) {
        goodsService.findPage.then(function(res) {
            $scope.list = res.data.rows; //当前页数据
            $scope.paginationConf.totalItems = res.data.total; //总记录数
        });
    }

    //保存商品
    $scope.save = function(){
        //未选择商品分类
        let typeTemplateId = $scope.goodsGroup.goods.typeTemplateId;
        if(typeTemplateId === undefined || typeTemplateId === null){
            layer.msg("请选择商品分类!");
            return;
        }
        let brandId = $scope.goodsGroup.goods.brandId;
        if(brandId === undefined || brandId === null){
            layer.msg("请选择商品品牌!");
            return;
        }
        //提取富文本值
        $scope.goodsGroup.goodsDesc.introduction = editor.html();

        let serviceObj = null;
        if($scope.goodsGroup.goods.id!=null){
            serviceObj=goodsService.update($scope.goodsGroup);
        }else{
            serviceObj=goodsService.add($scope.goodsGroup);
        }
        serviceObj.then( (res) => {
            if(res.data.success){
                layer.msg(res.data.message);
                if($scope.goodsGroup.goods.id!=null){
                    $timeout(function () {
                        location.href="goods.html";//跳转到商品列表页
                    },1000);
                }else{
                    //清空数据
                    $scope.goodsGroup = {goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
                    $scope.specListWithOptions = [];
                    editor.html('');
                }
            }else{
                layer.msg(res.data.message);
            }
        })
        .catch(err => console.log(err));
    }

    //查找实体-引用$location服务，进行页面之间跳转
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

    //回显规格属性-根据规格名称和选项名称返回是否被勾选
    $scope.checkAttrValue = function(attrName,attrValue){
        let specItems = $scope.goodsGroup.goodsDesc.specificationItems;
        let item = $scope.searchObjectByKey(specItems,'attributeName',attrName);
        if(item == null){
            return false;
        }else{
            //查找是否存在该值
            return (item.attributeValue.indexOf(attrValue)<0) ? false : true;
        }
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

    //上传图片
    $scope.uploadFile = function () {
        uploadService.uploadFile().then(function (res) {
            if(res.data.success){
                //文件地址
                $scope.img_entity.url = res.data.message;
            }else{
                layer("上传发生错误");
            }
        })
        .catch((err) => console.log(err));
    }

    //添加图片到列表
    $scope.addImgEntity = function () {
        $scope.goodsGroup.goodsDesc.itemImages.push($scope.img_entity);
    }

    //删除图片
    $scope.removeImgEntity = function (index) {
        $scope.goodsGroup.goodsDesc.itemImages.splice(index,1);
    }

    //获取一级分类列表
    $scope.selectItemCatalogRootList = function () {
        itemCatalogService.findByParentId(0).then( (res) => {
            $scope.itemCatalogRootList = res.data;
        })
        .catch((err)=>(console.log(err)));
    }

    //动态获取二级分类列表
    $scope.$watch('goodsGroup.goods.category1Id',function (newValue, oldValue) {
        if(newValue === undefined) return ;
        if(newValue === null){
            $scope.itemCatalog2List = {};
            return;
        }
        itemCatalogService.findByParentId(newValue).then( (res) => {
            $scope.itemCatalog2List = res.data;
        })
        .catch((err)=>(console.log(err)));
    });

    //动态获取三级分类列表
    $scope.$watch('goodsGroup.goods.category2Id',function (newValue, oldValue) {
        if(newValue === undefined) return ;
        if(newValue === null){
            $scope.itemCatalog3List = {};
            return;
        }
        itemCatalogService.findByParentId(newValue).then( (res) => {
            $scope.itemCatalog3List = res.data;
        })
        .catch((err)=>(console.log(err)));
    });

    //动态获取模板ID
    $scope.$watch('goodsGroup.goods.category3Id',function (newValue, oldValue) {
        if(newValue === undefined) return ;
        if(newValue === null){
            $scope.goodsGroup.goods.typeTemplateId = null;
            return;
        }
        itemCatalogService.findOne(newValue).then( (res) => {
            $scope.goodsGroup.goods.typeTemplateId = res.data.typeId;
        })
        .catch((err)=>(console.log(err)));
    });

    //根据模板ID，动态获取关联的品牌、规格
    $scope.$watch('goodsGroup.goods.typeTemplateId',function (newValue, oldValue) {
        if(newValue === undefined) return ;
        if(newValue === null){
            //清空模板和扩展属性(自定义属性）
            $scope.typeTemplate = {};
            $scope.goodsGroup.goodsDesc.customAttributeItems = {};
            return;
        }
        typeTemplateService.findOne(newValue).then( (res) => {
            //更新模板
            $scope.typeTemplate = res.data;
            //更新品牌、扩展属性，注意扩展属性绑定到页面实体
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
            //如果没有ID，则加载模板中的扩展数据
            if($location.search()['id']===undefined || $location.search()['id']===""){
                $scope.goodsGroup.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
            }
        }).then( (res) =>{
            //查询模板关联的规格列表-用于显示数据
            typeTemplateService.findSpecListWithOptions(newValue).then( (res) => {
                $scope.specListWithOptions = res.data;
            });
        })
        .catch((err)=>(console.log(err)));
    });

    //更新规格选项specificationItems
    $scope.updateSpecAttr = function ($event,name,value) {
        //查找集合中的对象/单条记录
        let specificationItems = $scope.goodsGroup.goodsDesc.specificationItems;
        let obj = $scope.searchObjectByKey( specificationItems,'attributeName' ,name);
        if(obj!=null){
            //对象存在集合中,进行值的更新与删除
            if($event.target.checked){
                obj.attributeValue.push(value);
            }else{
                obj.attributeValue.splice(obj.attributeValue.indexOf(value),1);
            }
            //所有选项取消，移除此记录
            if(obj.attributeValue.length===0){
                specificationItems.splice(specificationItems.indexOf(obj),1);
            }
        }else{
            specificationItems.push({"attributeName":name,"attributeValue":[value]});
        }
    }

    //商品SKU信息
    $scope.creatItemList = function () {
        //初始化
        $scope.goodsGroup.itemList = [{ spec:{},price:9999,num:9999,status:'0',isDefault:'0'}];
        let specItems = $scope.goodsGroup.goodsDesc.specificationItems;
        for(let i=0,len=specItems.length; i<len; ++i){
            $scope.goodsGroup.itemList = addColumn( $scope.goodsGroup.itemList,specItems[i].attributeName,specItems[i].attributeValue);
        }
    }

    //添加列-深拷贝
    let addColumn = function (list,colName,colValues) {
        let newList = [];
        for(let i=0,len=list.length; i<len; ++i){
            let oldRow = list[i];
            for(let j=0,colValueLength=colValues.length; j<colValueLength; ++j){
                //深拷贝
                let newRow = JSON.parse(JSON.stringify(oldRow));
                newRow.spec[colName] = colValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }

    $scope.itemCatalogList = [];

    //查询分类
    $scope.findItemCatalogList = function () {
        itemCatalogService.findList().then((res)=>{
            //对查询结果进行处理,封装成分类数组，[{id:"",name:""}]
            let data = res.data;
            for (let x=0,len=data.length; x<len; ++x){
                $scope.itemCatalogList[data[x].id]=data[x].name;
            }
        })
        .catch((err)=>(console.log(err)));
    }

    //更新商品状态
    $scope.updateStatus = function (status){
        let selectIds = $scope.getSelectId();
        if(selectIds.length===0){
            layer.msg("请选择要操作的数据!");
            return;
        }
        if(status==='0'){
            layer.confirm("确定要提交审核？", {icon: 3, title:TIPS}, function(index){
                goodsService.updateStatus(selectIds,status).then(function(res){
                    layer.msg("提交成功，请耐心等待审核。");
                    if(res.data.success){
                        $scope.reloadList();
                    }
                    layer.close(index);
                }).catch(err => console.log(err));
            });
        }else{

        }
    }

    //更新商品上下架
    $scope.updateMarketable = function (status){
        let selectIds = $scope.getSelectId();
        if(selectIds.length===0){
            layer.msg("请选择要操作的数据!");
            return;
        }
        goodsService.updateMarketable(selectIds,status).then(function(res){
            layer.msg(res.data.message);
            if(res.data.success){
                $scope.reloadList();
            }
            layer.close(index);
        }).catch(err => console.log(err));
    }


});
