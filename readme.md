程序存在的问题
新增员工时，创建人id和修改人id设置为了固定值

在获取token的时候，将empid放入了生成jwt令牌所需要的参数
接着在jwt方法中解析token，在拦截器的位置去获取empid，
问题：这个empid在很多类中都需要使用，所以通过ThreadLocal来实现这个功能
### ThreadLocal介绍

ThreadLocal并不是一个线程，而是线程里的一个局部变量
线程中有ThreadMap里面存放是当前线程Threadlocal的数据
当调用threadlocal的set方法就是从threadmap里去取出entry（key：threadlocal,value object）里的值
相当于threadlocal是操作threadmap的一个工具类哦

![image-20240307171705368](C:\Users\AEPO\AppData\Roaming\Typora\typora-user-images\image-20240307171705368.png)

4.当一个共享变量是共享的，但是需要每个线程互不影响，相互隔离，就可以使
用ThreadLocal
a·跨层传递信息的时候，每个方法都声明一个参数很麻烦，A1B\C\D3个类互相传递，
每个方法都声明参数降低了维护性，可以用一个ThreadLoca共享变量，在A存值，
BCD都可以获取。
b.隔离线程，存储一些线程不安全的工具对象，如(SimpleDateFormat)
c.spring中的事务管理器就是使用的ThreadLocal（所以多线程声明式事务就不能达到事务的一致性）
d.springmvc的HttpSession、HttpServletReuqest、HttpServletResponse都是放在
ThreadLocal,因为servlet是单例的，而springmvc允许在controller类中通过
@Autowiredi配置request、response**每个请求需要相对于的request和response所以使用threadlocal可以保持线程安全**requestcontexts等实例对象。底层就是搭配
Threadlocal才实现线程安全。

