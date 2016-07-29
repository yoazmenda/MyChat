
import com.MyBeanFactory.A;
import com.MyBeanFactory.BeanFactory.ApplicationContext;
import com.MyBeanFactory.D;
import com.MyBeanFactory.annotations.Component;
import org.apache.http.client.HttpClient;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Component
public class MyChatTests{

    private static HttpClient httpClient;

    @BeforeClass
    public static void setupClass() throws InvocationTargetException {

    }

    @Test
    public void testRandomGeneratl(){
        ApplicationContext ctx = new ApplicationContext("com.MyBeanFactory");
        int beanCount = ctx.getBeanCount();
        assertEquals(4, beanCount);


    }

}