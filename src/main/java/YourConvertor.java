import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class YourConvertor{
    public Object deserialize(String input, String className) {
        try {
            Class<?> cls = Class.forName(className);
            Constructor<?> constructor = cls.getConstructor();
            constructor.setAccessible(true);
            Object obj = constructor.newInstance();
            ArrayList<Field> fields = new ArrayList<>(Arrays.asList(cls.getDeclaredFields()));
            if(cls.getSuperclass() != Object.class)
                fields.addAll(Arrays.asList(cls.getSuperclass().getDeclaredFields()));
            Collections.sort(fields , Comparator.comparing(Field::getName));
            int pos = 1;
            for(Field field : fields){
                pos += deserializeField(obj, input.substring(pos));
            }
            return obj;
        }
        catch (Exception ignored){
            return null;
        }
    }
    private int deserializeField(Object obj , String str) throws InvocationTargetException, IllegalAccessException{
        Class<?> cls = obj.getClass();

        Object value = null;
        int f = str.indexOf(":") + 1;
        int t = str.indexOf(",") - 1;
        if(t < 0) t = str.indexOf("}") - 1;
        String name = str.substring(1 , f - 2);
        Field field = null;
        try {
            field = cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            try {
                field = cls.getSuperclass().getDeclaredField(name);
            } catch (NoSuchFieldException ex) {
                throw  new RuntimeException(ex);
            }
        }

        //"name":1,
        //"name":"c",
        //"name":"str",
        //"name":{...},

        if(field.getType() == int.class){
            value = Integer.parseInt(str.substring(f , t + 1));
        } else if(field.getType() == float.class){
            value = Float.parseFloat(str.substring(f , t + 1));
        } else if(field.getType() == char.class){
            value = str.charAt(f + 1);
        } else if(field.getType() == String.class) {
            value = str.substring(f +1, t);
        } else if(field.getType() == ArrayList.class){
            value = new ArrayList<>();
            t = str.indexOf("]");
            String[] parts = str.substring(f + 1,t ).split(",");
            for(String part : parts){
                ((ArrayList)value).add(Integer.parseInt(part));
            }
        }else{
            t = str.indexOf("}");
            value = deserialize(str.substring(f , t+1) , field.getType().getName());
        }
        System.out.printf("%s : %s\n" , name , str.substring(f , t + 1));

        String setterName = "set"+field.getName().substring(0, 1).toUpperCase() +field.getName().substring(1);
        Method setter = null;
        try {
            setter = cls.getMethod(setterName ,field.getType());
            setter.setAccessible(true);
        } catch (NoSuchMethodException ignored) {}

        field.setAccessible(true);
        if(setter != null) setter.invoke(obj, value);
        else field.set(obj, value);
        return t + 2;
    }

    public String serialize(Object input) {
        StringBuilder builder = new StringBuilder();
        try {
            serialize(input, builder);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
    private void serialize(Object obj , StringBuilder builder) throws IllegalAccessException {
        Class<?> cls = obj.getClass();
        if(obj instanceof Number){
            builder.append(obj.toString());
            return;
        }
        if(cls == Character.class){
            builder.append("'"+obj.toString()+"'");
            return;
        }
        if(cls == String.class){
            builder.append('"'+obj.toString()+'"');
        }
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(cls.getDeclaredFields()));
        if(cls.getSuperclass() != Object.class)
        fields.addAll(Arrays.asList(cls.getSuperclass().getDeclaredFields()));
        Collections.sort(fields, Comparator.comparing(Field::getName));
        builder.append("{");
        for (int i = 0; i < fields.size(); i++) {
            try {
                serializeField(obj , fields.get(i), builder);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            if(i != fields.size() - 1) builder.append(',');
        }
        builder.append("}");
    }
    private void serializeField(Object obj , Field field , StringBuilder builder) throws IllegalAccessException, InvocationTargetException {
        Class<?> cls = obj.getClass();
        field.setAccessible(true);
        System.out.println();
        String getterName = "get"+field.getName().substring(0, 1).toUpperCase() +field.getName().substring(1);
        Method getter = null;

        try {
            getter = cls.getMethod(getterName);
        } catch (NoSuchMethodException ignored) { }
        builder.append('"'+field.getName() + '"').append(":");

        if(getter != null)
            getter.setAccessible(true);

        Object value = null;
        if(getter != null) value = getter.invoke(obj);
        else value = field.get(obj);

        if(field.getType() == int.class)
            builder.append(value);
        else if(field.getType() == float.class)
            builder.append(value);
        else if(field.getType() == char.class)
            builder.append('"' + value.toString() + '"');
        else if(field.getType() == String.class)
            builder.append('"' + value.toString() + '"');
        else if(field.getType() == ArrayList.class) {
            ArrayList<?> arr = (ArrayList<?>) value;
            serializeArrayList(arr ,builder);
        }else
            serialize(field.get(obj), builder);
        System.out.println(field.getType());
    }

    private void serializeArrayList(ArrayList<?> arr, StringBuilder builder) throws IllegalAccessException {
        builder.append('[');
        boolean comma = false;
        for(Object obj : arr) {
            if(comma) builder.append(',');
            comma = true;
            serialize(obj, builder);
        }
        builder.append(']');
    }
}