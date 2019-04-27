package cn.jkcraft.rule110;

import org.jetbrains.annotations.NotNull;

import java.util.Random;


/***
 * Any live cell with fewer than two live neighbours dies, as if by underpopulation.
 * Any live cell with two or three live neighbours lives on to the next generation.
 * Any live cell with more than three live neighbours dies, as if by overpopulation.
 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 */
public class Rule110 {
    private int[][] matrix;
    private int row;
    private int column;

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public RuleProcessor<Integer> getProcessor() {
        return processor;
    }

    public void setProcessor(RuleProcessor<Integer> processor) {
        this.processor = processor;
    }

    private RuleProcessor<Integer> processor;
    public static RuleProcessor<Integer> standar(){
        return (a,b,c)->{
            int result = -1;
            if(a == 1 && b == 1 && c == 1){
                result = 0;
            }
            else if(a == 1 && b == 1 && c == 0){
                result = 1;
            }
            else if(a == 1 && b == 0 && c == 1){
                result = 1;
            }
            else if(a == 1 && b == 0 && c == 0){
                result = 0;
            }
            else if(a == 0 && b == 1 && c == 1){
                result = 1;
            }
            else if(a == 0 && b == 1 && c == 0){
                result = 1;
            }
            else if(a == 0 && b == 0 && c == 1){
                result = 1;
            }
            else if(a == 0 && b == 0 && c == 0){
                result = 1;
            }
            return result;
        };
    }

    public Rule110(int row, int column, RuleProcessor<Integer> processor){
        this.processor = processor;
        matrix = new int[row][column];
        this.row = row;
        this.column = column;
    }

    public void start(int count){
        Random random = new Random();
        for (int i = 0; i < count; i++) {
             matrix[random.nextInt(row)][random.nextInt(row)] = 1;
        }
        System.out.println(rule110ToString(matrix));
    }

    public void iterator(int times){
        for (int i = 0; i < times; i++) {
            nextGeneration(matrix);
            System.out.println(rule110ToString(matrix));
        }
    }

    public void iteratorOnce(){
        nextGeneration(matrix);
    }

    @org.jetbrains.annotations.Contract("_ -> param1")
    private int[][] nextGeneration(int[][] source){
        int[][] temp = new int[source.length][source[0].length];
        System.arraycopy(source,0,temp,0,source.length);
        for (int i = 0; i < source.length; i++) {
            int[] columnArray = source[i];
            for (int j = 0; j < columnArray.length; j++) {
                int a = j -1 ;
                int c = j+ 1;
                if(a < 0){
                    a = columnArray.length - 1;
                }
                if(columnArray.length == c){
                    c = 0;
                }
                temp[i][j] = processor.process(source[i][a],source[i][j],source[i][c]);
            }
        }
        System.arraycopy(temp,0,source,0,source.length);
        return source;
    }

    @NotNull
    private String rule110ToString(int[][] matrix){
        StringBuilder sb = new StringBuilder();
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                sb.append(String.format(" %d ",anInt));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return rule110ToString(matrix);
    }

    public static void main(String[] args) {
        Rule110 r = new Rule110(10,10,Rule110.standar());
        r.start(10);
        r.iterator(100);
        System.out.println(r);
    }
}
