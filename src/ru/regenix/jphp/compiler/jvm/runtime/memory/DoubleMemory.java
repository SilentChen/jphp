package ru.regenix.jphp.compiler.jvm.runtime.memory;

public class DoubleMemory extends Memory {

    double value;

    public DoubleMemory(double value) {
        super(Type.DOUBLE);
        this.value = value;
    }

    public static Memory valueOf(double value){
        return new DoubleMemory(value);
    }

    @Override
    public long toLong() {
        return (long)value;
    }

    @Override
    public double toDouble() {
        return value;
    }

    @Override
    public boolean toBoolean() {
        return value != 0.0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Memory toNumeric(){
        return this;
    }

    @Override
    public Memory plus(Memory memory) {
        switch (memory.type){
            case INT: return new DoubleMemory(value + ((LongMemory)memory).value);
            case DOUBLE: return new DoubleMemory(value + ((DoubleMemory)memory).value);
            default: return plus(memory.toNumeric());
        }
    }

    @Override
    public Memory minus(Memory memory) {
        switch (memory.type){
            case INT: return new DoubleMemory(value - ((LongMemory)memory).value);
            case DOUBLE: return new DoubleMemory(value - ((DoubleMemory)memory).value);
            default: return minus(memory.toNumeric());
        }
    }

    @Override
    public Memory mul(Memory memory) {
        switch (memory.type){
            case INT: return new DoubleMemory(value * ((LongMemory)memory).value);
            case DOUBLE: return new DoubleMemory(value * ((DoubleMemory)memory).value);
            default: return mul(memory.toNumeric());
        }
    }

    @Override
    public Memory div(Memory memory) {
        switch (memory.type){
            case INT: return new DoubleMemory(value / ((LongMemory)memory).value);
            case DOUBLE: return new DoubleMemory(value / ((DoubleMemory)memory).value);
            default: return div(memory.toNumeric());
        }
    }

    @Override
    public Memory mod(Memory memory) {
        switch (memory.type){
            case INT: return new DoubleMemory(value % ((LongMemory)memory).value);
            case DOUBLE: return new DoubleMemory(value % ((DoubleMemory)memory).value);
            default: return mod(memory.toNumeric());
        }
    }

    @Override
    public Memory concat(Memory memory) {
        switch (memory.type){
            case STRING: return new StringMemory(toString() + ((StringMemory)memory).value);
            default: return new StringMemory(toString() + memory.toString());
        }
    }

    @Override
    public Memory smaller(Memory memory) {
        switch (memory.type){
            case DOUBLE: return value < ((DoubleMemory)memory).value ? TRUE : FALSE;
            case INT: return value < ((LongMemory)memory).value ? TRUE : FALSE;
            default:
                return smaller(memory.toNumeric());
        }
    }

    @Override
    public Memory minus(long value) {
        return new DoubleMemory(this.value - value);
    }

    @Override
    public Memory plus(long value) {
        return new DoubleMemory(this.value + value);
    }
}
