package com.neo.java.spi;

import com.neo.java.spi.model.Search;
import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 测试java.util.ServiceLoader
 * 面向的对象的设计里，我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。
 为了实现在模块装配的时候能不在程序里动态指明，这就需要一种服务发现机制。java spi就是提供这样的一个机制：为某个接口寻找服务实现的机制。有点类似IOC的思想，

 就是将装配的控制权移到程序之外（配置文件），在模块化设计中这个机制尤其重要

 作者：dolphin叔叔
 链接：https://www.jianshu.com/p/5c3bb771dc5d
 來源：简书
 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。


 https://www.cnblogs.com/happyframework/archive/2013/09/17/3325560.html
 */
public class Test_01 {

    /**
     * resources/META-INF/services下新建接口全限定名的文件。文件里指定需要使用的实现类的权限顶名称
     */
    @Test
    public void test01() {
        ServiceLoader<Search> load = ServiceLoader.load(Search.class);

        Iterator<Search> iterator = load.iterator();

        while (iterator.hasNext()) {
            Search next = iterator.next();
            next.searchDoc("man");
        }
    }
}
