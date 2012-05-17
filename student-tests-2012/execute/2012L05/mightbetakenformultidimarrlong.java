// EXT:LONG

// should compile and output 0 (arrays are initialized to this)

class zxcv
{
    public static void main(String[] args)
    {
        long asdf;

        asdf = (new long[12])[7];
        System.out.println(asdf);
    }
}
