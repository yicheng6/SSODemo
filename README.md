# SSODemo

springmvc＋velocity下实现相同二级域名下的单点登录。

要点：cookie不能跨二级域名，可以跨三级域名；利用重定向及cookie实现单点登录。

mac下修改Hosts
sudo vi /etc/hosts
127.0.0.1	app.yicheng6.com
127.0.0.1	web.yicheng6.com
127.0.0.1	passport.yicheng6.com

参考：http://blog.csdn.net/ghsau/article/details/20466351
