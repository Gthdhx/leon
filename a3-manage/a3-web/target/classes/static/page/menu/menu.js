layui.use(['jquery','table','layer', 'form'], function(){

    var table = layui.table;

    var $=layui.jquery;

    var data = {};

    //第一个实例
    table.render({
        elem: '#menuList',
        height: "auto",
        url: '/menu/getMenuList', //数据接口
        method: 'GET',
        page: true, //开启分页,
        cols: [
            [ //表头
                {type: 'radio',field: 'id', title: 'ID', width:'5%', sort: true},
                {field: 'menuCode', title: '菜单编码', width:'10%'},
                {field: 'menuName', title: '菜单名称', width:'10%', sort: true},
                {field: 'parentCode', title: '上级菜单', width:'10%'},
                {field: 'sort', title: '排序', width: '10%', sort: true},
                {field: 'icon', title: '图标', width: '5%'},
                {field: 'path', title: '路由地址', width: '15%'},
                {field: 'menuLevel', title: '菜单等级', width: '10%', sort: true},
                {field: 'createTime', title: '创建时间', width: '15%', sort: true}
            ]
        ]
    });

    //监听行工具事件
    table.on('radio(menuList)', function(obj){
        data = obj.data; //获得当前行数据
    });

    $("#add").on("click",function () {
        openAddUser();
    });
    //打开添加页面
    function openAddUser(){
        layer.open({
            type: 2,
            area: ['50%','50%'],
            title: '编辑菜单',
            shadeClose: true,
            content: ['/menu/gotoMenuInsertPage']
        });
    }

    $("#edit").on("click",function () {
        if(data == null || JSON.stringify(data) === '{}'){
            layer.msg("请选择编辑菜单");
            return;
        }
        var id = data.id;
        editOpen(id);
    });

    function editOpen(id){
        layer.open({
            type: 2,
            area: ['50%','50%'],
            title: '编辑菜单',
            shadeClose: true,
            content: ['/menu/gotoMenuUpdatePage?id='+id]
        });
    }

});




