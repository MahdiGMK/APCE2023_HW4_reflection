
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class DeserializeTest {
    YourConvertor convertor;

    @BeforeEach
    public void setUp() {
        convertor = new YourConvertor();
    }

    @Test
    public void deserializeWithPrimitives() {
        String json = "{\"power\":5,\"gunName\":\"colt\"}";
        Object tor = convertor.deserialize(json, "model.Gun");
        assertTrue(tor instanceof Gun);
        Gun torr = (Gun) tor;
        assertEquals(torr.getPower(), 5);
        assertEquals(torr.getGunName(), "colt");
    }

    @Test
    public void deserializeWithArray() {
        String json = "{\"countOfDailyKilled\":[500,600,700],\"totalCount\":1800}";
        Object tor = convertor.deserialize(json, "model.Diary");
        assertTrue(tor instanceof Diary);
        Diary torr = (Diary) tor;
        assertEquals(torr.getTotalCount(), 1800);
        ArrayList<Integer> expectedArray = new ArrayList<>(Arrays.asList(500, 600, 700));
        assertEquals(torr.getCountOfDailyKilled().size(), expectedArray.size());
        for (int i = 0; i < expectedArray.size(); i++) {
            assertEquals(torr.getCountOfDailyKilled().get(i), expectedArray.get(i));
        }
    }

    @Test
    public void deserializeWithObject() {
        String json = "{\"john\":{\"name\":\"Wick\"}}";
        Object tor = convertor.deserialize(json, "model.John");
        assertTrue(tor instanceof John);
        John torr = (John) tor;
        assertEquals(torr.john.getName(), "Wick");
    }

    @Test
    public void deserializeToInheritClass() {
        String json = "{\"strength\":100,\"activateKey\":\"j\",\"apGrade\":9.9}";
        Object tor = convertor.deserialize(json, "model.JohnWickChild");
        assertTrue(tor instanceof JohnWickChild);
        JohnWickChild torr = (JohnWickChild) tor;
        assertEquals(torr.apGrade, 9.9, 0.1);
        assertEquals(torr.strength, 100);
        assertEquals(torr.activateKey, 'j');
    }

    @Test
    public void deserializeWithCustomSetter() {
        String json = "{\"text\":\"fKBBA\u000EyA\\BJ\u000F\"}";
        Object tor = convertor.deserialize(json, "model.Message");
        assertTrue(tor instanceof Message);
        Message torr = (Message) tor;
        assertEquals(torr.getText(), "Hello World!");
    }
}
