[TOC]





# 项目介绍

这是一个前后端分离的数独游戏练手项目，项目采用SpringBoot+Vue开发。



## 项目技术栈



### 后端

1. Spring Boot
2. Spring Security
3. MyBatis
4. MySQL
5. Redis
6. Spring Cache
7. ...



### 前端

1. vue
2. semanticUI
3. elementUI
4. jquery
5. axios
6. vue-router
7. vuex
8. vue-cli3
9. ...



# 快速部署



## 后端

1. 克隆项目到本地
2. 由于在项目中使用了`var`关键字，故要求$Java版本 \ge10$。若本机使用的是$8 \le java版本 \le 9$，可以手动将所有的`var`关键字改为其类型
3. 执行项目根目录下的`sudoku.sql`
4. 打开系统属性的环境变量，在**系统变量**中添加变量名为`SUDOKU_MYSQL_PASSWORD`，变量值为自己本地MySQL数据库的root密码，并**重启计算机**。若不想要这么麻烦，则在`resources`目录下的 `application.properties`文件中修改`datasource.password`为自己的root密码
5. 在`application.properties`文件中，配置自己的Redis
6. 启动MySQL和Redis
7. 在IDEA中启动`SudokuApplication`
8. 至此，服务端就启动成功了，此时在浏览器中输入 `http://localhost:8081` 即可访问该项目



## 前端

1. 克隆项目到本地
2. 进入到`sudoku-vue`目录中，在命令行依次输入如下命令

```
# 安装依赖
npm install
# 在localhost:8080启动项目
npm run serve
```

- 由于在项目中已经设置了端口转发，将数据转发到后端，因此项目启动之后，在浏览器中输入 `http://localhost:8080` 就可以访到前端项目（必须启动Spring Boot项目）

3. 修改完前端项目后，进入到`sudoku-vue`目录中，然后执行`npm run build`进行构建该项目
4. 该命令执行成功之后，`sudoku-vue`目录下会·生成一个`dist`文件夹，将该文件夹中所有文件复制到Spring Boot项目中`resources/static/`目录下，然后就可以启动Spring Boot项目进行访问了