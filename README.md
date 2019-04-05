#C2风格软件结构
##后面还会更新, 现在还不完整
1. 概述<br>
    1.1软件体系结构C2风格, 将各个构件用连接件连接起来, 协同计算, 为今后运用这种风格和分布式学习打好基础.<br>
    这种风格各构件的沟通就是通过消息.<br>
    1.2需求分析：<br>
    实现+，-，*，/,sin,cos 并根据汇编原理完成表达式的计算，比如求2+3*4，要求程序自动生成<br>
    3，4 *<br>
    Result， 2，+<br>
    1.3思考:<br>
    涉及到()处理<br> 
    输入的长度的处理<br>
    未输入正确的处理<br>
    1.4逻辑图<br>
    ![logic](document/image/logic.png)
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
    需要实现真正的分布式 前后没有联系的计算一起进行。<b>无法实现</b>，因为用编译的方法他会出错数据的返回时间不定, 只能实现流水式的并行。<br>
    进行复杂运算还没有完成,编译器运用还没完成。<b>已完成</b><br>
    sin cos。sin可以加括号 也可以不加<b>已完成</b><br>
    0.0.3优化程序<br>
7. 运行效果 <br>
   程序运行起来后，输入: 1*2+3*4 将会给出结果 14<br>
   输入: sin(30)  输出  0.499999999<br>
8. 软件测试 <br>
    白盒测试: 自动化测试工具（待续）<br>
    黑盒测试：<br>
    划分等价类<br>
    
    
|输入条件| 有效等价类 | 无效等价类|
|:------------:|:------------:|:------------:|
|计算方法| 1.+ 2.- 3.* 4./ 5.sin() 6.sin 7.cos() 8.cos  9.() | 无效的计算 13.arcsin 14.sqrt（） 15.^2|
|字符 | 10.数字和以上计算符号| 16.其他任意字符|
|进行运算个数| 11.1个及以上| 17.无输入运算|
|完整性|12.完整计算 | 18.不完整的计算例如 1+|

测试用例：<br>

|输入条件|预期输出|覆盖等价类|
|:------------:|:------------:|:------------:|
|sin30+sin（15+15）+6-cos60+cos(90-30)+5*6-30/5+(5*3)| 46.xx  46附近的浮点数| 1 2 3 4 5 6 7 8 9 10 12|
|arcsin（30）| 未输入或者未涉及此类运算，抱歉请重新输入!!!| 13|
|sqrt（）|未输入或者未涉及此类运算，抱歉请重新输入!!!| 14|
|^2|未输入或者未涉及此类运算，抱歉请重新输入!!!| 15|
|a12|未输入或者未涉及此类运算，抱歉请重新输入!!!| 16|
| |未输入或者未涉及此类运算，抱歉请重新输入!!!| 17|
|15+ |输入不完整| 18|
|15*5+7+|输入不完整| 19|
|sin(60|输入不完整| 20|
|sas(60|未输入或者未涉及此类运算，抱歉请重新输入!!!| 21|

    


