import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SerializerTest {
    private Serializer serializer;
    private Random random;

    @BeforeEach
    public void setUp() throws Exception {
        serializer = new Serializer();
        random = new Random();
    }

    private String randomString() {
        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    @Test
    public void initialNullTest() {
        assertNull( serializer.getObject());
    }

    @Test
    public void getterSetterTest() {
        Object o = new Object();
        serializer.setObject(o);
        assertEquals(o, serializer.getObject());
    }

    @Test
    public void getSerializedNoAnnotation() throws IllegalAccessException {
        int int1 = random.nextInt();
        int int2 = random.nextInt();
        double aDouble = random.nextDouble();
        String b1String = randomString();
        String b2String = randomString();
        Dummy dummy = new Dummy(int1, int2, aDouble, b1String, b2String);
        serializer.setObject(dummy);
        String serialized = serializer.getSerializedString();
        String expected =
                "aDouble:" + aDouble +
                        ",int1:" + int1 +
                        ",int2:" + int2 +
                        ",string1:" + b1String +
                        ",string2:" + b2String;
        assertNotNull(serialized);
        assertEquals(serialized, expected);
    }

    @Test
    public void getSerializedWithAnnotationWithoutRename() throws IllegalAccessException {
        int someInt = random.nextInt();
        int renamableInt = random.nextInt();
        String b1String = randomString();
        RenamableDummy dummy = new RenamableDummy(someInt, renamableInt, b1String);
        serializer.setObject(dummy);
        String serialized = serializer.getSerializedString();
        String expected =
                "renamableInt:" + renamableInt +
                        ",renamableString:" + b1String +
                        ",someInt:" + someInt;
        assertNotNull(serialized);
        assertEquals(serialized, expected);
    }

    @Test
    public void getSerializedWithAnnotationWithRename() throws IllegalAccessException {
        int someInt = random.nextInt();
        int renamableInt = random.nextInt();
        String b1String = randomString();
        RenamableDummy2 dummy = new RenamableDummy2(someInt, renamableInt, b1String);
        serializer.setObject(dummy);
        String serialized = serializer.getSerializedString();
        String expected =
                "renamedInt:" + renamableInt +
                        ",renamedString:" + b1String +
                        ",someInt:" + someInt;
        assertNotNull(serialized);
        assertEquals(serialized, expected);
    }
}