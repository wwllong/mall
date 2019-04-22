/**
 * 商品SPU服务层
 * Created by Wwl on 2019/4/18
 */
app.service('goodsService', function($http){

    //增加
    this.add = (entity) => $http.post('../goods/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../goods/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../goods/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../goods/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../goods/list.do');

    //分页查询
    this.findPage = (page,size) => $http.get('../goods/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../goods/search.do?page='+page+'&size='+size,searchEntity);

});