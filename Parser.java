import java.util.ArrayList;

class Parser {
    private final ArrayList<Classify> tokens;
    private int position;

    //Contructor
    Parser(ArrayList<Classify> tokens) {
        this.tokens = tokens;
    }

    //Helpers
    private Classify peek(){
        if (position < tokens.size()) {
            return tokens.get(position);
        }
        return null;
    }
    
    
    private Classify consume(){
        return tokens.get(position++);
    }

    private void expect(Symbol type) {
        Classify nextToken = consume();
        if (nextToken.type != type)
            throw new RuntimeException("Expected " + type + " but got " + nextToken);
    }
    
    //VARIABLE
    private Node parsePrimary() {
        Classify nextToken = peek();

        if (nextToken == null){
            throw new RuntimeException("Unexpected end of input");
        }

        if (nextToken.type == Symbol.LPAREN) {
            consume();
            Node inner = parseOR();
            expect(Symbol.RPAREN);
            return inner;
        }

        if (nextToken.type == Symbol.VARIABLE) {
            consume();
            return new Node(nextToken.value);
        }
        throw new RuntimeException("Unexpected token: " + nextToken);
    }
    
    //OR, AND, NOT
    public Node parse(){
        Node result = parseOR();

        if (position < tokens.size()) {
            throw new RuntimeException("Unexpected token: " + peek());
        }
        return result;
    }

    private Node parseOR(){
        Node left = parseAND();

        while (peek() != null && peek().type == Symbol.OR) {
            consume();
            Node right = parseAND();
            left = new Node(Symbol.OR, left, right); 
        }
        return left;
    }

    private Node parseAND(){
        Node left = parseNOT();

        while (peek() != null && peek().type == Symbol.AND) { 
            consume();
            Node right = parseNOT();
            left = new Node(Symbol.AND, left, right);
        }
        return left;
    }

    private Node parseNOT(){
        if (peek() != null && peek().type == Symbol.NOT){
            consume();
            Node child = parseNOT();
            return new Node(Symbol.NOT, child);
        }
        return parsePrimary();
    }
}