public class RenamableDummy {

    private int someInt;

    @Rename
    public int renamableInt;

    @Rename
    public String renamableString;

    public RenamableDummy(int someInt, int renamableInt, String renamableString) {
        this.someInt = someInt;
        this.renamableInt = renamableInt;
        this.renamableString = renamableString;
    }
}
