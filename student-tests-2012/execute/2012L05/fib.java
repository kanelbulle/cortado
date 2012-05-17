// EXT:BDJ
// EXT:CEQ

class fib
{
    public static void main(String[] args)
    {
        System.out.println((new fibbery()).fib(38));
    }
}

class fibbery
{
    public int fib(int n)
    {
        int result;

        if (n == 0 || n == 1)
            result = n;
        else
            result = this.fib(n-1) + this.fib(n-2);

        return result;
    }

}
