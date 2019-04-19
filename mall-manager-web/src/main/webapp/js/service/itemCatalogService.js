/**
 * 商品分类服务层
 * Created by Wwl on 2019/3/4
 */
app.service('itemCatalogService', function($http){

    //增加
    this.add = (entity) => $http.post('../itemCatalog/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../itemCatalog/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../itemCatalog/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../itemCatalog/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../itemCatalog/list.do');

    //分页查询
    this.findPage = (page,size) => $http.get('../itemCatalog/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../itemCatalog/search.do?page='+page+'&size='+size,searchEntity);

    //根据pId获得列表
    this.findByParentId = (parentId) => $http.get('../itemCatalog/findByParentId.do?parentId='+parentId);

});