class Classify{
    Symbol type;
    String value;
    
    Classify(Symbol type, String value){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{type: " + type + ", value: " + value + "}";
    }
}