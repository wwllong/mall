/**
 * 品牌服务层
 * Created by Wwl on 2019/3/4
 */
app.service('brandService', function($http){

    //增加
    this.add = (entity) => $http.post('../brand/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../brand/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../brand/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../brand/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../brand/list.do');

    //分页查询
    this.findPage = (page,size) => $http.get('../brand/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../brand/search.do?page='+page+'&size='+size,searchEntity);

    //下拉列表数据
    this.findOptionList = () => $http.get('../brand/findOptionList.do');

});