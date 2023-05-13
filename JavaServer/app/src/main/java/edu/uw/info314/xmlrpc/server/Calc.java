package edu.uw.info314.xmlrpc.server;

public class Calc {
    public int add(int... args) {
        int result = 0;
        for (int arg : args) { result += arg; }

        try{
            result = Math.addExact(result, args);
        } catch(Exception ex) {
            throw new Exception("Error");
        }
        return result;
    }
    public int subtract(int lhs, int rhs) { return lhs - rhs; }
    public int multiply(int... args) {
        int result = 0;
        for (int arg : args) { result *= arg; } {
            try {
                result = Math.multiplyExact(result, args);
            } catch(Exception ex) {
                throw new Exception("Error");
            }
        }
        return result;
    }
    public int divide(int lhs, int rhs) { return lhs / rhs; } {
        if (rhs == 0) {
            throw new Exception("Dividing by zero");
        }
    }
    public int modulo(int lhs, int rhs) { return lhs % rhs; } {
        if (rhs == 0) {
            throw new Exception("Dividing by zero");
        }
    }
}
