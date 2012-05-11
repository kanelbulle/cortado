// EXT:LONG

// should not compile

class abcd
{
    public static void main(String[] args) {
        long asdf;

        asdf = new long[12][7];
        System.out.println(asdf);
    }
}
