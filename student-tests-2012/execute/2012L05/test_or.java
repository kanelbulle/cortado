// EXT:BDJ

class woop
{
    public static void main(String[] args) 
    {
            boolean korv;
            shoop woop;

            woop = new shoop();

            korv = woop.b() || woop.c() && woop.a() && woop.a();
            System.out.println(korv);
            System.out.println(woop.get());

            korv = woop.b() || woop.a() && woop.b() || woop.a();
            System.out.println(korv);
            System.out.println(woop.get());
    }
    
}


class shoop
{
    int a;

    public int get()
    {
        return a;
    }
    

    public boolean a()
    {
        a = a + 1;
        return true;
    }

    public boolean b()
    {
        a = 34;
        return false;
    }

    public boolean c()
    {
        a = a + 33;
        return false;
    }
    
}

    
