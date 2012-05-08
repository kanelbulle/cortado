// EXT:ISC
// EXT:ICG
// EXT:LONG
// EXT:IWE
// EXT:NBD

// have this be execute



class fasdf
{
    public static void main(String[] sd)
    {
        int a;
        long[] asdf;

        fisk qwer;
        lol boll;
        
        boolean korv;
        long woop;



        qwer = new fisk();

        boll = qwer;                                        // test implicit casting upwards the hierarchy
        

        asdf = new long[2];
        asdf[0] = 33;

        a = 22;

        korv = true;


        woop = 4747474747l;
        
        while (a < 30)
        {
            long najs;
            int bopp;
            long rolf;


            bopp = 777777;
            rolf = 333333;

            najs = a + 33;
            korv = !korv;

            System.out.println(5656l);
            System.out.println(najs);
            System.out.println(korv);                       


            if (korv)
                System.out.println(asdf[1]);                // will print 0

            
            
            if (korv)
            {  
                System.out.println(a + qwer.fii(asdf));
            }
            else
            {
                System.out.println(9999);
                System.out.println(qwer.foo(3));
                System.out.println(boll.foo(2));
                System.out.println(8888);
                System.out.println(qwer.me().foo(100)); 

            }

            a = a + 1;
        }
        
    }
}

class fisk extends korv 
{
    public fisk me() 
    {
        System.out.println(1337);
        System.out.println(this.foo(2));
        return this;
    }
}


class korv extends lol
{
}


class lol 
{

    public long fii(long[] a) 
    {
        System.out.println(a.length);
        a[0] = a[0]*2;
        return 12l + 12 + a[0];
    }
    
    
    public int foo(int a) 
    { 
        int kor;
        long b;

        a = 23 + 1;
        b = a * a;                                          // a dead-code-finder should remove this AssignStmt
        
        
        return a+23;
    }
}
