# CPU

与门、或门、非门、异或门 ->基础逻辑电路。

加法器 累加器 锁存器...

实现手动计算(通电一次，运行一次位运算)

加入内存 实现自动运算(每次读取内存指令，(高电低电))

volatile 可见性 禁止指令重排

CUP和内存是计算机的核心。

指令计数器、寄存器、算数逻辑单元、缓存。

核有自己的L1、L2不同的核共享L3.所有区的共享内存

进程和线程的区别

进程是分配资源的单位

线程是最小的执行单位、共享线程的资源

纤程是线程的线程。  (fiber)

JVM中线程需要消耗1M左右内存跟os中的进程一对一的关系。

纤程的应用场景：用户的空间的异步编程

1.Callable + Future

2.Guava的扩展ListenableFuture

3.RxJava响应式编程

4.Quasar库(java agent)

5.Fiber

1.app发出0x80中断 或 调用sysenter原语

2.os进入内核态

3.在终端向量表中查找处理例程

4.保存硬件现场 CS IP 等寄存器的值

5.保存app现场 堆栈与寄存器的值

6.执行中断例程system_call

​	1.根据参数与编号寻找对应例程

​	2.执行并返回

7.恢复现场

8.返回用户态

9.app继续执行

# synchronized & volatile

## CAS

compare and swap

compare and exchange

比较并交换

ABA问题 其他线程多次修改最后值与原值相同。(加个version来对比)

native

汇编指令是lock cmpxchg 指令

```
markword 8个字节(记录了 锁信息、GC信息、HashCode)

klass pointer

m 四个字节

padding(对齐)


```

synchronized升级
偏向锁是伪锁态

自旋锁

只要有线程来抢占就会从偏向锁升级成自旋锁

偏向锁-markword上记录当前线程指针，下次同一个线程加锁的时候，不需要争用只需要判断线程指针是否为同一个，所以，偏向锁，偏向加锁的第一个线程。hashCode被分在线程栈上 线程销毁 锁降级为无锁。

有争用，锁升级为轻量级锁-每个线程有自己的LockRecord在自己的线程栈上，用CAS争用markword的LR的指针，指针指向那个线程的LR，哪个线程就拥有锁。

自旋超过10次，升级为重量级锁-如果太多线程自旋CPU消耗过大，不如升级为重量级锁，进入等待队列(不消耗CPU)-XX:PreBlockSpin

自旋锁在jdk1.4.2中引入，使用-XX:+useSpinning来开启，jdk6中变为默认开启，并且引入了自适应的自旋锁(适应性自旋锁)。

多个线程(超过CPU核数的1/2)在等待也会升级成自旋锁。

默认偏向锁有4秒延迟，有很多对象会抢占内存，不如直接上重量级锁。

匿名偏向。

`-xx:BiasedLockingStartupDelay=0`

`java -XX:+PrintFlagsFinal -version`

# volatile

线程可见性

禁止指令重排

```
lock用于在多处理器中执行指令时对共享内存的独占使用。
它的作用是能够将当前处理器对应缓存的内存刷新到内存，并使其他处理器对应的缓存失效
另外它还提供了有序的指令无法越过这个内存屏障的作用
```

































