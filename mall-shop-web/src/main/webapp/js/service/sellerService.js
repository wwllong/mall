/**
 * 卖家服务层
 * Created by Wwl on 2019/4/18
 */
app.service('sellerService', function($http){

    //增加
    this.add = (entity) => $http.post('../seller/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../seller/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../seller/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../seller/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../seller/list.do');

    //分页查询
    this.findPage = (page,size) => $http.get('../seller/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../seller/search.do?page='+page+'&size='+size,searchEntity);

});