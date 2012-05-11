// EXT:LONG

class wappity
{
    public static void main(String[] args)
    {
        int a;
        long b;

        a = 33;                                             // same type
        b = 123;                                            // shall work upcast
        b = 123l;                                           // same type
        a = b;                                              // nope: lose preicision
        a  = 0l;                                            // Might seem too work but nope: lose precision


    }

}
