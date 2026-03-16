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
