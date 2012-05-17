// EXT:LONG
// EXT:BDJ
// EXT:CEQ

class fib
{
    public static void main(String[] args)
    {
        // This particular line doesn't work with my minijava... Should it work for minijavas that implement long?
        // System.out.println((new fibbery()).fib(38));
        System.out.println((new fibbery()).fib(38l));

    }
}

class fibbery
{
    public long fib(long n)
    {
        long result;

        if (n == 0 || n == 1l)                 // mixing a bit between the literals to have this test also test implicit casting
            result = n;
        else
            result = this.fib(n-1) + this.fib(n-2l);

        return result;
    }

}
