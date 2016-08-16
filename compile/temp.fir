 --------------------------------------------------
|                Compute First Set                 |
 --------------------------------------------------
param-list Size: 5 ^^ {int void bool double float}
expression Size: 3 ^^ {ID ( NUM}
additive-expression-temp Size: 3 ^^ {empty + -}
param-temp Size: 2 ^^ {empty [}
expression-sub-sub Size: 12 ^^ {== <= != empty * + >= / > = - <}
param Size: 5 ^^ {int void bool double float}
expression-sub Size: 14 ^^ {* + ( >= / - == <= != empty > [ = <}
args Size: 4 ^^ {empty ID ( NUM}
var Size: 2 ^^ {empty [}
relop Size: 6 ^^ {== <= != >= > <}
type-specifier Size: 5 ^^ {int void bool double float}
compound-stmt Size: 1 ^^ {{}
addop Size: 2 ^^ {+ -}
arg-list` Size: 2 ^^ {empty ,}
var-declaration Size: 2 ^^ {; [}
local-declarations` Size: 6 ^^ {int empty void bool double float}
simple-expression Size: 7 ^^ {== <= != empty >= > <}
factor-temp Size: 3 ^^ {empty ( [}
statement-list Size: 9 ^^ {if empty ; ID return ( { NUM while}
additive-expression Size: 3 ^^ {ID ( NUM}
term-temp Size: 3 ^^ {empty * /}
iteration-stmt Size: 1 ^^ {while}
declaration Size: 5 ^^ {int void bool double float}
selection-stmt Size: 1 ^^ {if}
declaration-list Size: 5 ^^ {int void bool double float}
arg-list Size: 3 ^^ {ID ( NUM}
factor Size: 3 ^^ {ID ( NUM}
params Size: 5 ^^ {int void bool double float}
general-declaration Size: 3 ^^ {; ( [}
selection-stmt-temp Size: 2 ^^ {else empty}
statement Size: 8 ^^ {if return ID ; ( { NUM while}
expression-stmt Size: 4 ^^ {; ID ( NUM}
expression-none Size: 11 ^^ {== <= != * empty + >= > / < -}
term Size: 3 ^^ {ID ( NUM}
program Size: 5 ^^ {int void bool double float}
declaration-list` Size: 6 ^^ {int empty void bool double float}
local-declarations Size: 6 ^^ {int empty void bool double float}
var-declaration-sub Size: 5 ^^ {int void bool double float}
return-stmt Size: 1 ^^ {return}
mulop Size: 2 ^^ {* /}
param-list` Size: 2 ^^ {empty ,}
