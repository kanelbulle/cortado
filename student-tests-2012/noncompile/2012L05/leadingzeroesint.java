

// regular javac complains:
// <blah>: integer number too large: 0099
//        a = 0099 + 1;

// due to being interpreted as octal. now minijava doesn't support this


class burk
{
    public static void main(String[] sd)
    {
        int a;

        a = 0099 + 1;
    }
}
