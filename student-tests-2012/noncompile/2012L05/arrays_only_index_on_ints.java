// EXT:LONG

class fisk
{
    public static void main(String[] woop)
    {
        int[] a;
        long[] b;

        a = new int[12];                                    // OK
        b = new long[12];                                   // ok

        a = new int[33l];                                   // not ok (lose precision)
        b = new long[33l];                                  // not ok (lose precision)

    }
}
