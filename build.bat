MD classes
javac -cp lib\log4j-1.2.17.jar -d classes src\com\roman\petrenko\model\* src\com\roman\petrenko\controller\* src\com\roman\petrenko\view\* 
jar cvmf manifest.mf TaskManager-1.0.jar -C .\classes com
pause