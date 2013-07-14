grammar Regex;

regex       : disjunction;

disjunction : conjunction ( '|' conjunction )*;

conjunction : atom ( atom )*;

atom        : '(' disjunction ')'       #Braced
            | atom '*'                  #Closure
            | ID                        #Id
            ;

ID          : [a-zA-Z0-9];