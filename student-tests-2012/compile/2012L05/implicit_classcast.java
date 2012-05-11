// EXT:ISC
// EXT:ICG

class wrwerwer
{
    public static void main(String[] args)
    {
        sup a;
        boppity b;

        a = new sup();                                      // ok. same type
        a = new boppity();                                  // ok. casting upwards the hierarchy
        b = new boppity();                                  // ok. same type


    }
}

class sup
{
}

class boppity extends sup
{
}



