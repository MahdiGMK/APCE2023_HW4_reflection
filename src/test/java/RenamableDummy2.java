public class RenamableDummy2 {
    private int someInt;

    @Rename(name = "renamedInt")
    public int renamableInt;

    @Rename(name = "renamedString")
    public String renamableString;

    public RenamableDummy2(int someInt, int renamableInt, String renamableString) {
        this.someInt = someInt;
        this.renamableInt = renamableInt;
        this.renamableString = renamableString;
    }
}
