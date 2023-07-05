import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializeTest {
    YourConvertor convertor;

    @BeforeEach
    public void setUp() {
        convertor = new YourConvertor();
    }

    @Test
    public void serializeWithPrimitives() {
        Gun obj = new Gun(5, "colt");

        String json = "{\"gunName\":\"colt\",\"power\":5}";
        String tor = convertor.serialize(obj);
        assertEquals(tor, json);
    }

    @Test
    public void serializeWithArray() {
        Diary obj = new Diary(new ArrayList<>(Arrays.asList(500, 600, 700)), 1800);

        String json = "{\"countOfDailyKilled\":[500,600,700],\"totalCount\":1800}";
        String tor = convertor.serialize(obj);
        assertEquals(tor, json);
    }

    @Test
    public void serializeWithObject() {
        John obj = new John(new John.Wick("Wick"));
        String json = "{\"john\":{\"name\":\"Wick\"}}";
        String tor = convertor.serialize(obj);
        assertEquals(tor, json);
    }

    @Test
    public void serializeFromInheritClass() {
        JohnWickChild obj = new JohnWickChild(9.9F, 100, 'j');
        String json = "{\"activateKey\":\"j\",\"apGrade\":9.9,\"strength\":100}";
        String tor = convertor.serialize(obj);
        assertEquals(tor, json);
    }

    @Test
    public void serializeWithCustomGetter() {
        EncryptedMessage obj = new EncryptedMessage("Hello World!");
        String json = "{\"text\":\"fKBBA\u000EyA\\BJ\u000F\"}";
        String tor = convertor.serialize(obj);
        assertEquals(tor, json);
    }
}
