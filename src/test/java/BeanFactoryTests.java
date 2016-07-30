import com.company.BeanFactory;
import com.company.annotations.Component;
import com.company.annotations.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@Component
@RunWith(JUnit4.class)
public class BeanFactoryTests {

    @Test
    public void test1(){
        System.out.println("In test");
        BeanFactory beanFactory = new BeanFactory("com.company");
        int beanCount = beanFactory .getBeanCount();
        assertEquals(2, beanCount);


    }

}