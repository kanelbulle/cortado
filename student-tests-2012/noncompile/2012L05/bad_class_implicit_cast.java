// EXT:ISC

class wrwerwer
{
    public static void main(String[] args)
    {
        sup a;
        boppity b;

        a = new boppity();                                  // ok. casting upwards the hierarchy
        b = new boppity();                                  // ok. same type
        b = new sup();                                      // not ok


    }
}

class sup
{
}

class boppity extends sup
{
}



