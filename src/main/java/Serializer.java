import java.lang.reflect.Field;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.TreeMap;

public class Serializer {
    private Object object;
    private Class<?> cls;
    private TreeMap<String , Object> fieldData;
    private Field[] fields;
    public String getSerializedString() throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append('"');
        for(String fname : fieldData.keySet()){
            if(stringBuilder.length() > 0) stringBuilder.append(',');
            Object val = fieldData.get(fname);
            stringBuilder.append(fname).append(":").append(val.toString());
        }
//        stringBuilder.append('"');
        return stringBuilder.toString();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
        cls = object.getClass();
        fieldData = new TreeMap<>();
        fields = cls.getDeclaredFields();
        for(Field field : fields){
            String fname = field.getName();
            Rename annotation = field.getAnnotation(Rename.class);
            if(annotation != null && annotation.name().length() >0) fname = annotation.name();
            field.setAccessible(true);
            Object value = null;
            try {
                 value= field.get(object);
            } catch (IllegalAccessException ignored) {}
            fieldData.put(fname, value);
        }
    }
}