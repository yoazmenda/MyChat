import com.company.BeanFactory;
import com.company.annotations.Component;
import com.company.annotations.Inject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Component
public class BeanFactoryTests {

    @Test
    public void testRandomGeneratl(){
        BeanFactory beanFactory = new BeanFactory("com.company");
        int beanCount = beanFactory .getBeanCount();
        assertEquals(2, beanCount);


    }

}