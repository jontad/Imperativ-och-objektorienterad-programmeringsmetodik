# Inlupp 4
# How to run
- `make all` or `make` to build the program
- `make run` to run the program
- `make test` to build the tests of the program
- `make diff` to compare expected output with actual output (nothing happens if successful). Needs a text file of expected output.
- `make rm` to remove newly compiled files. Needs to be done after first time compiling.
- `make clean` to remove class files of the program

**** SYNTAX *****

Scopes:
- {SymbolicExpression}
Note: Variables defined in scope are only bound in said scope

EX: {1 = x} + {1 = x} == 2.0
    {{1 = x} = x} == 1.0

Conditionals:
- if SymbolicExpression op SymbolicExpression SCOPE else SCOPE
- Note: Both SymbolicExpression must contain values (not unassigned variables)

EX:
3 = x
4 = y
if x < y { 42 } else { 4711 }
evaluates to 42.0


FunctionDeclaration:
- function function_name(ARGUMENT(S))
  body
  end
  
Note: Variables in body are only defined in corresponding function
EX: function max(x, y)
    if x < y { y } else { x }
    end

FunctionCall:
- function_name(ARGUMENT(S))

EX: max(5, 7)
    7.0