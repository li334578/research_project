# JVM

```
-Xms -Xmx
jps
jinfo -flags pid
jmap
```

JDK JRE JVM 三者关系

class File -> class loader

运行时数据期 方法区 堆(heap) Java栈 本地方法栈 程序计数器

执行引擎

**栈存储什么？**

局部变量表：输入参数和输出参数以及方法内的变量类型；局部变量表在编译旗舰完成分配。当进入方法时，这个方法在帧中分配多少内存是固定的。

栈操作：记录出栈、入栈的操作。

动态链接

方法出口

**方法区**

方法区被所有线程共享，所有字段和方法字节码，以及一些特殊方法如构造函数，接口代码也在此定义。简单说，所有定义的方法信息都保存在该区域，此区域属于共享区间。

- 类信息

  - 类的版本

  - 字段

  - 方法

  - 接口

- 静态常量

- 常量

- 类信息(构造方法/接口定义)

- 运行时常量池

  

## 为什么可以使用Object类



## Java的启动类是哪个



## 双亲委派和沙箱安全机制



## native->thread->start->start0->JNI



## PC寄存器->存储指令地址



# 对象创建的方式（3/5种）

new

clone

反射 getInstence();



**对象的结构**

- header 对象头

  - 自身运行时数据(Mark Word)

  - hash值

  - GC分代年龄

  - 锁状态太标志

  - 线程持有锁

  - 偏向线程ID

  - 偏向时间戳
- 类型指针
- 数组长度(只有数组对象才有)
- InstanceData
- 相同宽度的数据被分配到一起(long,double)
- padding对齐
- 八个字节的整数倍

# 垃圾回收

## 判断对象是否是垃圾

1.引用计数法

2.可达性分析法GCRoot

## 回收算法

- 标记清除
- 复制
- 标记整理(标记压缩)
- 标记清除压缩
## 逃逸分析和栈上分配
- 逃逸分析


# 调优

```
jps
 java process status
 jps -l 主类全名
 jps -m 运行传入主类的参数
 jps -v 虚拟机参数
jstat
 jstat -gcutil pid 1000
 类加载、内存、垃圾收集、jit编译信息
jinfo
 实时调整和查看虚拟机参数
 -XX:[x/-]option
 -XX:option=value
jmap
 jmap -dump:formart=b,file=filepath pid
 jmap -histo pid
jhat
 JVM heap analysis Tool
jstack
 jstack -l pid > xxx.txt

jconsole

printf %x 10

-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/base
```

