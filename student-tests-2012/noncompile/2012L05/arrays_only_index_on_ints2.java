// EXT:LONG

class fisk
{
    public static void main(String[] woop)
    {
        int[] a;
        long[] b;

        a = new int[12];                                    // OK
        b = new long[12];                                   // ok

        System.out.println(a[0]);                           // ok
        System.out.println(b[0]);                           // ok

        System.out.println(a[0l]);                          // not ok, loses precision
        System.out.println(b[0l]);                          // same

    }
}
