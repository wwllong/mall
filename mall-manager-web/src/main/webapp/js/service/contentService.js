/**
 * 内容/广告服务层
 * Created by Wwl on 2019/3/4
 */
app.service('contentService', function($http){

    //增加
    this.add = (entity) => $http.post('../content/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../content/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../content/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../content/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../content/list.do');

    //分页查询
    this.findPage = (page,size) => $http.get('../content/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../content/search.do?page='+page+'&size='+size,searchEntity);

    //更新状态
    this.updateStatus = (ids,status) => $http.get('../content/updateStatus.do?ids='+ids+'&status='+status);
});