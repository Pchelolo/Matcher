grammar Regex;

regex       : disjunction;

disjunction : conjunction ( '|' conjunction )*;

conjunction : atom ( atom )*;

atom        : '(' disjunction ')'         #Braced
            | '[' ( range )+ ']'          #RangeGroup
            | '[' ID ( ID )+ ']'          #ListGroup
            | atom '*'                    #Closure
            | ID                          #Id
            ;

range       : ID '-' ID;

ID          : DIGIT
            | LETTER
            ;

NUMBER      : DIGIT+;

DIGIT       : [0-9];
LETTER      : [a-zA-Z];