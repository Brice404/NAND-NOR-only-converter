class Node {
    Symbol type;
    String value;
    Node left, right;

    Node(Symbol type, Node left, Node right){
        this.type = type;
        this.left = left;
        this.right = right;
    }

    Node(Symbol type, Node child) {
        this.type = type;
        this.left = child;
    }

     Node(String value) {
        this.type = Symbol.VARIABLE;
        this.value = value;
    }

    Node toNAND(){
        switch (type) {
            case VARIABLE -> {
                return new Node(value);
            }

            case NOT -> {
                Node a = left.toNAND();
                return new Node(Symbol.NAND, a, a);
            }

            case AND -> {
                Node a = left.toNAND();
                Node b = right.toNAND();
                Node t = new Node(Symbol.NAND, a, b);
                return new Node(Symbol.NAND, t, t);
            }

            case OR -> {
                Node a = left.toNAND();
                Node b = right.toNAND();
                return new Node(Symbol.NAND, new Node(Symbol.NAND, a, a), new Node(Symbol.NAND, b, b));
            }

            case NAND -> {
                Node a = left.toNAND();
                Node b = right.toNAND();
                return new Node(Symbol.NAND, a, b);
            }

            case NOR -> {
                Node a = left.toNAND();
                Node b = right.toNAND();
                Node orNode = new Node(Symbol.NAND, new Node(Symbol.NAND, a, a), new Node(Symbol.NAND, b, b));
                return new Node(Symbol.NAND, orNode, orNode);
            }

            default -> throw new RuntimeException("Cannot convert " + type + " to NAND");
        }
    }

    Node toNOR(){
        switch (type) {
            case VARIABLE -> {
                return new Node(value);
            }

            case NOT -> {
                Node a = left.toNOR();
                return new Node(Symbol.NOR, a, a);
            }

            case AND -> {
                Node a = left.toNOR();
                Node b = right.toNOR();
                return new Node(Symbol.NOR, new Node(Symbol.NOR, a, a), new Node(Symbol.NOR, b, b));
            }

            case OR -> {
                Node a = left.toNOR();
                Node b = right.toNOR();
                Node t = new Node(Symbol.NOR, a, b);
                return new Node(Symbol.NOR, t, t);
            }

             case NOR -> {
                Node a = left.toNOR();
                Node b = right.toNOR();
                return new Node(Symbol.NOR, a, b);
            }
 
            case NAND -> {
                Node a = left.toNOR();
                Node b = right.toNOR();
                Node andNode = new Node(Symbol.NOR, new Node(Symbol.NOR, a, a), new Node(Symbol.NOR, b, b));
                return new Node(Symbol.NOR, andNode, andNode);
            }

            default -> throw new RuntimeException("Cannot convert " + type + " to NOR");
        }
    }

    private String negated(Node inner) {
        if (inner.type == Symbol.VARIABLE) {
            return "¬" + inner;
        }
        return "¬(" + inner + ")";
    }

    @Override
    public String toString() {
        return switch (type) {
            case VARIABLE -> value;
            case NOT -> negated(left);
            case AND -> "(" + left + " ∧ " + right + ")";
            case OR -> "(" + left + " ∨ " + right + ")";
            case NAND -> left == right ? negated(left) : "¬(" + left + " * " + right + ")";
            case NOR -> left == right ? negated(left) : "¬(" + left + " + " + right + ")";
            default -> "(" + left + " " + type + " " + right + ")";
        };
    }
}
