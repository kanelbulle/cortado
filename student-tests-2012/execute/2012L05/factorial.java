// EXT:CEQ

class factorial
{
    public static void main(String[] args)
    {
        System.out.println((new FactorialFaculty()).run(10));
        System.out.println((new FactorialTrecFaculty()).run(10));
        System.out.println((new FactorialIterFaculty()).run(10));
    }
}

// punnity pun
class FactorialFaculty
{
    public int run(int n)
    {
        int result;

        if (n == 0)
            result = 1;
        else
            result = n * this.run(n-1);

        return result;
    }
}


// Now this won't go being TCO:ed or anything (in fact since we can't issue a return inside an if-statement in minijava this
// "optimization" of scheme fame is even nigh-impossible (though a sufficiently smart compiler just might might... ... (although
// the optimization is probably against java semantics to begin with))). But I still think it makes an interesting test since it
// involves more parameters and all.
class FactorialTrecFaculty
{
    public int run(int n)
    {
        return this.woop(n, 1);
    }

    public int woop(int n, int acc)
    {
        int result;

        if (n == 0)
            result = acc;
        else
            result = this.woop(n-1, n*acc);

        return result;
    }

}


class FactorialIterFaculty
{
    int acc;                                                // try instance variables for real?

    public int run(int n)
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

