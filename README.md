#C2风格软件结构
##后面还会更新, 现在还不完整
1. 概述<br>
    软件体系结构C2风格, 将各个构件用连接件连接起来, 协同计算, 为今后运用这种风格和分布式学习打好基础.<br>
    这种风格各构件的沟通就是通过消息.<br>
2. 运用的工具和语言<br>
    中间件: activemq<br>
    java第三方类库: activemq-all-5.3.1.jar<br>
    语言: java jdk1.8<br>
3. 原则<br>
    所有构件与用连接件连接起来<br>
    连接件也可以互相连接起来<br>
4. 最终效果<br>
    实现分布式计算器<br>
5. 运用所学知识<br>
    5.1 编译原理<br>
    词法分析 语法分析 语义分析 中间代码生成 优化 目标代码生成<br>
    5.2 网络通信<br>
    在同一个局域网内:几台电脑连接在一个交换机(数据链路层)或者由一个路由器(网络层)或者连接到一个WiFi(网络层)收发的信号时,这几台电脑是在一个局域网内的,因此可以互相访问使用 ping 命令(ICMP报文: 回送请求和回答).<br>
    不在同一个局域网内: 简单的办法是可以关闭防火墙进行互联进行 ping. 复杂一点的办法可以配置反向代理(就是别人访问你的时候转到什么地方，而正向代理是你访问别人的时候)，开放activemq服务端口(61616)可以让别人访问.<br>
6. TODO<br>
    需要实现真正的分布式 前后没有联系的计算一起进行。<br>
    进行复杂运算还没有完成,编译器运用还没完成。<br>
    sin cos。<br>

