import org.junit.jupiter.api.Test;

public class tst {
    @Test
    void strTest(){
        String str = "salasm:chetori, khoobi?";
        System.out.println(str.length());
        System.out.println(str.indexOf(":"));
        System.out.println(str.indexOf("s"));
        System.out.println(str.indexOf(","));
        System.out.println(str.indexOf("{"));
    }
}
