import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) {
        /*
        A+B => nand only circuit = (A*B)'

        #1 Recognize symbol/operator

        #2 Recognize variables affected by the operator

        #3 Use rule to convert to NAND/NOR only circuit
        */

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter a boolean expression: ");
                if (!sc.hasNextLine()) {
                    break;
                }

                String expression = sc.nextLine().trim();
                if (expression.isEmpty()) {
                    continue;
                }

                try {
                    expression = expression.toUpperCase();

                    String temp_Expression = expression.replace("(", " ( ");
                    String new_Expression = temp_Expression.replace(")", " ) ");

                    StringTokenizer st = new StringTokenizer(new_Expression, " ");

                    ArrayList<Classify> token = new ArrayList<>();

                    while (st.hasMoreTokens()) {
                        String currentToken = st.nextToken();

                        switch (currentToken) {
                            case "(" -> token.add(new Classify(Symbol.LPAREN, currentToken));
                            case ")" -> token.add(new Classify(Symbol.RPAREN, currentToken));
                            case "AND", "*" -> token.add(new Classify(Symbol.AND, currentToken));
                            case "OR", "+" -> token.add(new Classify(Symbol.OR, currentToken));
                            case "NOT", "!", "¬" -> token.add(new Classify(Symbol.NOT, currentToken));
                            case "NAND", "!*" -> token.add(new Classify(Symbol.NAND, currentToken));
                            case "NOR", "!+" -> token.add(new Classify(Symbol.NOR, currentToken));
                            default -> token.add(new Classify(Symbol.VARIABLE, currentToken));
                        }
                    }

                    Parser parser = new Parser(token);
                    Node root = parser.parse();

                    System.out.println("Original: " + root);
                    System.out.println("Nand-Only: " + root.toNAND());
                    System.out.println("Nor-Only: " + root.toNOR());
                } catch (Exception e) {
                    System.out.println("Invalid expression: " + e.getMessage());
                }

                System.out.print("Do another expression? (y/n): ");
                String again = sc.hasNextLine() ? sc.nextLine().trim() : "";
                if (again.isEmpty() || again.equalsIgnoreCase("n") || again.equalsIgnoreCase("no")) {
                    break;
                }
            }
        }
    }
}