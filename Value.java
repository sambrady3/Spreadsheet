public class Value {
  private double dval;
  private String sval;
  private String tag ;
  
  public Value() {
    dval = 0.0;
    sval = null;
    tag = "STRING";
  }
  
  //Constructor
  public Value(String s){
    //check if s starts with a quote, and if so remove it
    if(isQuote(s) == true){        
      s = s.substring(1);          
      this.sval = s;
      tag = "STRING";
    }
    else{
      dval = Double.parseDouble(s);
      tag = "DBL";
    }
  }
  
  //Checks if the first character of input is a quote, 
  //returns true or false. Helper method for Value constructor.
  public boolean isQuote(String s){
    return s.substring(0,1).equals("\"");
  }
  
  //Method to add two Values together
  public Value plus(Value v){
    Value newValue = new Value();
    //Check to ensure both Values have tag == DBL
    if(this.tag.equals("DBL") && v.tag.equals("DBL")){     
      newValue.dval = this.dval + v.dval;
      newValue.tag = "DBL";                                
    }
    //If both Values don't have DBL tags, don't do the addition
    //and set the new Value's tag to INVALID
    else
      newValue.tag = "INVALID";                            
    return newValue;                                       
  }
  
  //Method to subtract one Value from another. Same logic as plus()
  public Value minus(Value v){
    Value newValue = new Value();
    if(this.tag.equals("DBL") && v.tag.equals("DBL")){
      newValue.dval = this.dval - v.dval;
      newValue.tag = "DBL";
    }
    else
      newValue.tag = "INVALID";
    return newValue;
  }
  
  //Method to multiply two Values together. Same logic as plus()
  public Value star(Value v){
    Value newValue = new Value();
    if(this.tag.equals("DBL") && v.tag.equals("DBL")){
      newValue.dval = this.dval * v.dval;
      newValue.tag = "DBL";
    }
    else
      newValue.tag = "INVALID";
    return newValue;
  }
  
  //Method to divide one Value from another. 
  public Value slash(Value v){
    Value newValue = new Value();
    //Check both tags are DBL and divisor is not zero
    if(this.tag.equals("DBL") && v.tag.equals("DBL") && v.dval != 0){  
      newValue.dval = this.dval / v.dval;
      newValue.tag = "DBL";
    }  
    //If divisor is zero, print error statement, and don't attempt divison
    else if(v.dval == 0){                                 
      System.out.println("Attempted division by zero");
      newValue.tag = "INVALID";
    }
    else
      newValue.tag = "INCALID";
    return newValue;
  }
  
  // toString that checks tag, then truncates strings to 10 characters, 
  // and doubles to 10 digits with 4 decimal places.
  public String toString(){
    if(this.tag.equals("STRING"))
      return String.format("%10s", this.getSVal());
    else
      return String.format("%10.4f", this.getDVal());
  }
  
  public String getSVal(){
    if(this.sval == null)
      return "";
    return this.sval;
  }
  
  public Double getDVal(){
    return this.dval;
  }
  
  public String getTag(){
    return this.tag;
  }
}