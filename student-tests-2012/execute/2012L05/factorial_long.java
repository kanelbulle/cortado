// EXT:LONG
// EXT:CEQ

class factorial
{
    public static void main(String[] args)
    {
        System.out.println((new FactorialFaculty()).run(20l));
        System.out.println((new FactorialTrecFaculty()).run(20l));
        System.out.println((new FactorialIterFaculty()).run(20l));

    }
}

class FactorialFaculty
{
    int shoop;
    long lawl;
    int woop;

    public long run(long n)
    {
        int blah;
        long result;
        int potato;

        potato = 0 - 3487342; // This is here to eventually mess up bad long handling were ints and longs may overlap in space
        blah = 0 - 222;

        lawl = 2323223l;                                    // Testing out instance-variables

        if (n == 0)
            result = 1;
        else
            result = n * this.run(n-1);

        lawl = result;                                      // Testing out instance-variables

        shoop = 34 + potato;                          // Testing out instance-variables some more (this is in reality dead code)
        woop = shoop*2;

        return lawl;
    }
}




// Now this won't go being TCO:ed or anything (in fact since we can't issue a return inside an if-statement in minijava this
// "optimization" of scheme fame is even nigh-impossible (though a sufficiently smart compiler just might might... ... (although
// the optimization is probably against java semantics to begin with))). But I still think it makes an interesting test since it
// involves more parameters and all.
class FactorialTrecFaculty
{
    public long run(long n)
    {
        return this.woop(n, 1l);
    }

    public long woop(long n, long acc)
    {
        long result;

        if (n == 0)
            result = acc;
        else
            result = this.woop(n-1l, n*acc);

        return result;
    }

}


class FactorialIterFaculty
{
    long acc;                                               // Try instance variables for real?

    public long run(long n)
    {
        acc = 1;
        while (!(n == 0))
        {
            acc = n*acc;
            n = n - 1;
        }

        return acc;
    }
}

