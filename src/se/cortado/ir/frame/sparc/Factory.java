package se.cortado.ir.frame.sparc;

import java.util.List;
import se.cortado.ir.temp.Label;

public class Factory implements se.cortado.ir.frame.Factory
{
	public Frame newFrame(Label name, List<Boolean> formals) { 
		return new Frame(name, formals);
	}

	public Record newRecord(String name) {
		return new Record(name);
	}




}




