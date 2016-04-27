grammar HillbilliesTaskLang;

task : 
'name' ':' name=STRING
'priority' ':' priority=INT
'activities' ':' statements
;

statements: stmts += statement (stmts += statement)*;

statement : 
    assignmentStatement
  | whileStatement
  | ifStatement
  | breakStatement
  | printStatement
  | actionStatement
	;

assignmentStatement : variableName=IDENTIFIER ':=' value=expression ';';

whileStatement : 'while' condition=expression 'do' body=statements 'done';

breakStatement: 'break' ';';

ifStatement : 'if' condition=expression 'then' ifbody=statements ('else' elsebody=statements)?  'fi';

printStatement : 'print' value=expression ';';

actionStatement :
    'moveTo' target=expression ';' #moveToAction
  | 'work' target=expression ';' #workAction
	| 'follow' target=expression ';' #followAction
	| 'attack' target=expression ';' #attackAction
	;

expression : 
             variable=IDENTIFIER #readVariableExpr
           | position #positionExpr
					 | unit #unitExpr
           | 'true' #trueLiteral
           | 'false' #falseLiteral
           | '(' expr=expression ')' #parenExpr
           | test=(ISSOLID|ISPASSABLE) argument=expression #positionTestExpr
           | test=(ISFRIEND|ISENEMY|ISALIVE|CARRIESITEM) argument=expression #unitTestExpr
           | '!' expr=expression #notExpr
           | left=expression '&&' right=expression #andExpr
           | left=expression '||' right=expression #orExpr
           ;

unit: 
      'this' #thisUnit
    | 'friend' #friendUnit
    | 'enemy' #enemyUnit
    | 'any' #anyUnit
    ;

position: 
     'here' #herePosition
   | 'log' #logPosition
	 | 'boulder' #boulderPosition
	 | 'workshop' #workshopPosition
	 | '(' x=INT ',' y=INT ',' z=INT ')' #literalPosition
	 | 'next_to' expr=expression #nextToPosition
	 | 'position_of' expr=expression #positionOfPosition
	 | 'selected' #selectedPosition
	 ;


ISSOLID: 'is_solid';
ISPASSABLE: 'is_passable';

ISFRIEND: 'is_friend';
ISENEMY: 'is_enemy';
ISALIVE: 'is_alive';
CARRIESITEM: 'carries_item';


INT: ('+'?|'-') ('0'..'9')+;

STRING: '"' (ESC|.)*? '"';
fragment ESC : '\\"' | '\\\\' ;

IDENTIFIER: ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9')*;

WS : (' ' | '\t' | '\n') -> skip;
