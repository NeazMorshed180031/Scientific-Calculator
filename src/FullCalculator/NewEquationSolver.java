package FullCalculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NewEquationSolver {

    public static String solveEquation(String equation) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String output = "";
        try {
            output = "" + engine.eval(equation);
            System.out.println(output);
        } catch (Exception e) {
            System.out.println("Error occurred");
            return "Error Occurred";
        }
        return output;
    }

    public static void main(String args[]) {
//        ScriptEngineManager mgr = new ScriptEngineManager();
//        ScriptEngine engine = mgr.getEngineByName("JavaScript");
//        String foo = "(40+2)-(9+2)";
//        try {
//            System.out.println(engine.eval(foo));
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
        NewEquationSolver.solveEquation("(10+9) - 3");
    }

}
