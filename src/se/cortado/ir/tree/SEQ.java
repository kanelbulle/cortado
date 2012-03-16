package se.cortado.ir.tree;
public class SEQ extends IR_Stm {
  public IR_Stm left, right;
  public SEQ(IR_Stm l, IR_Stm r) { left=l; right=r; }
  public IR_ExpList kids() {throw new Error("kids() not applicable to SEQ");}
  public IR_Stm build(IR_ExpList kids) {throw new Error("build() not applicable to SEQ");}
}

