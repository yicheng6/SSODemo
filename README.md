# SSODemo

springmvc＋velocity下实现相同二级域名下的单点登录。

要点：cookie不能跨二级域名，可以跨三级域名；利用重定向及cookie实现单点登录。

mac下修改Hosts  
sudo vi /etc/hosts  
127.0.0.1	app.yicheng6.com  
127.0.0.1	web.yicheng6.com  
127.0.0.1	passport.yicheng6.com 

2015日12月13日版本v1：  
实现应用系统Filter重定向认证系统及配置（目前以请求参数方式携带）；  
实现认证系统登陆认证跳转（目前使用重定向，携带信息使用RedirectAttributes－springmvc3.1以上通过容器中的flashmap在servlet的生命周期始末分别获取、存储session信息）。  
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－  
v1.1(2015/12/16)  
改进认证系统认证失败后跳转携带信息方式（采用转发方式实现）；  
实现登录后cookie的基本管理；  
参考tomcat生成JSessionId源码实现cookie中认证Id的随机生成：  
  ＊使用ConcurrentLinkedQueue<SecureRandom>线程安全的生成随机字节组；  
  ＊通过位运算将字节组转换为16进制形式的字符串；  
  ＊生成时通过tokens.containsKey(tokenKey)循环判断生成随机数是否重复，保证唯一性。  
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－  
2015日12月20日版本目标：  
实现cookie管理认证；  
改进应用系统重定向时携带最终url方式（之后考虑放入Header中）；  
改进认证系统认证失败后跳转携带信息方式（考虑使用转发方式实现）。  

参考：基本J2EE实现单点登录方法－－http://blog.csdn.net/ghsau/article/details/20466351
