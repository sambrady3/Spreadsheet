
public class Grid {
  private int rows = 10;
  private int columns = 6;
  private Node head;
  
  //Constructor: Makes a 10x6 grid of nodes, 
  //circularly linked with right and down pointers,
  //using arrays to assist in initial linking of nodes 
  public Grid(){
    //Create 2d-array and fill with Nodes
    Node[][] gridArray = new Node[rows][columns];
    for(int i = 0; i < rows; i++)
      for(int j = 0; j < columns; j++)
      gridArray[i][j] = new Node();
    head = gridArray[0][0];
    //Traverse 2d-array and set each Node's right and down pointers
    for(int i = 0; i < rows; i++)
      for(int j = 0; j < columns; j++){
      gridArray[i][j].setRight(gridArray[i][(j+1) % columns]);
      gridArray[i][j].setDown(gridArray[(i+1) % rows][j]);
    }
  }
  
  //Traverses to a given node from the head pointer.
  public Node traverse(int row, int col){
    Node iterator = head;
    for(int i = 0; i < col; i++)
      iterator = iterator.getRight();
    for(int i = 0; i < row; i++)
      iterator = iterator.getDown();
    return iterator;
  }
  
  //Creates a Value from given string, 
  //uses Traverse method to get to given cell, then setValue
  public void assignCell(int row, int col, String s){
    Value newValue = new Value(s);
    Node iterator = traverse(row,col);
    iterator.setValue(newValue);
  }
  
  //Creates a subgrid from beginning and ending coordinates, and fills cells in
  //a rectangular subgrid with a given value 
  public void fill(int fromRow, int fromCol, int toRow, int toCol, Value val){
    Node iterator = traverse(fromRow, fromCol);
    for(int j = fromRow; j <= toRow; j++){
      for(int i = fromCol; i <= toCol; i++){
        iterator.setValue(val);
        iterator = iterator.getRight();
      }
      for(int i = 0; i < (columns - (toCol - fromCol + 1)); i++)
        iterator = iterator.getRight();
      iterator = iterator.getDown();
    }
  }
  
  public void number(int fromRow, int fromCol, int toRow, int toCol){
    Node iterator = traverse(fromRow, fromCol);
    int val = 0;
    for(int j = fromRow; j <= toRow; j++){
      for(int i = fromCol; i <= toCol; i++){
        iterator.setValue(new Value(String.valueOf(val)));
        iterator = iterator.getRight();
        val++;
      }
      for(int i = 0; i < (columns - (toCol - fromCol + 1)); i++)
        iterator = iterator.getRight();
      iterator = iterator.getDown();
    }
  }
  
  public void addCells(int firstRow, int firstCol, int secondRow, 
                       int secondCol, int targetRow, int targetCol){
    Node first = traverse(firstRow, firstCol);
    Node second = traverse(secondRow, secondCol);
    Node target = traverse(targetRow, targetCol);
    Node result = new Node();
    result.setValue(first.getValue().plus(second.getValue()));
    if(result.getValue().getTag() != "INVALID")
      target.setValue(result.getValue());
  }
  
  public void subtractCells(int firstRow, int firstCol, int secondRow, 
                            int secondCol, int targetRow, int targetCol){
    Node first = traverse(firstRow, firstCol);
    Node second = traverse(secondRow, secondCol);
    Node target = traverse(targetRow, targetCol);
    Node result = new Node();
    result.setValue(first.getValue().minus(second.getValue()));
    if(result.getValue().getTag() != "INVALID")
      target.setValue(result.getValue());
  }
  
  public void multiplyCells(int firstRow, int firstCol, int secondRow, 
                            int secondCol, int targetRow, int targetCol){
    Node first = traverse(firstRow, firstCol);
    Node second = traverse(secondRow, secondCol);
    Node target = traverse(targetRow, targetCol);
    Node result = new Node();
    result.setValue(first.getValue().star(second.getValue()));
    if(result.getValue().getTag() != "INVALID")
      target.setValue(result.getValue());
  }
  
  public void divideCells(int firstRow, int firstCol, int secondRow, 
                          int secondCol, int targetRow, int targetCol){
    Node first = traverse(firstRow, firstCol);
    Node second = traverse(secondRow, secondCol);
    Node target = traverse(targetRow, targetCol);
    Node result = new Node();
    result.setValue(first.getValue().slash(second.getValue()));
    if(result.getValue().getTag() != "INVALID")
      target.setValue(result.getValue());
  }
  
