// EXT:CNE
// EXT:NBD

class testcnenbd
{
    public static void main(String[] args)
    {
        System.out.println((new FactorialLitterFaculty()).run(10));
    }
}



class FactorialLitterFaculty
{
    public int run(int n)
    {
        int acc;

        acc = 1;
        while (n != 0)
        {
            int shoopwoop;
            int rolf;

            rolf = 1;
            shoopwoop = n*acc;
            n = n - rolf;
            acc = shoopwoop;
        }

        return acc;
    }
}

