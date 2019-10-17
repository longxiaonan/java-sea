单机环境下我们可以通过JAVA的Synchronized和Lock来实现进程内部的锁，但是随着分布式应用和集群环境的出现，系统资源的竞争从单进程多线程的竞争变成了多进程的竞争，这时候就需要分布式锁来保证。 实现分布式锁现在主流的方式大致有以下三种 1. 基于数据库的索引和行锁 2. 基于Redis的单线程原子操作：setNX 3. 基于Zookeeper的临时有序节点




参考：
redis分布式锁参考：
https://juejin.im/post/5d077d04f265da1b5f265661
https://juejin.im/post/5b7bcd7ce51d4538826f4684
zookeeper分布式锁参考：
http://www.dengshenyu.com/java/分布式系统/2017/10/23/zookeeper-distributed-lock.html
https://juejin.im/post/5b7ccea3e51d4538c77a810c#heading-10
http://sadwxqezc.github.io/HuangHuanBlog/middleware/2017/02/08/Zookeeper%E7%AC%94%E8%AE%B0.html