  public void addRows(int firstRow, int secondRow, int targetRow){
    for(int i = 0; i < columns; i++){
      addCells(firstRow, i, secondRow, i, targetRow, i);
    }
  }
  
  public void subtractRows(int firstRow, int secondRow, int targetRow){
    for(int i = 0; i < columns; i++){
      subtractCells(firstRow, i, secondRow, i, targetRow, i);
    }
  }
  
  public void multiplyRows(int firstRow, int secondRow, int targetRow){
    for(int i = 0; i < columns; i++){
      multiplyCells(firstRow, i, secondRow, i, targetRow, i);
    }
  }
  
  public void divideRows(int firstRow, int secondRow, int targetRow){
    for(int i = 0; i < columns; i++){
      divideCells(firstRow, i, secondRow, i, targetRow, i);
    }
  }
  
  public void addColumns(int firstColumn, int secondColumn , int targetColumn){
    for(int i = 0; i < rows; i++){
      addCells(i, firstColumn, i, secondColumn, i, targetColumn);
    }
  }
  
  public void subtractColumns(int firstColumn, int secondColumn , int targetColumn){
    for(int i = 0; i < rows; i++){
      subtractCells(i, firstColumn, i, secondColumn, i, targetColumn);
    }
  }
  
  public void multiplyColumns(int firstColumn, int secondColumn , int targetColumn){
    for(int i = 0; i < rows; i++){
      multiplyCells(i, firstColumn, i, secondColumn, i, targetColumn);
    }
  }
  
  public void divideColumns(int firstColumn, int secondColumn , int targetColumn){
    for(int i = 0; i < rows; i++){
      divideCells(i, firstColumn, i, secondColumn, i, targetColumn);
    }
  }
  
  public void insertRow(int row){
    Node iterator;
    Node newNode;
    if(row == 0){
      iterator = traverse(rows-1, 0);
      newNode = new Node();
      head = newNode;
    }
    else{
      iterator = traverse(row-1, 0);
      newNode = new Node();
    }
    Node current = iterator;
    for(int i = 0; i < columns - 1; i++){
      newNode.setDown(current.getDown());
      current.setDown(newNode);
      newNode.setRight(new Node());
      newNode = newNode.getRight();
      current = current.getRight();
    }
    newNode.setDown(current.getDown());
    current.setDown(newNode);
    newNode.setRight(iterator.getDown());
    rows++;
  }
  
  public void insertColumn(int col){
    Node iterator;
    Node newNode;
    if(col == 0){
      iterator = traverse(0, columns - 1);
      newNode = new Node();
      head = newNode;
    }
    else{
      iterator = traverse(0, col-1);
      newNode = new Node();
    }
    Node current = iterator;
    for(int i = 0; i < rows - 1; i++){
      newNode.setRight(current.getRight());
      current.setRight(newNode);
      newNode.setDown(new Node());
      newNode = newNode.getDown();
      current = current.getDown();
    }
    newNode.setRight(current.getRight());
    current.setRight(newNode);
    newNode.setDown(iterator.getRight());
    columns++;
  }
  
  public void deleteRow(int row){
    Node iterator;
    if(row == 0)
      iterator = traverse(rows - 1, 0);
    else
      iterator = traverse(row - 1, 0);
    for(int i = 0; i < columns; i++){
      iterator.setDown(iterator.getDown().getDown());
      iterator = iterator.getRight();
    }
    if(row == 0)
      head = iterator.getDown();
    rows--;
  }
  
  public void deleteColumn(int col){
    Node iterator;
    if(col == 0)
      iterator = traverse(0, columns - 1);
    else
      iterator = traverse(0, col - 1);
    for(int i = 0; i < rows; i++){
      iterator.setRight(iterator.getRight().getRight());
      iterator = iterator.getDown();
    }
    if(col == 0)
      head = iterator.getRight();
    columns--;
  }
  
  public void display(){
    System.out.print("     ");
    for(int i = 0; i < columns; i++){
      System.out.printf("     col " + i);
    }
    System.out.println();
    Node temp = head;
    for(int i = 0; i < rows; i++){
      System.out.print("row " + i);
      for(int j = 0; j < columns; j++){
        System.out.print(temp.getValue());
        temp = temp.getRight();
      }
      temp = temp.getDown();
      System.out.println();
    }
  }
  
  public Node getHead(){
    return this.head;
  }
  
  public int getRows(){
    return this.rows;
  }
  
  public int getColumns(){
    return this.columns;
  }
}

