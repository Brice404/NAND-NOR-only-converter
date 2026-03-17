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
            case VARIABLE:
                return new Node(value);

            case NOT: {
                Node a = left.toNAND();
                return new Node(Symbol.NAND, a, a);
            }

            case AND: {
                Node a = left.toNAND();
                Node b = right.toNAND();
                Node t = new Node(Symbol.NAND, a, b);
                return new Node(Symbol.NAND, t, t);
            }

            case OR: {
                Node a = left.toNAND();
                Node b = right.toNAND();
                return new Node(Symbol.NAND, new Node(Symbol.NAND, a, a), new Node(Symbol.NAND, b, b));
            }

            default:
                throw new RuntimeException("Cannot convert " + type + " to NAND");
        }
    }

    Node toNOR(){
        switch (type) {
            case VARIABLE:
                return new Node(value);

            case NOT: {
                Node a = left.toNOR();
                return new Node(Symbol.NOR, a, a);
            }

            case AND: {
                Node a = left.toNOR();
                Node b = right.toNOR();
                return new Node(Symbol.NOR, new Node(Symbol.NOR, a, a), new Node(Symbol.NOR, b, b));
            }

            case OR: {
                Node a = left.toNOR();
                Node b = right.toNOR();
                Node t = new Node(Symbol.NOR, a, b);
                return new Node(Symbol.NOR, t, t);
            }

            default:
                throw new RuntimeException("Cannot convert " + type + " to NOR");
        }
    }

    @Override
    public String toString() {
        if (type == Symbol.VARIABLE) {
            return value;
        }

        if (type == Symbol.NOT) {
            return "(" + type + " " + left + ")";
        }

        return "(" + left + " " + type + " " + right + ")";
    }
}
