/**
 * 内容服务层
 * Created by Wwl 2019/4/24
 */
app.service("contentService", function ($http) {

    //根据分类ID查询广告列表
    this.findByCategoryId = (categoryId) => ($http.get('content/findByCategoryId.do?categoryId='+categoryId));

});