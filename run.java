import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

class run {
    public static void main(String[] args) {
        /*
        A+B => nand only circuit = (A*B)'

        #1 Recognize symbol/operator

        #2 Recognize variables affected by the operator

        #3 Use rule to convert to NAND/NOR only circuit
        */


        // #1 Recognize symbol/operator
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter a boolean expression: ");
            String expression = sc.nextLine().toUpperCase();

            String temp_Expression = expression.replace("(", " ( ");
            String new_Expression = temp_Expression.replace(")", " ) ");

            StringTokenizer st = new StringTokenizer(new_Expression, " ");


            ArrayList<Classify> token = new ArrayList<>();

            while(st.hasMoreTokens()){
                String currentToken = st.nextToken();
                
                switch (currentToken) {
                    case "(" -> token.add(new Classify(Symbol.LPAREN,currentToken));
                    case ")" -> token.add(new Classify(Symbol.RPAREN,currentToken));
                    case "AND" -> token.add(new Classify(Symbol.AND,currentToken));
                    case "OR" -> token.add(new Classify(Symbol.OR,currentToken));
                    case "NOT" -> token.add(new Classify(Symbol.NOT,currentToken));
                    default -> token.add(new Classify(Symbol.Variable,currentToken));
                }
            }
            System.out.println(token);
        } 
    }
}