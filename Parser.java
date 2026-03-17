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
    
    //Primary
    private Node parsePrimary() {
        Classify nextToken = peek();

        if (nextToken == null){
            throw new RuntimeException("Unexpected end of input");
        }

        if (nextToken.type == Symbol.LPAREN) {
            consume();
            Node inner = parseOr();
            expect(Symbol.RPAREN);
            return inner;
        }

        if (nextToken.type == Symbol.VARIABLE) {
            consume();
            return new Node(nextToken.value);
        }
        throw new RuntimeException("Unexpected token: " + nextToken);
    }
    
    //OR, AND, NOT, VARIABLE
    public Node parse(){
        Node result = parseOR();

        if (position < tokens.size()) {
            throw new RuntimeException("Unexpected token: " + peek());
        }
        return result;
    }

    private Node parseOR(){

    }

    private Node parseAND(){
        
    }

    private Node parseNOT(){
        
    }
}
