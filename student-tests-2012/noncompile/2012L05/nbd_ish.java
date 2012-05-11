// EXT:NBD

class asdf
{
    public static void main(String[] args)
    {
        int a;
        a = 11;
        {
            int b;

            b = 12;                                         // eeyup

            a = a+b;                                        // eeyup
        }

        b = 12;                                             // nnope
        a = a*a;                                            // eeyup

        {
            int c;

            c = 12;                                         // eeyup
            b = 12;                                         // nnope
            c = a + b +c;                                   // nnope
            c = a +c;                                       // eeyup
        }


        a = c;                                              // nnope


    }
}
