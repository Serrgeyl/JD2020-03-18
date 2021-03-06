package by.it.verbitsky.calc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

abstract class Var implements Operation {
    private static ResourceManager rm = ConsoleRunner.getRm();

    private static Map<String, Var> calcMemory = new HashMap<>();

    public static Map<String, Var> getCalcMemory() {
        return calcMemory;
    }

    public static void setCalcMemory(Map<String, Var> calcMemory) {
        Var.calcMemory = calcMemory;
    }

    public static Var createVar(String strVar) throws CalcException {
        if (strVar.matches(Patterns.SCALAR)) {
            return new Scalar(strVar);
        }
        if (strVar.matches(Patterns.VECTOR)) {
            return new Vector(strVar);
        }
        if (strVar.matches(Patterns.MATRIX)) {
            return new Matrix(strVar);
        } else {
            Var var = calcMemory.get(strVar);
            if (var != null) {
                return var;
            } else {
                throw new CalcException(String.format(rm.getMessage(CalcMessages.SYSTEM_ERROR_UNKNOWN_VAR), strVar));
            }
        }
    }

    @Override
    public String toString() {
        return " Abstract Var";
    }

    @Override
    public Var add(Var other) throws CalcException {
        throw new CalcException(String.format(rm.getMessage(CalcMessages.SYSTEM_ERROR_WRONG_OP_ADD),
                this, other));
    }

    @Override
    public Var sub(Var other) throws CalcException {
        throw new CalcException(String.format(rm.getMessage(CalcMessages.SYSTEM_ERROR_WRONG_OP_SUB),
                this, other.toString()));
    }

    @Override
    public Var mul(Var other) throws CalcException {
        throw new CalcException(String.format(rm.getMessage(CalcMessages.SYSTEM_ERROR_WRONG_OP_MUL),
                this, other));
    }

    @Override
    public Var div(Var other) throws CalcException {
        throw new CalcException(String.format(rm.getMessage(CalcMessages.SYSTEM_ERROR_WRONG_OP_DIV),
                this, other));
    }


    public static void memoryAdd(String name, Var value) {
        getCalcMemory().put(name, value);
    }

    //Methods for Calc memory
    public static void printvar() {
        if (!getCalcMemory().isEmpty()) {
            Set<String> keys = getCalcMemory().keySet();
            for (String key : keys) {
                System.out.printf("%s=%s\n", key, getCalcMemory().get(key).toString());
            }
        } else {
            System.out.println(Patterns.EMPTY_BUFFER);
        }
    }


    public static void sortvar() {
        setCalcMemory(new TreeMap<>(getCalcMemory()));
        printvar();
    }


    public static void clearMemory() {
        getCalcMemory().clear();
    }

}
