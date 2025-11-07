if [ "$1" == "" ]; then
  cat ./in_out/testcase.txt | java Solve.java
elif [ "$1" == "save" ]; then
  cp ./Solve.java ./"$2.java"
  sed -i "s/Solve/$2/g" ./"$2.java"
elif [ "$1" == "d" ]; then
  # to toggle btween comment or uncomment debugln's
  sed -i '/debugln.*;/ { /^\s*\/\// s/^\(\s*\)\/\/\(.*\)/\1\2/; t; s/^\(\s*\)\(.*\)/\1\/\/\2/; }' Solve.java
elif [ "$1" == "dc" ]; then
  # to toggle btween comment or uncomment debugln's
  sed -i '/\/\/ @debug/,/\/\/ @debugend/ { /\/\/ @debug\| \/\/ @debugend/! { /^ *\/\// s/^\( *\)\/\/\(.*\)/\1\2/; t; s/^/\/\// } }' Solve.java
elif [ "$1" == "check" ]; then
  cat ./in_out/testcase.txt | java Solve.java | java ./Checker.java
else
  # cd ./in_out/ && yarn start --link $1 && cd ..
  cp template/Solve.txt Solve.java
  # sed -i '/\/\/ @solverstart/,/\/\/ @solverend/c\// @solverstart\n  public static void solver(int z, Scanner in) {}\n  // @solverend' Solve.java
  echo "$1" >>.history_links
fi
