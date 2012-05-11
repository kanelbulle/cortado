// EXT:IWE

class Main {
    public static void main(String[] args) {
	Dog d;
	Foo foo;
	boolean b;
	Flipper f;
	Flupper f2;
	int a;
	f = new Flipper();
	f2 = new Flupper();
	System.out.println(f2.flipp(f));
	System.out.println(f2.flupp());
	System.out.println(f2.flipp(f));
	System.out.println(f2.flupp());

	d = new Dog();
	foo = new Foo();
	System.out.println(d.bark(7)); //Should print 1337
	if(false && d.roll(4)){
		System.out.println(5);
	}
	if(d.roll(6) && false){ //Should print 6
		System.out.println(7);
	}
	if(1<0){
		System.out.println(33);
	}
	if(1<1){
		System.out.println(34);
	}
	if(0<1){
		System.out.println(35);//Should print 35
	}

	System.out.println(foo.init()); //Should print 0
	if(foo.toggle()){ b = foo.toggle(); }
	if(!foo.toggle()){ b = foo.toggle(); }
	if(true && foo.toggle()){ b = foo.toggle(); }
	if(false && foo.toggle()){ b = foo.toggle(); }
	if(foo.toggle() && false){ b = foo.toggle(); }
	if(!foo.toggle() && foo.toggle()){ b = foo.toggle(); }
	System.out.println(foo.getCount());//Should print 8

    }
}
/* This is a class */
class Dog {
	public int bark(int volume) {
		return 1330+volume;
	}
	public boolean roll(int laps){
		System.out.println(laps);
		return true;
	}
}
/* This is also a class */
class Foo {
	int count;
	boolean t;
	public int init(){
		count = 0;
		t = false;
		return count;
	}

	public boolean toggle(){
		t = !t;
		count = count + 1;
		return t;
	}

	public int getCount(){
		return count;
	}
}
/* This is also another class */
class Flupper {
	Flipper flip;
	int i;
	public int flipp(Flipper fl){
		flip = fl;
		i = flip.setNum(1);
		return flip.getNum();
	}

	public int flupp(){
		flip = new Flipper();
		i = flip.setNum(5);
		return flip.getNum();
	}
}
/* This is a struct. No, I  joke. It's a class. */
class Flipper {
	boolean c;
	int b;
	public int getNum() {

		return b;
	}

	public int setNum(int n){
		b = n;
		return b;
	}
}
