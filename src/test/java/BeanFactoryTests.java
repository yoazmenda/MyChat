import com.company.BeanFactory;
import com.company.annotations.Component;
import com.company.annotations.Inject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Component
public class BeanFactoryTests {

    @Component
    public class A{

        @Inject
        private B b;

    }

    @Component
    public class B{

    }

    @Component
    public class C{

    }

    @Component
    public class D{

    }


    @Test
    public void testRandomGeneratl(){
        BeanFactory beanFactory = new BeanFactory("com.company");
        int beanCount = beanFactory .getBeanCount();
        assertEquals(4, beanCount);


    }

}