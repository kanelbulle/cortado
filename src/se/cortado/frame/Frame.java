package se.cortado.frame;

import se.cortado.ir.temp.*;
import se.cortado.ir.tree.*;

import java.util.List;


/**
   Interface for handling of frames on the runtime stack. The
   interface is independent of the target architecture, but it is
   designed for real processors. For virtual machines, use the @see
   VMFrame interface.
   
   Basically this is a holder for
	• the locations of all the formals,
	• instructions required to implement the “viewshift,”
	• the number of locals allocated so far,
	• and the label at which the function’s machine code is to begin
*/
public interface Frame
{

	public Frame newFrame(Label name, List<Boolean> formals);
	
    public Record newRecord(String name);
    
    
    /**
       The label corresponding to the assembler entry point
       for the function using the frame.
       @return A Label object that contains the label.
    */
    public Label name();

    /**
       @return The size of the frame in bytes.
    */
    public int size();

    /**
       Returns a list of Access objects, one for each formal 
       parameter. The first Access object correspons to the
       first formal etc.

       @return A List of Access objects.
    */
    public List<Access> formals();

    /**
       Allocates an Access object corresponding to a local
       variable in the function. The Access objects may
       be allocated in any order, but all formal variables
       must be allocated before any local variable is
       allocated.

       @param escape A boolean variable stating if the function
       parameter escapes outside the function. Typically, a
       non-escaping parameter may be put in a register

       @return An Access object that describes how the corresponding
       local variable is accessed from within the function.
    */
    public Access allocLocal(boolean escape);

    /**
       Returns an Access object that can be used to access
       an outgoing parameter. Such an object comes in handy
       in code generation.

       @param index The index (starting from zero) of the parameter.
    */
    public Access accessOutgoing(int index);

    /**
       Encapsulates naming conventions for external functions,
       calling conventions, etc.

       @param func The name of the external function as seen
       from within MiniJava.

       @param args The arguments to the function.

       @return Tree code that calls the external function.
    */
    public IR_Exp externalCall(String func, IR_ExpList args);

    /**
       Appends tree code to move incoming arguments into the
       places where the function body is expecting them to be.

       @param body The function body, including code to move
       the return value into the RV register.

       @return Tree code that first moves incoming arguments
       to their correct places and then executes the function
       body.
    */
    public IR_Stm procEntryExit1(IR_Stm body);

    /**
       Appends a "sink" Assem instruction that "uses" special registers
       i.e., framepointer, retur value register etc.

       @param inst The list of instructions to which the sink instruction
       should be appended.

       @return The modified List with the sink instruction.
    */
    public List<se.cortado.assem.Instr> procEntryExit2(List<se.cortado.assem.Instr> inst);

    /**
       Produces first part of prologue:
       pseudo instructions to announce procedure start
       a label for the procedure name
       instruction to adjust stack pointer

       Also produces last part of epilogue:
       Instruction to reset stack pointer
       The return instruction
       pseudo instructions to indicate procedure end

       @param inst The list of instructions to which the sink instruction
       should be appended.

       @return a Proc object
    */
    public Proc procEntryExit3(List<se.cortado.assem.Instr> body);
    

    /**
       @return The return value register register.
    */
    public Temp RV();

    /**
       @return The frame pointer register.
    */
    public Temp FP();

    /**
       @return The machine's natural word size.
    */
    public int wordSize();

    /**
       @return The translation of a tree.Stm into a list of assem.Instr.
    */
    public abstract List<se.cortado.assem.Instr> codegen(se.cortado.ir.tree.IR_Stm stm);

    /**
       @return A TempMap mapping Temp:s corresponding to hardware 
       registers to their names, represented as String:s.
    */
    public TempMap initial();
    
    /**
       @return A TempList corresponding to the registers of the 
       underlying hardware. Note that only registers that are to be
       used at register allocation are returned. This normally excludes 
       the frame pointer and the stack pointer, for instance.
    */
    public TempList registers();
    
};
