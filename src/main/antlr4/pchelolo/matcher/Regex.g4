grammar Regex;

regex   : pattern;

pattern : '(' pattern ')'      #Braced
        | pattern '*'          #Closure
        | pattern  pattern     #And
        | pattern '|' pattern  #Or
        | ID                   #Id
        ;

ID      : [a-zA-Z0-9];