// should compile and output 0 (arrays are initialized to this)

class zxcv
{
    public static void main(String[] args)
    {
        int asdf;

        asdf = (new int[12])[7];
        System.out.println(asdf);
    }
}
