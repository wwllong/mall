/**
 * 用户服务层
 * Created by Wwl on 2019/3/4
 */
app.service('userService', function($http){

    //增加
    this.add = (entity,code) => $http.post('../user/add.do?code='+code,entity);

    //修改
    this.update = (entity) => $http.post('../user/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../user/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../user/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../user/list.do');

    //分页查询
    this.findPage = (page,size) => $http.get('../user/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.get('../user/search.do?page='+page+'&size='+size,searchEntity);

    //发送验证码
    this.sendCode = (phone) => $http.get('../user/sendCode.do?phone='+phone);


});