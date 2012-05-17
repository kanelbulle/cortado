// EXT:IWE

// a correct program prints nothing
// a faulty one prints 33
// an even faulter one (seriously, get your logic right!) might print 22
// a super-mega-ultra-faulty-one may do something else entirely.
// like print 42 or something

class iwe
{
    public static void main(String[] args)
    {
        if (false)
            if (true)
                System.out.println(22);
            else
                System.out.println(33);
    }
}
